package hr.fer.opp.controller;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hr.fer.opp.model.Appointment;
import hr.fer.opp.service.AppointmentService;
import hr.fer.opp.service.AutoserviceService;
import hr.fer.opp.util.Util;

@RestController
@CrossOrigin("*")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private AutoserviceService autoserviceService;

	@RequestMapping(value = "/appointment", method = RequestMethod.GET)
	public ResponseEntity<List<Appointment>> listAppointments() {
		List<Appointment> list = appointmentService.listAll();
		if (list != null) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/appointment/{email}", method = RequestMethod.GET)
	public ResponseEntity<List<Appointment>> listAppointments(@PathVariable("email") String mechEmail) {
		List<Appointment> list = appointmentService.listAllFromUser(mechEmail);
		if (list != null) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/appointment/pdf/{id}", method = RequestMethod.GET)
	public ResponseEntity<ByteArrayResource> generatePDF(@PathVariable("id") String appointmentId) {
		try {
			Appointment appointment = appointmentService.showRecord(Integer.valueOf(appointmentId));
			PDDocument document = Util.generatePDF(appointment, autoserviceService.showInfo());
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			document.save(byteArrayOutputStream);
			document.close();
			
			byte[] data = byteArrayOutputStream.toByteArray();
			ByteArrayResource resource = new ByteArrayResource(data);
			return ResponseEntity.ok()
		            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=form" + appointmentId + ".pdf") 
		            .contentType(MediaType.APPLICATION_PDF).contentLength(data.length)
		            .body(resource);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(null);
		}

	}

	@RequestMapping(value = "/appointment", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Appointment> crateAppointment(@RequestBody Appointment appointment) {
		//TODO check vehStatus 
		boolean created = appointmentService.createRecord(appointment);
		if (created) {
			return new ResponseEntity<Appointment>(appointment, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Appointment>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/appointment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Appointment> updateAppointment(@RequestBody Appointment appointment) {
		try {
			appointmentService.updateRecord(appointment);
			return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Appointment>(appointment, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/appointment/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Appointment> deleteAppointment(@PathVariable("id") String id) {
		try {
			Appointment appointment = appointmentService.showRecord(Integer.valueOf(id));
			appointmentService.deleteRecord(appointment);
			return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Appointment>(HttpStatus.BAD_REQUEST);
		}
	}
}
