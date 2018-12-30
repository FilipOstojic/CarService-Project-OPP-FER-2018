package hr.fer.opp.service;

import java.util.List;

import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.dao.GenericDAO;
import hr.fer.opp.model.Appointment;

@Service("appointmentService")
public class AppointmentService {

	@Autowired
	protected GenericDAO<Appointment> dao;
	
	public List<Appointment> listAll() {
		return dao.read();
	}
	
	private boolean exists(Appointment record) {
		List<Appointment> list = dao.read();
		return list.contains(record);
	}
	
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
