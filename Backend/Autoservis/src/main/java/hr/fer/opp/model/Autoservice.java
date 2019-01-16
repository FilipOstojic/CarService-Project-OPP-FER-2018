package hr.fer.opp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Autoservice")
public class Autoservice {

	@Id
	@GeneratedValue
	private int id;
	
	@Size(max = 50)
	@Column(name = "name")
	private String name;

	@Size(max = 50)
	@Column(name = "address", nullable = false)
	private String address;
	
	@Email
	@Size(max = 50)
	@Column(name = "email", nullable = false)
	private String email;
	
	@Size(min = 8, max = 15)
	@Column(name = "mobile", nullable = false)
	private String mobile;
	
	@Size(max = 11)
	@Column(name = "oib", unique = true)
	private String oib;
	
	public Autoservice() {
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Autoservice other = (Autoservice) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
