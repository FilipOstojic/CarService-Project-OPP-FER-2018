package hr.fer.opp.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	@Id
	@Email
	@Size(max = 50)
	@Column(name = "email", nullable = false)
	private String email;

	@Size(max = 20)
	@Column(name = "name", nullable = false)
	private String name;

	@Size(max = 20)
	@Column(name = "surname", nullable = false)
	private String surname;

	@Size(min = 8, max = 15)
	@Column(name = "mobile", nullable = false)
	private String mobile;

	@Size(max = 11)
	@Column(name = "oib", unique = true )
	private String oib;

	@Column(name = "password", nullable = false)
	private String password;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleId", foreignKey = @ForeignKey(name = "Fk_role_id"))
	private Role role;

	@OneToOne(mappedBy = "mechanic")
	private Appointment appointment;

	//nullable  true
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
	private Set<UserVehicle> vehicles;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "rentedTo")
	private ServiceVehicle serviceVehicle;
	
	
	public User(String email, String name, String surname, String mobile, String oib, String password, Role role) {
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.mobile = mobile;
		this.oib = oib;
		this.password = password;
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOib() {
		return oib;
	}

	public void setOib(String oib) {
		this.oib = oib;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
