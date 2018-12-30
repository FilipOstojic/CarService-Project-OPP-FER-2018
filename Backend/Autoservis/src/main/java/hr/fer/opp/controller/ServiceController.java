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

import hr.fer.opp.model.Service;
import hr.fer.opp.service.ServiceService;

@RestController
@CrossOrigin("*")
public class ServiceController {

	@Autowired
	private ServiceService serviceService;

	@RequestMapping(value = "/service", method = RequestMethod.GET)
	public ResponseEntity<List<Service>> listModels() {
		List<Service> list = serviceService.listAll();
		if (list != null) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/service", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Service> crateModel(@RequestBody Service service) {
		boolean created = serviceService.createRecord(service);
		if (created) {
			return new ResponseEntity<Service>(service, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Service>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/service", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Service> updateModel(@RequestBody Service service) {
		try {
			serviceService.updateRecord(service);
			return new ResponseEntity<Service>(service, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Service>(service, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/service/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Service> deleteModel(@PathVariable("id") String id) {
		try {
			Service service = serviceService.showRecord(Integer.valueOf(id));
			serviceService.deleteRecord(service);
			return new ResponseEntity<Service>(service, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Service>(HttpStatus.BAD_REQUEST);
		}
	}
}
