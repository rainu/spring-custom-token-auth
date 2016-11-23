package de.rainu.example.config.security;

import de.rainu.example.model.User;
import de.rainu.example.store.TokenStore;
import de.rainu.example.store.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * This class is responsible for checking the token.
 */
public class AuthProvider implements AuthenticationProvider {

	@Autowired
	TokenStore tokenStore;

	@Autowired
	UserStore userStore;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		final AuthenticationToken tokenContainer = (AuthenticationToken) auth;
		final String token = tokenContainer.getToken();

		//do i know this token?
		if (!tokenStore.contains(token)) {
			//...if not: the token is invalid!
			throw new BadCredentialsException("Invalid token - " + token);
		}

		final String username = tokenStore.get(token);
		if (!userStore.contains(username)) {
			//normally this shouldn't be happen
			throw new BadCredentialsException("No user found for token - " + token);
		}

		final User user = userStore.get(username);

		return new AuthenticationToken(token, user);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		//this class is only responsible for AuthTokenContainers
		return AuthenticationToken.class.isAssignableFrom(authentication);
	}
}