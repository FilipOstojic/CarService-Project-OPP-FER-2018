package hr.fer.opp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import hr.fer.opp.model.Role;
import hr.fer.opp.model.User;

@Component
public class UserDAOImpl extends GenericDAO<User> {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> read() {
		Session session = this.sessionFactory.openSession();
		List<User> userList = session.createQuery("from User").list();
		session.close();
		return userList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User read(String email) {
		Session session = this.sessionFactory.openSession();

		@SuppressWarnings("rawtypes")
		Query query = (Query) session.createQuery("from User where email = :attribute");
		query.setParameter("attribute", email);

		List<User> userList = query.list();
		session.close();
		return userList.isEmpty() ? null : userList.get(0);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<User> readByRole(String roleName) {
		Session session = sessionFactory.openSession();

		Query query = session.createQuery("FROM Role WHERE name = :attribute");
		query.setParameter("attribute", roleName);

		List<Role> roleList = query.list();
		session.close();
		Role role = roleList.isEmpty() ? null : roleList.get(0);
		
		session = sessionFactory.openSession();

		query = (Query) session.createQuery("from User where role = :attribute");
		query.setParameter("attribute", role);

		List<User> userList = query.list();
		session.close();
		return userList;
	}
}
