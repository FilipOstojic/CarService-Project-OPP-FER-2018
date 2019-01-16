package hr.fer.opp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import hr.fer.opp.model.Role;

public abstract class GenericDAO<T> {

	@Autowired
	protected SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void create(T record) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(record);
		tx.commit();
		session.close();
	}

	public abstract List<T> read();

	public abstract T read(String key);
	
	public T readByName(String name) {
		return null;
	}
	
	public List<T> readByRole(Role role) {
		return null;
	}
	
	public List<T> readByEmail(String email) {
		return null;
	}

	public void update(T record) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(record);
		tx.commit();
		session.close();
	}

	public void delete(T record) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(record);
		tx.commit();
		session.close();
	}
}
