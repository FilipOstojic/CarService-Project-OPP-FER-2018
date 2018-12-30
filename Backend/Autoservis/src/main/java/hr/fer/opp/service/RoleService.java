package hr.fer.opp.service;

import java.util.List;

import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.dao.GenericDAO;
import hr.fer.opp.model.Role;

@Service("roleService")
public class RoleService {

	@Autowired
	protected GenericDAO<Role> dao;
	
	public List<Role> listAll() {
		return dao.read();
	}
	
	private boolean exists(Role role) {
		List<Role> list = dao.read();
		return list.contains(role);
	}
	
	public Role showRecord(Integer roleID) {
		Role role = dao.read(String.valueOf(roleID));
		if (role != null) {
			return role;
		}
		throw new NullPointerException("Role with " + roleID + " does not exist.");
	}

	public Role showRecordByName(String roleName) {
		Role role = dao.readByName(roleName);
		if (role != null) {
			return role;
		}
		throw new NullPointerException("Role with " + roleName + " does not exist.");
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
