package hr.fer.opp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hr.fer.opp.model.Autoservice;
import hr.fer.opp.service.AutoserviceService;

@RestController
@CrossOrigin("*")
public class AutoserviceController {
	
	@Autowired
	AutoserviceService autoserviceService;
	
	@RequestMapping(value = "/autoservice", method = RequestMethod.GET)
	public ResponseEntity<Autoservice> showInfo() {
		Autoservice autoservice = autoserviceService.showInfo();
		if (autoservice != null) {
			return new ResponseEntity<>(autoservice, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(autoservice, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/autoservice", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Autoservice> updateInfo(@RequestBody Autoservice autoservice) {
		try {
			autoserviceService.updateRecord(autoservice);
			return new ResponseEntity<Autoservice>(autoservice, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Autoservice>(autoservice, HttpStatus.BAD_REQUEST);
		}
	}
}
