package hr.fer.opp.controller;

import java.util.List;

import javax.servlet.ServletContext;

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
import hr.fer.opp.service.UserService;
import hr.fer.opp.util.Util;

@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listModels() {
		List<User> list = userService.listAll();
		if (list != null) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/user/invitation/{email}", method = RequestMethod.GET)
	public ResponseEntity<User> confirmEmail(@PathVariable("email") String email) {
		try {
			User user = userService.showRecord(email);
			user.setConfirmed(true);
			userService.updateRecord(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<User> crateModel(@RequestBody User user) {
		user.setPassword(Util.digestPassword(user.getPassword()));
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

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User> updateModel(@RequestBody User user) {
		try {
			userService.updateRecord(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
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
}