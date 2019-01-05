package hr.fer.opp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import hr.fer.opp.model.Autoservice;

public class AutoserviceDAOImpl extends GenericDAO<Autoservice> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Autoservice> read() {
		Session session = sessionFactory.openSession();
		List<Autoservice> autoserviceList = session.createQuery("from Autoservice").list();
		session.close();
		return autoserviceList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Autoservice read(String key) {
		Session session = sessionFactory.openSession();

		@SuppressWarnings("rawtypes")
		Query query = (Query) session.createQuery("FROM Autoservice WHERE name = :attribute");
		query.setParameter("attribute", key);

		List<Autoservice> modelList = query.list();
		session.close();
		return modelList.isEmpty() ? null : modelList.get(0);
	}
}
