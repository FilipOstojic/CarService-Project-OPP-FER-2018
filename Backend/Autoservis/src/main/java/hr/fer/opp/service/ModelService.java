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
	private GenericDAO<Model> modelDAO;

	public List<Model> listAll() {
		return modelDAO.read();
	}

	public Model listModel(int id) {
		Model model = modelDAO.read(String.valueOf(id));
		if (modelExists(model)) {
			return model;
		}
		throw new NullPointerException("Model with " + id + " does not exist.");
	}

	public void deleteModel(Model model) {
		if (modelExists(model)) {
			modelDAO.delete(model);
		} else {
			throw new IllegalArgumentException("Model with " + model.getId() + " does not exist.");
		}
	}

	public boolean createModel(Model model) {
		if (model != null && !modelExists(model) && model.getName().length() > 0) {
			modelDAO.create(model);
			return true;
		} else {
			return false;
		}
	}

	public void updateModel(Model model) {
		if (!modelExists(model)) {
			throw new NullPointerException("Cant update null model.");
		}
		if (model.equals(modelDAO.read(String.valueOf(model.getId())))) {
			throw new IllegalAddException("No update occured for model.");
		}
		modelDAO.update(model);
	}

	private boolean modelExists(Model model) {
		List<Model> modelList = modelDAO.read();
		return modelList.contains(model);
	}
}
