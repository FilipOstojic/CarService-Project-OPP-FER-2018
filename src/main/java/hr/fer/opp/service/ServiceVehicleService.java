package hr.fer.opp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.dao.GenericDAO;
import hr.fer.opp.model.ServiceVehicle;

@Service("serviceVehicleService")
public class ServiceVehicleService {

	@Autowired
	protected GenericDAO<ServiceVehicle> dao;
	
	public List<ServiceVehicle> listAll() {
		return dao.read();
	}
	
	private boolean exists(ServiceVehicle serviceVehicle) {
		List<ServiceVehicle> list = dao.read();
		return list.contains(serviceVehicle);
	}

	public ServiceVehicle showRecord(String licensePlate) {
		ServiceVehicle vehicle = dao.read(licensePlate);
		if (vehicle != null) {
			return vehicle;
		}
		throw new NullPointerException("Cant list null vehicle");
	}

	public void deleteRecord(ServiceVehicle vehicle) {
		if (exists(vehicle)) {
			dao.delete(vehicle);
		} else {
			throw new IllegalArgumentException("Vehicle with " + vehicle.getLicensePlate() + " does not exist.");
		}
	}

	public boolean createRecord(ServiceVehicle vehicle) {
		if (vehicle != null && !exists(vehicle) && vehicle.getLicensePlate().length() > 0) {
			dao.create(vehicle);
			return true;
		} else {
			return false;
		}
	}

	public void updateRecord(ServiceVehicle vehicle) {
		if (!exists(vehicle)) {
			throw new NullPointerException("Cant update null vehicle.");
		}
		dao.update(vehicle);
	}
}