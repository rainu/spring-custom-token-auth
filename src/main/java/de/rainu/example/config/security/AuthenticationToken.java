package de.rainu.example.config.security;

import de.rainu.example.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * This class is responsible for holding the raw token value and - if authenticated - the {@link User}.
 */
public class AuthenticationToken extends AbstractAuthenticationToken {
	private final String token;
	private final User user;

	public AuthenticationToken(String token) {
		super(null);

		this.token = token;
		this.user = null;
		setAuthenticated(false);
	}

	public AuthenticationToken(String token, User user) {
		//note that the constructor needs a collection of GrantedAuthority
		//but our User have a collection of our UserRole's
		super(user.getRoles());

		this.token = token;
		this.user = user;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return getToken();
	}

	@Override
	public Object getPrincipal() {
		return getUser();
	}

	public String getToken() {
		return token;
	}

	public User getUser() {
		return user;
	}
}