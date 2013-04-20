package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Favorite implements Serializable,HasTime  {
	private static final long serialVersionUID = 1L;
	private Status status;
	private List<Tag> tag;
	private String favorited_time;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Tag> getTag() {
		return tag;
	}

	public void setTag(List<Tag> tag) {
		this.tag = tag;
	}

	public String getFavorited_time() {
		return favorited_time;
	}

	public void setFavorited_time(String favorited_time) {
		this.favorited_time = favorited_time;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public long getTime() {
		return (new Date(favorited_time)).getTime();
	}

}
