package hr.fer.opp.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import hr.fer.opp.model.Role;
import hr.fer.opp.model.User;

public class UserPrincipal implements UserDetails {

	private User user;
	private Collection<? extends GrantedAuthority> auths;
	
	private static final long serialVersionUID = 3080225537073446483L;
	
	public UserPrincipal(User user) {
		this.user = user;
		this.auths = getAuths(user.getRole());
	}

	public User getUser() {
		return user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return auths;
	}

	private Collection<? extends GrantedAuthority> getAuths(Role role) {
		List<SimpleGrantedAuthority> auths = new ArrayList<>();
		if (role.getName().equals("ADMIN")) {
			auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else if (role.getName().equals("MECH")) {
			auths.add(new SimpleGrantedAuthority("ROLE_MECH"));
		} else {
			auths.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return auths;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isConfirmed();
	}
}
