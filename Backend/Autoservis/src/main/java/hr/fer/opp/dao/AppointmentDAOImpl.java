package hr.fer.opp.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.fer.opp.model.Appointment;

@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
@Component
public class AppointmentDAOImpl implements GenericDAO<Appointment>{

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void create(Appointment appointment) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(appointment);
		tx.commit();
		session.close();
	}

	@Override
	public List<Appointment> read() {
		Session session = this.sessionFactory.openSession();
		List<Appointment> appointmentList = session.createQuery("from Appointment").list();
		session.close();
		return appointmentList;
	}

	@Override
	public Appointment read(String key) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Appointment where id =:atribute ");
		query.setParameter("atribute", key);
		List<Appointment> appointmentList = query.list();
		session.close();
		return (appointmentList.size() > 0) ? appointmentList.get(0) : null;
	}

	@Override
	public void update(Appointment appointment) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(appointment);
		tx.commit();
		session.close(); 
	}

	@Override
	public void delete(Appointment appointment) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(appointment);
		tx.commit();
		session.close();
	}

}
