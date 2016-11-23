package de.rainu.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import de.rainu.example.config.security.AuthFilter;
import de.rainu.example.model.dto.ErrorResponse;
import de.rainu.example.model.dto.LoginResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

	@LocalServerPort
	int randomServerPort;
	String baseUrl;
	Client client;
	ObjectMapper mapper;

	@Before
	public void setup() {
		client = Client.create();
		baseUrl = "http://localhost:" + randomServerPort + "/";
		mapper = new ObjectMapper();
	}

	@After
	public void clean() {
		client.destroy();
	}

	@Test
	public void healthCheck() {
		ClientResponse response = client.resource(baseUrl + "/health")
				  .get(ClientResponse.class);

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void notLoggedIn() {
		ClientResponse response = client.resource(baseUrl + "/hello")
				  .get(ClientResponse.class);

		assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
	}

	@Test
	public void userDoesNotExists() throws IOException {
		ClientResponse response = client.resource(baseUrl + "/login")
				  .type(MediaType.APPLICATION_JSON)
				  .post(ClientResponse.class, "{\"username\":\"wronguser\", \"password\":\"password\"}");

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		ErrorResponse data = mapper.readValue(response.getEntity(String.class), ErrorResponse.class);
		assertEquals("Username or password are incorrect!", data.getMessage());
	}

	@Test
	public void wrongPassword() throws IOException {
		ClientResponse response = client.resource(baseUrl + "/login")
				  .type(MediaType.APPLICATION_JSON)
				  .post(ClientResponse.class, "{\"username\":\"admin\", \"password\":\"password\"}");

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		ErrorResponse data = mapper.readValue(response.getEntity(String.class), ErrorResponse.class);
		assertEquals("Username or password are incorrect!", data.getMessage());
	}

	public LoginResponse login(String username, String password) throws IOException {
		ClientResponse response = client.resource(baseUrl + "/login")
				  .type(MediaType.APPLICATION_JSON)
				  .post(ClientResponse.class, "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}");

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		LoginResponse data = mapper.readValue(response.getEntity(String.class), LoginResponse.class);
		assertFalse(data.getToken().isEmpty());

		return data;
	}

	@Test
	public void noRights() throws IOException {
		LoginResponse data = login("user", "user");

		ClientResponse response = client.resource(baseUrl + "/hello/admin")
				  .header(AuthFilter.TOKEN_HEADER, data.getToken())
				  .get(ClientResponse.class);

		assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
	}

	@Test
	public void enoughRights() throws IOException {
		LoginResponse data = login("admin", "admin");

		ClientResponse response = client.resource(baseUrl + "/hello")
				  .header(AuthFilter.TOKEN_HEADER, data.getToken())
				  .get(ClientResponse.class);

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		response = client.resource(baseUrl + "/hello/admin")
				  .header(AuthFilter.TOKEN_HEADER, data.getToken())
				  .get(ClientResponse.class);

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void logout() throws IOException {
		LoginResponse data = login("admin", "admin");

		ClientResponse response = client.resource(baseUrl + "/me/logout")
				  .header(AuthFilter.TOKEN_HEADER, data.getToken())
				  .post(ClientResponse.class);

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		response = client.resource(baseUrl + "/hello/admin")
				  .header(AuthFilter.TOKEN_HEADER, data.getToken())
				  .get(ClientResponse.class);

		assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
	}
}
