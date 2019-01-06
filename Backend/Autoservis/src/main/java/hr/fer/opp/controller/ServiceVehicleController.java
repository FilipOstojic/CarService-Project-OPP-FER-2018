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

import hr.fer.opp.model.ServiceVehicle;
import hr.fer.opp.service.ServiceVehicleService;

@RestController
@CrossOrigin("*")
public class ServiceVehicleController {
	
	@Autowired
	private ServiceVehicleService serviceVehicleService;
	
	@RequestMapping(value = "/serviceVehicle", method = RequestMethod.GET)
	public ResponseEntity<List<ServiceVehicle>> listServiceVehicles() {
		List<ServiceVehicle> list = serviceVehicleService.listAll();
		if (list != null) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/serviceVehicle", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<ServiceVehicle> crateServiceVehcicles(@RequestBody ServiceVehicle vehicle) {
		boolean created = serviceVehicleService.createRecord(vehicle);
		if (created) {
			return new ResponseEntity<ServiceVehicle>(vehicle, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<ServiceVehicle>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/serviceVehicle", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ServiceVehicle> updateServiceVehicle(@RequestBody ServiceVehicle vehicle) {
		try {
			serviceVehicleService.updateRecord(vehicle);
			return new ResponseEntity<ServiceVehicle>(vehicle, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ServiceVehicle>(vehicle, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/serviceVehicle/{licensePlate}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<ServiceVehicle> deleteServiceVehicle(@PathVariable("licensePlate") String licensePlate) {
		try {
			ServiceVehicle vehicle = serviceVehicleService.showRecord(licensePlate);
			serviceVehicleService.deleteRecord(vehicle);
			return new ResponseEntity<ServiceVehicle>(vehicle, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ServiceVehicle>(HttpStatus.BAD_REQUEST);
		}
	}
}
