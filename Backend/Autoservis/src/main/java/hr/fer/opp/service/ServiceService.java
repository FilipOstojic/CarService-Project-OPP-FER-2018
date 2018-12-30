package hr.fer.opp.service;

import org.dom4j.IllegalAddException;
import hr.fer.opp.model.Service;


@org.springframework.stereotype.Service("serviceService")
public class ServiceService extends GenericService<Service, Integer>{

	public Service showRecord(Integer serviceId) {
		Service service = dao.read(String.valueOf(serviceId));
		if (service != null) {
			return service;
		}
		throw new NullPointerException("Cant list null service");
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
