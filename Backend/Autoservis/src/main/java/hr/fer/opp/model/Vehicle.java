package hr.fer.opp.model;

import javax.persistence.Column;
import javax.persistence.Id;

public abstract class Vehicle {

	@Id
	@Column(name = "licensePlate", nullable = false)
	private String licensePlate;

	@Column(name = "model", nullable = false)
	private Model model;

	@Column(name = "year", nullable = false)
	private String year;

	public Vehicle(String licensePlate, Model model, String year) {
		super();
		this.licensePlate = licensePlate;
		this.model = model;
		this.year = year;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((licensePlate == null) ? 0 : licensePlate.hashCode());
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
		Vehicle other = (Vehicle) obj;
		if (licensePlate == null) {
			if (other.licensePlate != null)
				return false;
		} else if (!licensePlate.equals(other.licensePlate))
			return false;
		return true;
	}

}
