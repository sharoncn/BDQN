package rjfsdo.sharoncn.android.BDQN.LeisureLife.Data;

import java.io.Serializable;

/**
 * 电影基类
 * @author sharoncn
 *
 */
public class BaseMovie implements Serializable,HasImage {
	private static final long serialVersionUID = 1L;
	private String id,image;
	private String name,type,player;
	private String time,desc,tlong;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTlong() {
		return tlong;
	}
	public void setTlong(String tlong) {
		this.tlong = tlong;
	}
	@Override
	public String toString() {
		return "id:" + id + ",image:" + image + ",name:" + name + ",type:" + type + 
				",player:" + player + ",time:" + time + ",desc:" + desc + ",tlong:" + tlong;
	}
}
