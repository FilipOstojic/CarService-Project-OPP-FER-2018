package hr.fer.opp.service;


import java.util.List;

import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.dao.GenericDAO;
import hr.fer.opp.model.Model;

@Service("modelService")
public class ModelService {

	@Autowired
	protected GenericDAO<Model> dao;
	
	public List<Model> listAll() {
		return dao.read();
	}
	
	private boolean exists(Model model) {
		List<Model> list = dao.read();
		return list.contains(model);
	}
	
	public Model showRecord(Integer modelID) {
		Model model = dao.read(String.valueOf(modelID));
		if (exists(model)) {
			return model;
		}
		throw new NullPointerException("Model with " + modelID + " does not exist.");
	}
	
	public Model showRecordByName(String modelName) {
		Model model = dao.readByName(modelName);
		if (exists(model)) {
			return model;
		}
		throw new NullPointerException("Model with " + modelName + " does not exist.");
	}

	public void deleteRecord(Model model) {
		if (exists(model)) {
			dao.delete(model);
		} else {
			throw new IllegalArgumentException("Model with " + model.getId() + " does not exist.");
		}
	}

	public boolean createRecord(Model model) {
		if (model != null && !exists(model) && model.getName().length() > 0) {
			dao.create(model);
			return true;
		} else {
			return false;
		}
	}

	public void updateRecord(Model model) {
		if (!exists(model)) {
			throw new NullPointerException("Cant update null model.");
		}
		if (model.equals(dao.read(String.valueOf(model.getId())))) {
			throw new IllegalAddException("No update occured for model.");
		}
		dao.update(model);
	}
}
