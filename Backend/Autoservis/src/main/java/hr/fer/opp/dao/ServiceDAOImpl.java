package hr.fer.opp.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import hr.fer.opp.model.Service;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Component
public class ServiceDAOImpl extends GenericDAO<Service> {

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
		Query query = session.createQuery("from Service where id = :attribute");
		query.setParameter("attribute", key);
		List<Service> serviceList = query.list();
		session.close();
		return (serviceList.size() > 0) ? serviceList.get(0) : null;
	}
}
