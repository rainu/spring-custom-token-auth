package de.rainu.example.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * @author Max Marche (m.marche@tarent.de)
 */
public class AuthFilter extends AbstractAuthenticationProcessingFilter {

	 public static final String TOKEN_HEADER = "x-auth-token";


	 public AuthFilter(RequestMatcher requestMatcher) {
		  super(requestMatcher);

	 }

	 @Override
	 public void doFilter(ServletRequest request, ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		  final String token = getTokenValue((HttpServletRequest) request);

		  //This filter only applies if the header is present
		  if (StringUtils.isEmpty(token)) {
				chain.doFilter(request, response);
				return;
		  }

		  //On success keep going on the chain
		  this.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
				@Override
				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
					 chain.doFilter(request, response);
				}
		  });

		  super.doFilter(request, response, chain);

	 }

	 @Override
	 public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
				throws AuthenticationException, IOException, ServletException {

		  final String tokenValue = getTokenValue(request);

		  if (StringUtils.isEmpty(tokenValue)) {
				//Doing this check is kinda dumb because we check for it up above in doFilter
				//..but this is a public method and we can't do much if we don't have the header
				//also we can't do the check only here because we don't have the chain available
				return null;
		  }

		  AuthTokenContainer token = new AuthTokenContainer(tokenValue);
		  token.setDetails(authenticationDetailsSource.buildDetails(request));

		  return this.getAuthenticationManager().authenticate(token);
	 }

	 private String getTokenValue(HttpServletRequest req) {
		  return Collections.list(req.getHeaderNames()).stream()
					 .filter(header -> header.equalsIgnoreCase(TOKEN_HEADER))
					 .map(header -> req.getHeader(header))
					 .findFirst()
					 .orElse(null);
	 }
}