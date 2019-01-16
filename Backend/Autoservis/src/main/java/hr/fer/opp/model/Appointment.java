package hr.fer.opp.model;

import java.util.Date;

import javax.persistence.CascadeType;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dateOfApply")
	private Date dateOfApply;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mechanic_id", nullable = false, foreignKey = @ForeignKey(name = "Fk_mechanic_email"))
	private User mechanic;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "vehicle_id", nullable = false, foreignKey = @ForeignKey(name = "Fk_userVehicle_licencplate"))
	private UserVehicle vehicle;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "service_id", nullable = false, foreignKey = @ForeignKey(name = "Fk_service_name"))
	private Service service;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "repVehicle", nullable = false)
	private boolean repVehicle;
	
	@Column(name = "vehicleStatus", nullable = true)
	private String vehicleStatus;

	public Appointment() {
		// TODO Auto-generated constructor stub
	}

	public Appointment(Date date, User mechanic, UserVehicle vehicle, Service service, String description,
			boolean repVehicle,String vehicleStatus ) {
		this.date = date;
		this.mechanic = mechanic;
		this.vehicle = vehicle;
		this.service = service;
		this.description = description;
		this.repVehicle = repVehicle;
		this.vehicleStatus = vehicleStatus;
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

	public String getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public Date getDateOfApply() {
		return dateOfApply;
	}

	public void setDateOfApply(Date dateOfApply) {
		this.dateOfApply = dateOfApply;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((mechanic == null) ? 0 : mechanic.hashCode());
		result = prime * result + ((vehicle == null) ? 0 : vehicle.hashCode());
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
		Appointment other = (Appointment) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (mechanic == null) {
			if (other.mechanic != null)
				return false;
		} else if (!mechanic.equals(other.mechanic))
			return false;
		if (vehicle == null) {
			if (other.vehicle != null)
				return false;
		} else if (!vehicle.equals(other.vehicle))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return id + " " + date + " " + dateOfApply + " " + mechanic + " " + vehicle.getLicensePlate() + " " + service.getName();
	}
}
