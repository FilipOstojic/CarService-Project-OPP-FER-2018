package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UserVehicle")
public class UserVehicle extends Vehicle {

	@Column(name = "owner", nullable = false)
	@ManyToOne
	private RegisteredUser owner;

	public UserVehicle(String licensePlate, Model model, String year, RegisteredUser owner) {
		super(licensePlate, model, year);
		this.owner = owner;
	}

}
