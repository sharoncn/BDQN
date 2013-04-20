package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Models;

import java.io.Serializable;

public class FileInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String type, size, date, path, name;
	private int icon;

	public FileInfo() {
	}

	public FileInfo(String type, String size, String date, String path, String name, int icon) {
		super();
		this.type = type;
		this.size = size;
		this.date = date;
		this.path = path;
		this.name = name;
		this.icon = icon;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "name:" + name + "  path:" + path + "  type:" + type + "  size:" + size + "  date:" + date + "  icon:"
				+ icon;
	}
}
