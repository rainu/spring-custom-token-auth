package de.rainu.example.config.security;

import de.rainu.example.store.TokenStore;
import de.rainu.example.store.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author Max Marche (m.marche@tarent.de)
 */
public class AuthProvider implements AuthenticationProvider {

	 @Autowired
	 TokenStore tokenStore;

	 @Autowired
	 UserStore userStore;

	 @Override
	 public Authentication authenticate(Authentication auth) throws AuthenticationException {
		  final AuthTokenContainer tokenContainer = (AuthTokenContainer) auth;
		  final String token = tokenContainer.getToken();

		  if (!tokenStore.contains(token)) {
				throw new BadCredentialsException("Invalid token - " + token);
		  }

		  final String username = tokenStore.get(token);
		  if (!userStore.contains(username)) {
				throw new BadCredentialsException("No user found for token - " + token);
		  }

		  final de.rainu.example.model.User user = userStore.get(username);

		  return new RememberMeAuthenticationToken(user.getUsername(), user, user.getRoles());

//		  try {
//				//Authenticate token against redis or whatever you want
//				//TODO get user from database
//				//This i found weird, you need a Principal in your Token...I use User
//				//I found this to be very redundant in spring security, but Controller param resolving will break if you don't do this...anoying
//				Application.User user = Application.TOKEN_STORE.get(tokenContainer.getToken());
//				User principal = new User(user.name, user.password, user.roles.stream().map(r -> UserRole.valueOf(r)).collect(Collectors.toList())); //new User("user", "password", Arrays.asList(UserRole.ADMIN));
//
//				//Our token resolved to a username so i went with this token...you could make your AuthTokenContainer take the principal.  getCredentials returns "NO_PASSWORD"..it gets cleared out anyways.  also the getAuthenticated for the thing you return should return true now
//				return new UsernamePasswordAuthenticationToken(principal, tokenContainer.getCredentials(), principal.getAuthorities());
//		  } catch (Exception e) {
//				//TODO throw appropriate AuthenticationException types
//				throw new BadCredentialsException("PENIS", e);
//		  }
	 }

	 @Override
	 public boolean supports(Class<?> authentication) {
		  return AuthTokenContainer.class.isAssignableFrom(authentication);
	 }
}