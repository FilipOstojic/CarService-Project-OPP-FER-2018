package hr.fer.opp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hr.fer.opp.dao.GenericDAO;
import hr.fer.opp.model.User;
import hr.fer.opp.security.UserPrincipal;

@Service("userService")
public class UserService implements UserDetailsService {

	@Autowired
	protected GenericDAO<User> dao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> listAll() {
		return dao.read();
	}
	
	public List<User> listMechs() {
		return dao.readByRole("MECH");
	}
	
	private boolean exists(User user) {
		List<User> list = dao.read();
		return list.contains(user);
	}
	
	public User showRecord(String key) {
		User user = dao.read(key);
		if (user != null) {
			return user;
		}
		throw new NullPointerException("Cant list null user");
	}

	public void deleteRecord(User user) {
		if (exists(user)) {
			dao.delete(user);
		} else {
			throw new IllegalArgumentException("This user doesn't exist");
		}
	}

	public boolean createRecord(User user) {
		if (user != null && !exists(user) && user.getEmail().length() > 0 
					&& user.getPassword() != null && user.getPassword().length() > 0) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			dao.create(user);
			return true;
		}
		return false;
	}

	public void updateRecord(User user) {
		User temp = dao.read(user.getEmail());
		if (temp == null) {
			throw new NullPointerException("User with this email does not exist.");
		} 
		
		String newPassword = user.getPassword();
		String oldPassword = temp.getPassword();
		
		if (newPassword != null) {
			if (newPassword.length() == 0) {
				throw new IllegalArgumentException("Password must not have length zero.");
			}
			
			if (!newPassword.equals(oldPassword) && !passwordEncoder.matches(newPassword, oldPassword)) {
				user.setPassword(passwordEncoder.encode(newPassword));
			}
		} else {
			user.setPassword(temp.getPassword());
		}
		
		if (temp.isConfirmed()) {
			user.setConfirmed(true);
		}
		dao.update(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = dao.read(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserPrincipal(user);
	}
	
	public Authentication getAuthenticatedUser() {
		SecurityContext sc = SecurityContextHolder.getContext();
		if (sc != null) {
			Authentication auth = sc.getAuthentication();
			if (auth != null) {
				return auth;
			}
		}
		return null;
	}
	
	public User currentlyLoggedIn() {
		try {
			Authentication auth = getAuthenticatedUser();
			UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
			return principal.getUser();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean isAuthenticated() {
		SecurityContext sc = SecurityContextHolder.getContext();
		if (sc == null) return false;
		Authentication ac = sc.getAuthentication();
		if (ac == null) return false;
		return ac.isAuthenticated();
	}
}
