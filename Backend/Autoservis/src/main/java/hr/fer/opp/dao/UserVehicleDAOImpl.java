package hr.fer.opp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import hr.fer.opp.model.UserVehicle;

public class UserVehicleDAOImpl extends GenericDAO<UserVehicle> {

	@SuppressWarnings("unchecked")
	@Override
	public List<UserVehicle> read() {
		Session session = sessionFactory.openSession();
		List<UserVehicle> userVehicles = session.createQuery("from UserVehicle").list();
		session.close();
		return userVehicles;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public UserVehicle read(String licensePlate) {
		Session session = sessionFactory.openSession();

		Query query = session.createQuery("FROM UserVehicle WHERE licensePlate = :attribute");
		query.setParameter("attribute", licensePlate);

		List<UserVehicle> userVehicles = query.list();
		session.close();
		return userVehicles.isEmpty() ? null : userVehicles.get(0);
	}
}
