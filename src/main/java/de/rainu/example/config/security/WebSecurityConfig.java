package de.rainu.example.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

/**
 * This class is responsible for configure our security settings.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		  .addFilterBefore(createCustomFilter(), AnonymousAuthenticationFilter.class)
		  .csrf().disable();
	}

	//Note, we don't register this as a bean as we don't want it to be added to the main Filter chain, just the spring security filter chain
	protected AbstractAuthenticationProcessingFilter createCustomFilter() throws Exception {
		//here we define the interfaces which don't need any authorisation
		AuthFilter filter = new AuthFilter(new NegatedRequestMatcher(
		  new AndRequestMatcher(
			 new AntPathRequestMatcher("/login"),
			 new AntPathRequestMatcher("/health")
		  )
		));
		filter.setAuthenticationManager(authenticationManagerBean());
		return filter;
	}
}
