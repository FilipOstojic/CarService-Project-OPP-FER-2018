package hr.fer.opp.model;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ServiceVehicle")
public class ServiceVehicle extends Vehicle {

	@OneToOne
	@JoinColumn(name = "rentedTo", foreignKey = @ForeignKey(name = "Fk_regUser_email"))
	private User rentedTo;

	public ServiceVehicle() {
		// TODO Auto-generated constructor stub
	}

	public ServiceVehicle(String licensePlate, Model model, String year, User rentedTo) {
		super(licensePlate, model, year);
		this.rentedTo = rentedTo;
	}

	public User getRentedTo() {
		return rentedTo;
	}

	public void setRentedTo(User rentedTo) {
		this.rentedTo = rentedTo;
	}

}
