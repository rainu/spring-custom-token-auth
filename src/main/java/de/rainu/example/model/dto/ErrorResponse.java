package de.rainu.example.model.dto;

/**
 * This class represents a error response. They contains only a error message.
 */
public class ErrorResponse {
	private String message;

	public ErrorResponse() {
	}

	public ErrorResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
