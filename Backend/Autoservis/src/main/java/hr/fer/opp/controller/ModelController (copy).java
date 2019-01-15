package hr.fer.opp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hr.fer.opp.model.Model;
import hr.fer.opp.service.ModelService;

@RestController
@CrossOrigin("*")
public class ModelController {

	@Autowired
	private ModelService modelService;

	@RequestMapping(value = "/model", method = RequestMethod.GET)
	public ResponseEntity<List<Model>> listModels() {
		List<Model> list = modelService.listAll();
		if (list != null) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/model", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Model> crateModel(@RequestBody Model model) {
		boolean created = modelService.createRecord(model);
		if (created) {
			return new ResponseEntity<Model>(model, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Model>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/model", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Model> updateModel(@RequestBody Model model) {
		try {
			modelService.updateRecord(model);
			return new ResponseEntity<Model>(model, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Model>(model, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/model/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Model> deleteModel(@PathVariable("id") String id) {
		try {
			Model model = modelService.showRecord(Integer.valueOf(id));
			modelService.deleteRecord(model);
			return new ResponseEntity<Model>(model, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Model>(HttpStatus.BAD_REQUEST);
		}
	}
}
