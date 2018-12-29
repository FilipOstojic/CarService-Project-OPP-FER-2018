package hr.fer.opp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import hr.fer.opp.dao.GenericDAO;

public abstract class GenericService<R, S> {

	@Autowired
	protected GenericDAO<R> dao;
	
	public List<R> listAll() {
		return dao.read();
	}

	public abstract R showRecord(S key);

	public abstract void deleteRecord(R record);

	public abstract boolean createRecord(R record);

	public abstract void updateRecord(R record);

	protected boolean exists(R record) {
		List<R> list = dao.read();
		return list.contains(record);
	}
	
}
