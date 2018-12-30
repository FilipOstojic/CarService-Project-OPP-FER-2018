package hr.fer.opp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import hr.fer.opp.model.ServiceVehicle;

public class ServiceVehicleDAOImpl extends GenericDAO<ServiceVehicle> {

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceVehicle> read() {
		Session session = sessionFactory.openSession();
		List<ServiceVehicle> serviceVehicles = session.createQuery("from ServiceVehicle").list();
		session.close();
		return serviceVehicles;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ServiceVehicle read(String licensePlate) {
		Session session = sessionFactory.openSession();

		Query query = session.createQuery("from ServiceVehicle where licensePlate = :attribute");
		query.setParameter("attribute", licensePlate);

		List<ServiceVehicle> serviceVehicles = query.list();
		session.close();
		return serviceVehicles.isEmpty() ? null : serviceVehicles.get(0);
	}
}
