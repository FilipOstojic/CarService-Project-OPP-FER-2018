package hr.fer.opp.service;

import org.dom4j.IllegalAddException;
import org.springframework.stereotype.Service;

import hr.fer.opp.model.ServiceVehicle;

@Service("serviceVehicleService")
public class ServiceVehicleService extends GenericService<ServiceVehicle, String> {

	@Override
	public ServiceVehicle showRecord(String licensePlate) {
		ServiceVehicle vehicle = dao.read(licensePlate);
		if (vehicle != null) {
			return vehicle;
		}
		throw new NullPointerException("Cant list null vehicle");
	}

	@Override
	public void deleteRecord(ServiceVehicle vehicle) {
		if (exists(vehicle)) {
			dao.delete(vehicle);
		} else {
			throw new IllegalArgumentException("Vehicle with " + vehicle.getLicensePlate() + " does not exist.");
		}
	}

	@Override
	public boolean createRecord(ServiceVehicle vehicle) {
		if (vehicle != null && !exists(vehicle) && vehicle.getLicensePlate().length() > 0) {
			dao.create(vehicle);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void updateRecord(ServiceVehicle vehicle) {
		if (!exists(vehicle)) {
			throw new NullPointerException("Cant update null vehicle.");
		}
		if (vehicle.equals(dao.read(vehicle.getLicensePlate()))) {
			throw new IllegalAddException("No update occured for vehicle.");
		}
		dao.update(vehicle);
	}
}