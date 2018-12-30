package hr.fer.opp.service;

import java.util.List;

import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;

import hr.fer.opp.dao.GenericDAO;
import hr.fer.opp.model.Service;

@org.springframework.stereotype.Service("serviceService")
public class ServiceService  {

	@Autowired
	protected GenericDAO<Service> dao;
	
	public List<Service> listAll() {
		return dao.read();
	}
	
	private boolean exists(Service service) {
		List<Service> list = dao.read();
		return list.contains(service);
	}
	
	public Service showRecord(Integer serviceId) {
		Service service = dao.read(String.valueOf(serviceId));
		if (service != null) {
			return service;
		}
		throw new NullPointerException("Service with " + serviceId + " does not exist.");
	}

	public Service showRecordByName(String serviceName) {
		Service service = dao.readByName(serviceName);
		if (service != null) {
			return service;
		}
		throw new NullPointerException("Service with " + serviceName + " does not exist.");
	}

	public void deleteRecord(Service service) {
		if (exists(service)) {
			dao.delete(service);
		} else {
			throw new IllegalArgumentException("This service doesn't exist");
		}
	}

	public boolean createRecord(Service service) {
		if (service != null && !exists(service) && service.getName().length() > 0) {
			dao.create(service);
			return true;
		}
		return false;
	}

	public void updateRecord(Service service) {
		Service temp = dao.read(String.valueOf(service.getId()));

		if (temp == null) {
			throw new NullPointerException("Cant update null service");
		}
		if (temp.equals(service)) {
			throw new IllegalAddException("No update occured for service");
		}
		dao.update(service);
	}

}
