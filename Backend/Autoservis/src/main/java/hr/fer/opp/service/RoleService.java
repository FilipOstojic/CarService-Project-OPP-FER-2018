package hr.fer.opp.service;

import org.dom4j.IllegalAddException;
import org.springframework.stereotype.Service;

import hr.fer.opp.model.Role;

@Service("roleService")
public class RoleService extends GenericService<Role, Integer> {

	public Role showRecord(Integer roleID) {
		Role role = dao.read(String.valueOf(roleID));
		if (role != null) {
			return role;
		}
		throw new NullPointerException("Cant list null role");
	}

	public void deleteRecord(Role role) {
		if (exists(role)) {
			dao.delete(role);
		} else {
			throw new IllegalArgumentException("This role doesn't exist");
		}
	}

	public boolean createRecord(Role role) {
		if (role != null && !exists(role) && role.getName().length() > 0) {
			dao.create(role);
			return true;
		}
		return false;
	}

	public void updateRecord(Role role) {
		Role temp = dao.read(String.valueOf(role.getId()));

		if (temp == null) {
			throw new NullPointerException("Cant update null role");
		}
		if (temp.equals(role)) {
			throw new IllegalAddException("No update occured for role");
		}
		dao.update(role);
	}
}
