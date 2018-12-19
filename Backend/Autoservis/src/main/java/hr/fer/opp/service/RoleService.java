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
	private GenericDAO<Role> roleDAO;
	
	public List<Role> listAll(){
		return roleDAO.read();
	}
	
	public Role listRole(int id) {
		Role role = roleDAO.read(String.valueOf(id));
		if(role != null) {
			return role;
		}
		throw new NullPointerException("Cant list null role");
	}
	
	public void deleteRole(Role role) {
		if(roleExists(role)) {
			roleDAO.delete(role);
		} else {
			throw new IllegalArgumentException("This role doesn't exist");
		}
	}
	
	public boolean createRole(Role role) {
		if(role != null && role.getName().length() > 0) {
			roleDAO.create(role);
			return true;
		}
		return false;
	}
	
	public void updateRole(Role role) {
		Role temp = roleDAO.read(String.valueOf(role.getId()));
		
		if(temp == null) {
			throw new NullPointerException("Cant update null role");
		}
		if(temp.equals(role)) {
			throw new IllegalAddException("No update occured for role");
		}
		roleDAO.update(role);
	}

	private boolean roleExists(Role role) {
		return roleDAO.read(String.valueOf(role.getId())) != null;
	}
}
