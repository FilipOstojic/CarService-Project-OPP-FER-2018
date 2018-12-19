package hr.fer.opp.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.fer.opp.model.Service;

@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
@Component
public class ServiceDAOImpl implements GenericDAO<Service> {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void create(Service service) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(service);
		tx.commit();
		session.close();
	}

	@Override
	public List<Service> read() {
		Session session = this.sessionFactory.openSession();
		List<Service> personList = session.createQuery("from Service").list();
		session.close();
		return personList;
	}

	@Override
	public Service read(String key) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Service where id =:atribute ");
		query.setParameter("atribute", key);
		List<Service> serviceList = query.list();
		session.close();
		return (serviceList.size() > 0) ? serviceList.get(0) : null;
	}

	@Override
	public void update(Service service) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(service);
		tx.commit();
		session.close(); 
	}

	@Override
	public void delete(Service service) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(service);
		tx.commit();
		session.close();
	}

}
