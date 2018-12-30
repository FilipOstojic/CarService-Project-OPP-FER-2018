package hr.fer.opp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import hr.fer.opp.model.Appointment;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Component
public class AppointmentDAOImpl extends GenericDAO<Appointment>{

	@Override
	public List<Appointment> read() {
		Session session = sessionFactory.openSession();
		List<Appointment> appointmentList = session.createQuery("from Appointment").list();
		session.close();
		return appointmentList;
	}

	@Override
	public Appointment read(String key) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Appointment where id = :attribute");
		query.setParameter("attribute", key);
		List<Appointment> appointmentList = query.list();
		session.close();
		return (appointmentList.size() > 0) ? appointmentList.get(0) : null;
	}
	
	@Override
	public List<Appointment> readByEmail(String mechEmail) {
		Session session = sessionFactory.openSession();

		Query query = session.createQuery("FROM Appointment WHERE mechanic_id = :attribute");
		query.setParameter("attribute", mechEmail);

		List<Appointment> mechAppointments = query.list();
		session.close();
		return mechAppointments;
	}
}
