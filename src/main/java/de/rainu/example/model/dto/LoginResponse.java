package de.rainu.example.model.dto;

/**
 * This class represents a login response. It contains only the generated token.
 */
public class LoginResponse {
	private String token;

	public LoginResponse() {
	}

	public LoginResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
}
