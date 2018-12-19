package hr.fer.opp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.fer.opp.model.Model;

@Component
public class ModelDAOImpl implements GenericDAO<Model> {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void create(Model model) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(model);
		tx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Model> read() {
		Session session = this.sessionFactory.openSession();
		List<Model> modelList = session.createQuery("from Model").list();
		session.close();
		return modelList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Model read(String modelID) {
		Session session = this.sessionFactory.openSession();

		@SuppressWarnings("rawtypes")
		Query query = (Query) session.createQuery("from Model where id =:atribute ");
		query.setParameter("atribute", modelID);

		List<Model> modelList = query.list();
		session.close();
		return modelList.isEmpty() ? null : modelList.get(0);
	}

	@Override
	public void update(Model model) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(model);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Model model) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(model);
		tx.commit();
		session.close();
	}

}
