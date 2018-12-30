package hr.fer.opp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import hr.fer.opp.model.Role;

@Component
public class RoleDAOImpl extends GenericDAO<Role> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> read() {
		Session session = sessionFactory.openSession();
		List<Role> roleList = session.createQuery("from Role").list();
		session.close();
		return roleList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Role read(String roleID) {
		Session session = sessionFactory.openSession();

		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("FROM Role WHERE id = :attribute");
		Integer i = Integer.parseInt(roleID);
		query.setParameter("attribute", i);

		List<Role> roleList = query.list();
		session.close();
		return roleList.isEmpty() ? null : roleList.get(0);
	}

	@SuppressWarnings("unchecked")
	public Role readByName(String roleName) {
		Session session = sessionFactory.openSession();

		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("FROM Role WHERE name = :attribute");
		query.setParameter("attribute", roleName);

		List<Role> roleList = query.list();
		session.close();
		return roleList.isEmpty() ? null : roleList.get(0);
	}
}
