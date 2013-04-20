package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models;

import java.util.ArrayList;


/**
 * @author sharoncn
 *
 */
public class Province {
	private int id;
	private String name;
	private ArrayList<City> citys;

	public ArrayList<City> getCitys() {
		return citys;
	}

	public void setCitys(ArrayList<City> citys) {
		this.citys = citys;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
		StringBuffer sb = new StringBuffer();
		for(City city:getCitys()){
			sb.append(city.toString());
			sb.append("-");
		}
		String citiesStr = sb.toString();
		return "[ID:" + id + "][name:" + name + "][" + citiesStr.substring(0, citiesStr.length()) + "]";
	}
}
