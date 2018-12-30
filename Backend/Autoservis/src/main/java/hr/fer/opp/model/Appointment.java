package hr.fer.opp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Appointment")
public class Appointment {

	@Id
	@GeneratedValue
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date", nullable = false)
	private Date date;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mechanic_id", nullable = false, foreignKey = @ForeignKey(name = "Fk_mechanic_email"))
	private User mechanic;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_id", nullable = false, foreignKey = @ForeignKey(name = "Fk_userVehicle_licencplate"))
	private UserVehicle vehicle;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_id", nullable = false, foreignKey = @ForeignKey(name = "Fk_service_name"))
	private Service service;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "repVehicle", nullable = false)
	private boolean repVehicle;

	public Appointment() {
		// TODO Auto-generated constructor stub
	}

	public Appointment(Date date, User mechanic, UserVehicle vehicle, Service service, String description,
			boolean repVehicle) {
		this.date = date;
		this.mechanic = mechanic;
		this.vehicle = vehicle;
		this.service = service;
		this.description = description;
		this.repVehicle = repVehicle;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getMechanic() {
		return mechanic;
	}

	public void setMechanic(User mechanic) {
		this.mechanic = mechanic;
	}

	public UserVehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(UserVehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isRepVehicle() {
		return repVehicle;
	}

	public void setRepVehicle(boolean repVehicle) {
		this.repVehicle = repVehicle;
	}

	public int getId() {
		return id;
	}

}
