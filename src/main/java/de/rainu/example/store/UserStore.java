package de.rainu.example.store;

import de.rainu.example.model.User;
import de.rainu.example.model.UserRole;
import org.springframework.stereotype.Component;

/**
 * This class represents a in-memory user store. For productive usage
 * this store should be a database.
 */
@Component
public class UserStore extends AbstractStore<String, User> {

	public UserStore() {
	}

	@Override
	protected void initilizeStore() {
		store.put("admin", new User("admin", "admin", UserRole.ADMIN, UserRole.USER));
		store.put("user", new User("user", "user", UserRole.USER));
	}

	@Override
	public User get(String username) {
		return super.get(username);
	}

	@Override
	public boolean contains(String username) {
		return super.contains(username);
	}
}
