package rjfsdo.sharoncn.android.BDQN.ImageViewer.Models;

import java.io.Serializable;
import java.util.Date;

import rjfsdo.sharoncn.android.BDQN.ImageViewer.Utils.ImageFinder;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Utils.Util;

import android.graphics.Bitmap;

/**
 * Image对象的实体类
 * @author sharoncn
 *
 */
public class ImageInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name, path;
	private Date time;
	private long size;

	public ImageInfo(){
		this(null,null,null,0);
	}
	
	public ImageInfo(String name, String path, Date time, long size) {
		super();
		this.name = name;
		this.path = path;
		this.time = time;
		this.size = size;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ImageInfo) {
			if (((ImageInfo) o).getPath().equals(getPath())) {
				return true;
			} else {
				return false;
			}
		} else {
			return super.equals(o);
		}
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public long getSize() {
		return size;
	}

	public String getFormatedSize() {
		String measurement = "";
		float s = 0;
		
		if(size >= 1024 * 1024 * 1024){
			measurement = "GB";
			s = size / (1024 * 1024 * 1024);
		}else if(size >= 1024 * 1024){
			measurement = "MB";
			s = size / (1024 * 1024);
		}else if(size >= 1024){
			measurement = "KB";
			s = size /1024;
		}
		String strFloat = Util.formatDecimal(s); 
		return strFloat + measurement;
	}
	
	public Date getTime() {
		return time;
	}
	
	public String getFormatedTime() {
		return Util.formatDate(time);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Bitmap getImage() {
		return getImage(80,80);
	}
	
	public Bitmap getImage(int width,int height) {
		return ImageFinder.inflateImage(this,width,height);
	}
}
