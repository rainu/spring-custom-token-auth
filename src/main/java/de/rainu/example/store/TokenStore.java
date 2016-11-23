package de.rainu.example.store;

import org.springframework.stereotype.Component;

/**
 * This class represents a in-memory token store.
 */
@Component
public class TokenStore extends AbstractStore<String, String> {

	public TokenStore() {
	}

	@Override
	protected void initilizeStore() {
		//no tokens are stored on startup
	}

	@Override
	public String get(String token) {
		return super.get(token);
	}

	@Override
	public boolean contains(String token) {
		return super.contains(token);
	}

	@Override
	public void put(String token, String username) {
		super.put(token, username);
	}

	@Override
	public String remove(String token) {
		return super.remove(token);
	}
}
