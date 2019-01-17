package hr.fer.opp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

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

import hr.fer.opp.model.User;
import hr.fer.opp.service.RoleService;
import hr.fer.opp.service.UserService;
import hr.fer.opp.util.Util;

@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listUsers() {
		List<User> list = userService.listAll();
		if (list != null) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/user/mech", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listMechs() {
		List<User> list = userService.listAll(roleService.showRecordByName("MECH"));
		if (list != null) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/user/invitation/{email}", method = RequestMethod.GET)
	public void confirmEmail(@PathVariable("email") String email, HttpServletResponse response) throws IOException {
		try {
			User user = userService.showRecord(email);
			user.setConfirmed(true);
			userService.updateRecord(user);
		} catch (Exception e) {
		}
		response.sendRedirect("/prijava");
	}

	@RequestMapping(value = "/user/createUser", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<User> crateUser(@RequestBody User user) {
		System.out.println(user);
		user.setRole(roleService.showRecordByName("USER"));
		boolean created = userService.createRecord(user);
		if (created) {
			Util.sendEmail(user, servletContext);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/user/createMech", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<User> crateMech(@RequestBody User user) {
		user.setRole(roleService.showRecordByName("MECH"));
		boolean created = userService.createRecord(user);
		if (created) {
			// sto ako slanje mail ne uspije??
			if (Util.sendEmail(user, servletContext)) {
				System.out.println("Poslano");
			}
			
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/user/createAdmin", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<User> crateAdmin(@RequestBody User user) {
		user.setRole(roleService.showRecordByName("ADMIN"));
		boolean created = userService.createRecord(user);
		if (created) {
			Util.sendEmail(user, servletContext);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User> updateModel(@RequestBody User user) {
		try {
			if (user.getPassword() == null || user.getPassword().equals("")) {
				user.setPassword(null);
			}
			userService.updateRecord(user);
			User u = userService.showRecord(user.getEmail());
			return new ResponseEntity<User>(u, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/user/{email}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<User> deleteModel(@PathVariable("email") String email) {
		try {
			User user = userService.showRecord(email);
			userService.deleteRecord(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/user/loggedIn", method = RequestMethod.GET)
	public ResponseEntity<User>  getLoggedIn() {
		return new ResponseEntity<User>(userService.currentlyLoggedIn(), HttpStatus.OK);
	}
}
