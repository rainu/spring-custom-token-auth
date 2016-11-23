package de.rainu.example.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * This enum contains all roles in our example application.
 * Note that this enum implements {@link GrantedAuthority}!
 */
public enum UserRole implements GrantedAuthority {
	USER,
	ADMIN;

	@Override
	public String getAuthority() {
		return name();
	}
}
