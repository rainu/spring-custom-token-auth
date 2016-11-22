package de.rainu.example.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Max Marche (m.marche@tarent.de)
 */
public enum UserRole implements GrantedAuthority {
  USER,
  ADMIN;

  @Override
  public String getAuthority() {
    return name();
  }
}
