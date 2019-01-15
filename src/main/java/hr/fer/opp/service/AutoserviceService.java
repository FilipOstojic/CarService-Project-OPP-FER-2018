package hr.fer.opp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.dao.GenericDAO;
import hr.fer.opp.model.Autoservice;

@Service("autoserviceService")
public class AutoserviceService {

	@Autowired
	protected GenericDAO<Autoservice> dao;
	
	private boolean exists(Autoservice record) {
		List<Autoservice> list = dao.read();
		return list.contains(record);
	}
	
	public Autoservice showInfo() {
		List<Autoservice> list = dao.read();
		if (list.isEmpty()) {
			throw new IllegalArgumentException("There is no autoservice info");
		}
		return list.get(0);
	}

	public void updateRecord(Autoservice autoservice) {
		if (!exists(autoservice)) {
			throw new NullPointerException("Cant update null autoservice.");
		}
		dao.update(autoservice);
	}
}
