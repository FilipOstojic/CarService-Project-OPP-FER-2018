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

import hr.fer.opp.model.User;
import hr.fer.opp.service.UserService;

@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listModels() {
		List<User> list = userService.listAll();
		if (list != null) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<User> crateModel(@RequestBody User model) {
		boolean created = userService.createRecord(model);
		if (created) {
			return new ResponseEntity<User>(model, HttpStatus.CREATED);
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
