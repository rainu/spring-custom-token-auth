package de.rainu.example.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @author Max Marche (m.marche@tarent.de)
 */
public class AuthTokenContainer extends AbstractAuthenticationToken {
    private final String token;

    public AuthTokenContainer(String token) {
        super(null);
        this.token = token;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    public String getToken() {
        return token;
    }
}