package hr.fer.opp.controller;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hr.fer.opp.model.Appointment;
import hr.fer.opp.model.Service;
import hr.fer.opp.model.ServiceVehicle;
import hr.fer.opp.model.User;
import hr.fer.opp.model.UserVehicle;
import hr.fer.opp.service.AppointmentService;
import hr.fer.opp.service.AutoserviceService;
import hr.fer.opp.service.ServiceVehicleService;
import hr.fer.opp.service.UserService;
import hr.fer.opp.util.Util;

@RestController
@CrossOrigin("*")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private AutoserviceService autoserviceService;
	
	@Autowired
	private ServiceVehicleService serviceVehicleService;
	
	@Autowired
	private UserService userService;

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
		List<Appointment> list = new ArrayList<>();
		Calendar today = Calendar.getInstance();
		today.setTime(new Date());
		for (Appointment a : appointmentService.listAllFromUser(mechEmail)) {
			Calendar tmp = Calendar.getInstance();
			tmp.setTime(a.getDate());
			if (Util.areEqualDay(today, tmp)) {
				list.add(a);
			}
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/appointment/pdf/{id}", method = RequestMethod.GET)
	public ResponseEntity<ByteArrayResource> generatePDF(@PathVariable("id") String appointmentId) {
		System.out.println("ušao");
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
	
	@RequestMapping(value = "/appointment/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<Appointment> getAppointment(@PathVariable("id") String appointmentId) {
		Appointment appointment = appointmentService.showRecord(Integer.valueOf(appointmentId));
		if (appointment == null) {
			return new ResponseEntity<Appointment>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/appointment/available", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getAvailableAppointments() {
		List<String> availableAppointments = new ArrayList<>();
		Map<String, Integer> numOfApp = Util.getAvailable(appointmentService.listAll());
		int numOfMechs = userService.listMechs().size();
		
		for (String date : numOfApp.keySet()) {
			if (numOfApp.get(date) < numOfMechs) {
				availableAppointments.add(date);
			}
		}
	
		return new ResponseEntity<>(availableAppointments, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/appointment/available/{email}", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getUserAvailableAppointments(@PathVariable("email") String mechEmail) {
		List<String> availableAppointments;
		try {
			availableAppointments = Util.getAvailableAppointments(appointmentService.listAllFromUser(mechEmail));
		} catch (ParseException e) {
			e.printStackTrace();
			availableAppointments = null;
		}
		return new ResponseEntity<>(availableAppointments, HttpStatus.OK);
	}

	@RequestMapping(value = "/appointment", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Appointment> crateAppointment(@RequestBody Map<String, String> params) {
		Appointment appointment = new Appointment();
		Date date;
		try {
			date = Util.SDF.parse(params.get("date"));
			appointment.setDate(date);
		} catch (ParseException e) {
			return new ResponseEntity<Appointment>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		appointment.setDescription(params.get("description"));
		appointment.setRepVehicle(Boolean.parseBoolean(params.get("repVehicle")));
		appointment.setService(new Service(Integer.parseInt(params.get("service"))));
		appointment.setVehicle(new UserVehicle(params.get("vehicle")));
		appointment.setDateOfApply(new Date());
		
		String mechanicEmail = params.get("mechanic");
		if (mechanicEmail == null || mechanicEmail.equals("null")) {
			for (User mech : userService.listMechs()) {
				if (Util.isMechFree(date, appointmentService.listAllFromUser(mech.getEmail()))) {
					mechanicEmail = mech.getEmail();
					break;
				}
			}
		}
		
		if (mechanicEmail == null) {
			return new ResponseEntity<Appointment>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		appointment.setMechanic(new User(mechanicEmail));
		
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
	
	@RequestMapping(value = "/appointment/finalize/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Appointment> finalizeAppointment(@PathVariable("id") String id) {
		try {
			Appointment appointment = appointmentService.showRecord(Integer.valueOf(id));
			if (appointment.isRepVehicle()) {
				List<ServiceVehicle> vehicles = serviceVehicleService.listAll();
				for (ServiceVehicle v : vehicles) {
					if (v.getRentedTo() == null) {
						v.setRentedTo(appointment.getVehicle().getOwner());
						serviceVehicleService.updateRecord(v);
						break;
					}
				}
			}
			appointmentService.deleteRecord(appointment);
			return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Appointment>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@Scheduled(cron = "0 0 8 * * *")
	public void sendPeriodicEmail() {
		List<Appointment> appointments = appointmentService.listAll();
		for (Appointment appointment : appointments) {
				Date today = new Date();
			Calendar calToday = Calendar.getInstance();
			calToday.setTime(today);

			Calendar calDate = Calendar.getInstance();
			calDate.setTime(appointment.getDate());
			
			Calendar calDateOfApply = Calendar.getInstance();
			calDateOfApply.setTime(appointment.getDateOfApply());
			
			if ((calDate.get(Calendar.DATE) - calDateOfApply.get(Calendar.DATE)) >= 3 
					&& (calDate.get(Calendar.DATE) - calToday.get(Calendar.DATE)) == 1) {
				Util.sendPeriodicEmail(appointment);
			}
		}
	}
}
