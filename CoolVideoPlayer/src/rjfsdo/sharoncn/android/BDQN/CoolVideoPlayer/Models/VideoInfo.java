package rjfsdo.sharoncn.android.BDQN.CoolVideoPlayer.Models;

import android.graphics.Bitmap;

public class VideoInfo {
	private String name;

	private String size;

	private Bitmap thumbnail;
	
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public String getSize() {
		return size;
	}

	public Bitmap getThumbnail() {
		return thumbnail;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSize(String size) {
		this.size = size;
	}
	public void setThumbnail(Bitmap thumbnail) {
		this.thumbnail = thumbnail;
	}

}
