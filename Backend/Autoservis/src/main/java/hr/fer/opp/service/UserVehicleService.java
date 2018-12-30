package hr.fer.opp.service;

import java.util.List;

import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.dao.GenericDAO;
import hr.fer.opp.model.UserVehicle;

@Service("userVehicleService")
public class UserVehicleService {

	@Autowired
	protected GenericDAO<UserVehicle> dao;
	
	public List<UserVehicle> listAll() {
		return dao.read();
	}
	
	private boolean exists(UserVehicle userVehicle) {
		List<UserVehicle> list = dao.read();
		return list.contains(userVehicle);
	}
	
	public UserVehicle showRecord(String licensePlate) {
		UserVehicle vehicle = dao.read(licensePlate);
		if (vehicle != null) {
			return vehicle;
		}
		throw new NullPointerException("Cant list null vehicle");
	}

	public void deleteRecord(UserVehicle vehicle) {
		if (exists(vehicle)) {
			dao.delete(vehicle);
		} else {
			throw new IllegalArgumentException("Vehicle with " + vehicle.getLicensePlate() + " does not exist.");
		}
	}

	public boolean createRecord(UserVehicle vehicle) {
		if (vehicle != null && !exists(vehicle) && vehicle.getLicensePlate().length() > 0) {
			dao.create(vehicle);
			return true;
		} else {
			return false;
		}
	}

	public void updateRecord(UserVehicle vehicle) {
		if (!exists(vehicle)) {
			throw new NullPointerException("Cant update null vehicle.");
		}
		dao.update(vehicle);
	}
}
