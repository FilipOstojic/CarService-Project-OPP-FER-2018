package hr.fer.opp.service;

import org.dom4j.IllegalAddException;
import org.springframework.stereotype.Service;

import hr.fer.opp.model.Appointment;

@Service("appointmentService")
public class AppointmentService extends GenericService<Appointment, Integer> {

	public Appointment showRecord(Integer appointmentID) {
		Appointment appointment = dao.read(String.valueOf(appointmentID));
		if (exists(appointment)) {
			return appointment;
		}
		throw new NullPointerException("Appointment with " + appointmentID + " does not exist.");
	}

	public void deleteRecord(Appointment appointment) {
		if (exists(appointment)) {
			dao.delete(appointment);
		} else {
			throw new IllegalArgumentException("Appointment with " + appointment.getId() + " does not exist.");
		}
	}

	public boolean createRecord(Appointment appointment) {
		if (appointment != null && !exists(appointment) && appointment.getDate() != null) {
			dao.create(appointment);
			return true;
		} else {
			return false;
		}
	}

	public void updateRecord(Appointment appointment) {
		if (!exists(appointment)) {
			throw new NullPointerException("Cant update null appointment.");
		}
		if (appointment.equals(dao.read(String.valueOf(appointment.getId())))) {
			throw new IllegalAddException("No update occured for appointment.");
		}
		dao.update(appointment);
	}

}
