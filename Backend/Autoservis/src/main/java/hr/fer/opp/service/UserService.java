package hr.fer.opp.service;

import org.dom4j.IllegalAddException;
import hr.fer.opp.model.User;

public class UserService extends GenericService<User, String> {

	public User showRecord(String key) {
		User user = dao.read(key);
		if (user != null) {
			return user;
		}
		throw new NullPointerException("Cant list null user");
	}

	public void deleteRecord(User user) {
		if (exists(user)) {
			dao.delete(user);
		} else {
			throw new IllegalArgumentException("This user doesn't exist");
		}
	}

	public boolean createRecord(User user) {
		if (user != null && !exists(user) && user.getEmail().length() > 0) {
			dao.create(user);
			return true;
		}
		return false;
	}

	public void updateRecord(User user) {
		User temp = dao.read(user.getEmail());

		if (temp == null) {
			throw new NullPointerException("Cant update null user");
		}
		if (temp.equals(user)) {
			throw new IllegalAddException("No update occured for user");
		}
		dao.update(user);
	}

}