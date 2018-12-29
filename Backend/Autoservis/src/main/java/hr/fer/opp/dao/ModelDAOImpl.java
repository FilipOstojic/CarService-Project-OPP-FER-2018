package hr.fer.opp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import hr.fer.opp.model.Model;

@Component
public class ModelDAOImpl extends GenericDAO<Model> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Model> read() {
		Session session = sessionFactory.openSession();
		List<Model> modelList = session.createQuery("from Model").list();
		session.close();
		return modelList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Model read(String modelID) {
		Session session = sessionFactory.openSession();

		@SuppressWarnings("rawtypes")
		Query query = (Query) session.createQuery("FROM Model WHERE id = :attribute");
		query.setParameter("attribute", Integer.parseInt(modelID));

		List<Model> modelList = query.list();
		session.close();
		return modelList.isEmpty() ? null : modelList.get(0);
	}
}
