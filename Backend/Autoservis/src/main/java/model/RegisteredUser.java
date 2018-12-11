package model;

import java.util.Set;

import javax.persistence.OneToMany;

public class RegisteredUser extends User {

	@OneToMany(mappedBy = "owner")
	private Set<UserVehicle> vehicles;

	public RegisteredUser(String email, String name, String surname, String mobile, String oib, String password,
			Role role, Set<UserVehicle> vehicles) {
		super(email, name, surname, mobile, oib, password, role);
		this.vehicles = vehicles;
	}

	public Set<UserVehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(Set<UserVehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public void addVehicle(UserVehicle vehicle) {
		if (vehicle == null) {
			return;
		}
		this.vehicles.add(vehicle);
	}

	public void removeVehicle(UserVehicle vehicle) {
		vehicles.remove(vehicle);
	}
}
