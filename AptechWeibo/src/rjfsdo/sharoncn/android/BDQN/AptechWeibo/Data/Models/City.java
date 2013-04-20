package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models;

public class City {
	private String id, name;
	
	public City(){}

	public City(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return id + ":" + name;
	}

}
