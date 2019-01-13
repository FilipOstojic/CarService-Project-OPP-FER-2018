package hr.fer.opp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UserVehicle")
public class UserVehicle extends Vehicle {

	private static final String[] array = { "ZAPRIMLJEN", "NA SERVISU", "SERVIS ZAVRŠEN" };

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "User_email", foreignKey = @ForeignKey(name = "Fk_regUser_email"))
	private User owner;

	@Column(name = "vehicleStatus", nullable = true)
	private String vehicleStatus;

	public UserVehicle() {
		// TODO Auto-generated constructor stub
	}

	public UserVehicle(String licensePlate, Model model, String year, User owner) {
		super(licensePlate, model, year);
		this.owner = owner;
	}

	public UserVehicle(String licensePlate) {
		super(licensePlate, null, null);
	}
	
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public static boolean isValidStatus(String status) {
		if (status == null) {
			return true;
		}
		for (String s : array) {
			if (s.equals(status)) {
				return true;
			}
		}
		return false;
	}

}
