package hr.fer.opp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.fer.opp.model.Role;

@Component
public class RoleDAOImpl implements GenericDAO<Role> {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void create(Role role) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(role);
		tx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> read() {
		Session session = this.sessionFactory.openSession();
		List<Role> roleList = session.createQuery("from Role").list();
		session.close();
		return roleList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Role read(String roleID) {
		Session session = sessionFactory.openSession();
		
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("FROM Role WHERE id = :atribute");
		Integer i = Integer.parseInt(roleID);
		query.setParameter("atribute", i);
	
		List<Role> roleList = query.list();	
		session.close();
		return roleList.isEmpty() ? null : roleList.get(0);
	}

	@Override
	public void update(Role role) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(role);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Role role) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(role);
		tx.commit();
		session.close();
	}

}
