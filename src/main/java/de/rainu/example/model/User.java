package de.rainu.example.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a User. A user can have
 * * a name
 * * a password
 * * many Roles
 */
public class User {
	private String username;
	private String password;
	private List<UserRole> roles;

	public User(String name, String password, List<UserRole> roles) {
		this.username = name;
		this.password = password;
		this.roles = roles;
	}

	public User(String name, String password, UserRole... roles) {
		this(name, password, Arrays.asList(roles));
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public List<UserRole> getRoles() {
		return Collections.unmodifiableList(roles);
	}
}
