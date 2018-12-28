package hr.fer.opp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hr.fer.opp.model.Role;
import hr.fer.opp.service.RoleService;

@RestController
@CrossOrigin("*")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/role", method = RequestMethod.GET)
	public ResponseEntity<List<Role>> listRoles() {
		List<Role> list = roleService.listAll();
		if (list != null) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/role", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Role> crateRole(@RequestBody Role role) {
		boolean created = roleService.createRole(role);
		if (created) {
			return new ResponseEntity<Role>(role, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Role>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/role", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Role> updateRole(@RequestBody Role role) {
		try {
			roleService.updateRole(role);
			return new ResponseEntity<Role>(role, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Role>(role, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Role> deleteRole(@PathVariable("id") String id) {
		try {
			Role role = roleService.listRole(Integer.valueOf(id));
			roleService.deleteRole(role);
			return new ResponseEntity<Role>(role, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Role>(HttpStatus.BAD_REQUEST);
		}
	}
}
