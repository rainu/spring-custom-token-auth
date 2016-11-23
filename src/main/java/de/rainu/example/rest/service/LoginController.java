package de.rainu.example.rest.service;

import de.rainu.example.config.security.AuthenticationToken;
import de.rainu.example.model.User;
import de.rainu.example.model.dto.AuthDTO;
import de.rainu.example.model.dto.ErrorResponse;
import de.rainu.example.model.dto.LoginResponse;
import de.rainu.example.store.TokenStore;
import de.rainu.example.store.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * This controller contains the endpoints for handling user tokens.
 */
@RestController
public class LoginController {
	@Autowired
	UserStore userStore;

	@Autowired
	TokenStore tokenStore;

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(@RequestBody AuthDTO auth) {
		User user = userStore.get(auth.getUsername());
		if (user != null && user.getPassword().equals(auth.getPassword())) {
			final String token = UUID.randomUUID().toString();
			tokenStore.put(token, auth.getUsername());

			return new LoginResponse(token);
		}

		return new ResponseEntity(new ErrorResponse("Username or password are incorrect!"), HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(path = "/me/logout", method = RequestMethod.POST)
	public void logout(AuthenticationToken authToken) {
		tokenStore.remove(authToken.getToken());
	}
}
