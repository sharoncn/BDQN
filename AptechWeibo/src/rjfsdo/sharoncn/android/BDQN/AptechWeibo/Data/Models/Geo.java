package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models;

import java.io.Serializable;

public class Geo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String type;
	private double[] coordinates;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(double[] coordinates) {
		this.coordinates = coordinates;
	}
}
