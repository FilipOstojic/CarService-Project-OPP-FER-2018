package hr.fer.opp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import hr.fer.opp.model.UserVehicle;

public class UserVehicleDAOImpl implements GenericDAO<UserVehicle> {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void create(UserVehicle vehicle) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(vehicle);
		tx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserVehicle> read() {
		Session session = this.sessionFactory.openSession();
		List<UserVehicle> userVehicles = session.createQuery("from UserVehicle").list();
		session.close();
		return userVehicles;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public UserVehicle read(String licensePlate) {
		Session session = sessionFactory.openSession();

		Query query = session.createQuery("from Model where id =:atribute ");
		query.setParameter("atribute", licensePlate);

		List<UserVehicle> userVehicles = query.list();
		session.close();
		return userVehicles.isEmpty() ? null : userVehicles.get(0);
	}

	@Override
	public void update(UserVehicle vehicle) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(vehicle);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(UserVehicle vehicle) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(vehicle);
		tx.commit();
		session.close();
	}
}
