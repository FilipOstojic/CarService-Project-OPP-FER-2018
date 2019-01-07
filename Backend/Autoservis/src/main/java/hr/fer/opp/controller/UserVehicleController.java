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

import hr.fer.opp.model.UserVehicle;
import hr.fer.opp.service.UserVehicleService;

@RestController
@CrossOrigin("*")
public class UserVehicleController {

	@Autowired
	private UserVehicleService userVehicleService;

	@RequestMapping(value = "/userVehicle", method = RequestMethod.GET)
	public ResponseEntity<List<UserVehicle>> listUserVehicles() {
		List<UserVehicle> list = userVehicleService.listAll();
		if (list != null) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/userVehicle/{email}", method = RequestMethod.GET)
	public ResponseEntity<List<UserVehicle>> listVehiclesFromUser(@PathVariable("email") String userEmail) {
		List<UserVehicle> list = userVehicleService.listAllFromUser(userEmail);
		if (list != null) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/userVehicle", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<UserVehicle> crateUserVehcicles(@RequestBody UserVehicle vehicle) {
		boolean created = userVehicleService.createRecord(vehicle);
		if (created) {
			return new ResponseEntity<UserVehicle>(vehicle, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<UserVehicle>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/userVehicle", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<UserVehicle> updateUserVehicle(@RequestBody UserVehicle vehicle) {
		try {
			if (!UserVehicle.isValidStatus(vehicle.getVehicleStatus())) {
				throw new IllegalArgumentException("Illegal vehicle status");
			}
			userVehicleService.updateRecord(vehicle);
			return new ResponseEntity<UserVehicle>(vehicle, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<UserVehicle>(vehicle, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/userVehicle/{licensePlate}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<UserVehicle> deleteUserVehicle(@PathVariable("licensePlate") String licensePlate) {
		try {
			UserVehicle vehicle = userVehicleService.showRecord(licensePlate);
			userVehicleService.deleteRecord(vehicle);
			return new ResponseEntity<UserVehicle>(vehicle, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<UserVehicle>(HttpStatus.BAD_REQUEST);
		}
	}
}
