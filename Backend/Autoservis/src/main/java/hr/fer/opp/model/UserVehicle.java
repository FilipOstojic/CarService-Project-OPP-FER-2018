package hr.fer.opp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UserVehicle")
public class UserVehicle extends Vehicle {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "User_email", foreignKey = @ForeignKey(name = "Fk_regUser_email"))
	private User owner;

	public UserVehicle() {
		// TODO Auto-generated constructor stub
	}

	public UserVehicle(String licensePlate, Model model, String year, User owner) {
		super(licensePlate, model, year);
		this.owner = owner;
	}

}
