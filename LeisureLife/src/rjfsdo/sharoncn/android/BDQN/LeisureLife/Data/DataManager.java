package rjfsdo.sharoncn.android.BDQN.LeisureLife.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Listener.OnComplatedListener;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Service.DataService;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;

/**
 * 数据管理器，负责网络数据获取操作。
 * Activities向DataManager请求数据，DataManager转发请求给DataService，DataService与网络模块
 * 交互。DataManager基本什么都没做。就是转发了一下获取网络数据的请求，感觉大材小用了。
 * 但是如果将Preference的功能和数据库的功能开放都放入DataManager，感觉DataManager又太臃肿了。
 * 它内部本身就放入了很多的内部类。
 * @author sharoncn
 *
 */
public class DataManager {
	public static final int FLAG_MOVIE = 1;
	public static final int FLAG_WILLMOVIE = 2;
	public static final int FLAG_DISPLAY = 3;
	public static final int FLAG_DELICACIES = 4;
	public static final int FLAG_GET_RECOMMAND = 5;
	public static final int FLAG_PKO = 6;
	public static final int FLAG_PLAY = 7;
	public static final int FLAG_CONCERT = 8;
	public static final int FLAG_MUSIC = 9;
	public static final int FLAG_MOVIE_DETAIL = 11;
	public static final int FLAG_WILLMOVIE_DETAIL = 21;
	public static final int FLAG_DISPLAY_DETAIL = 31;
	public static final int FLAG_DELICACIES_DETAIL = 41;
	public static final int FLAG_SEND_RECOMMAND = -3;
	public static final int FLAG_PKO_DETAIL = 61;
	public static final int FLAG_PLAY_DETAIL = 71;
	public static final int FLAG_CONCERT_DETAIL = 81;
	public static final int FLAG_MUSIC_DETAIL = 91;
	public static final String FLAG_ISSUCCESS = "isSuccess";
	public static final String FLAG_DATA = "data";
	private static final String TAG = "DataManager";
	public static final int FLAG_TOAST_MSG = 20001;
	public static final String FLAG_MSG = "msg";
	
	private static DataManager me;

	private static DataService dataService;

	//缓存获取的图片
	private static Map<String,Drawable> images = new HashMap<String,Drawable>();
	
	static{
		me = new DataManager();
	}
	
	public static DataManager getInstance(Context context){
		return me;
	}
	
	public void setDataService(DataService _dataService){
		dataService = _dataService;
	}
	
//	没有其他类需要和DataService互交，只有DataManager，所以不需要提供这个接口。
//	public DataService getDataService() {
//		return dataService;
//	}
	
	/**
	 * 获得图片
	 * @param index   通常为list填充图片，目前设计，这个index并不是必须的
	 * @param imageId 图片id
	 * @param l       当获取完图片通知这个监听器
	 */
	public void getImage(int index,String imageId,OnComplatedListener l){
		Log.i(TAG,"getImage index:" + index + "  imageid:" + imageId);
		if(images.containsKey(imageId) && l != null){
			l.onComplated(index, imageId);
		}
		if(dataService != null){
			new ImageThread(index,imageId,l).start();
		}
	}
	
	/**
	 * 从图片缓存中获取图片
	 * @param imageId  图片的ID
	 * @return  图片或者null
	 */
	public Drawable getImage(String imageId){
		if(images.containsKey(imageId)){
			return images.get(imageId);
		}
		return null;
	}
	
	class ImageThread extends Thread{
		private String imageId;
		private OnComplatedListener l;
		private int index;
		
		public ImageThread(int index,String imageId,OnComplatedListener l){
			this.l = l;
			this.imageId = imageId;
			this.index = index;
		}
		@Override
		public void run() {
			Log.v(TAG,"ImageThread Running");
			synchronized (images) {
				Log.v(TAG,"ImageThread 锁定images");
				images.put(imageId, dataService.getImage(imageId));
			}
			Log.v(TAG,"ImageThread 释放images");
			if(l != null){
				l.onComplated(index, imageId);
			}
		}
	}
	
	/**
	 * 获取详细信息
	 * @see DataService#getDetail(int, Handler, Map, Object)
	 * @param flag
	 * @param handler
	 * @param args
	 * @param obj
	 * @throws Exception
	 */
	public void getDataDetail(int flag,Handler handler,Map<String,String> args,Object obj) throws Exception{
		if(flag < FLAG_MOVIE_DETAIL){
			throw new Exception("Please choose getData method");
		}
		
		if(dataService != null){
			dataService.getDetail(flag, handler, args, obj);
		}else{
			Log.v(TAG,"dataService is null");
		}
	}
	
	/**
	 * 获取列表
	 * @see DataService#getList(Class, int, Handler, Map)
	 * @param clazz
	 * @param flag
	 * @param handler
	 * @param args
	 * @throws Exception
	 */
	public void getData(Class<?> clazz,int flag,Handler handler,Map<String,String> args) throws Exception{
		if(flag > FLAG_MOVIE_DETAIL){
			throw new Exception("Please choose getDataDetail method");
		}
		if(dataService != null){
			dataService.getList(clazz, flag, handler, args);
		}else{
			Log.v(TAG,"dataService is null");
		}
	}

	
	/**
	 * 电影
	 */
	public static class Movie extends BaseMovie{
		private static final long serialVersionUID = 1L;
	}

	/**
	 * 即将上映
	 */
	public static class WillMovie extends BaseMovie{
		private static final long serialVersionUID = 1L;
	}

	/**
	 * 展览
	 */
	public static class Display implements Serializable,HasImage {
		private static final long serialVersionUID = 1L;
		private String id,image;
		private String name,time,addr,call,host,desc;
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
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getAddr() {
			return addr;
		}
		public void setAddr(String addr) {
			this.addr = addr;
		}
		public String getCall() {
			return call;
		}
		public void setCall(String call) {
			this.call = call;
		}
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		@Override
		public String toString() {
			return "id:" + id + ",image:" + image + ",name:" + name + ",time:" + time + 
					",addr:" + addr + ",call:" + call + ",desc:" + desc + ",host:" + host;
		}
	}

	/**
	 * 美食
	 */
	public static class Delicacies implements Serializable,HasImage {
		private static final long serialVersionUID = 1L;
		private String id,image,avg;
		private String label,addr,name,call;
		private String mapx,mapy;
		private ArrayList<Food> food;
		public static class Food implements Serializable , HasImage{
			private static final long serialVersionUID = 1L;
			private String name;
			private String image,oprice,nprice;
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getImage() {
				return image;
			}
			public void setImage(String image) {
				this.image = image;
			}
			public String getOprice() {
				return oprice;
			}
			public void setOprice(String oprice) {
				this.oprice = oprice;
			}
			public String getNprice() {
				return nprice;
			}
			public void setNprice(String nprice) {
				this.nprice = nprice;
			}
			
		}
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
		public String getAvg() {
			return avg;
		}
		public void setAvg(String avg) {
			this.avg = avg;
		}
		public String getLabel() {
			return label;
		}
		public void setLabel(String lable) {
			this.label = lable;
		}
		public String getAddr() {
			return addr;
		}
		public void setAddr(String addr) {
			this.addr = addr;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCall() {
			return call;
		}
		public void setCall(String call) {
			this.call = call;
		}
		public String getMapx() {
			return mapx;
		}
		public void setMapx(String mapx) {
			this.mapx = mapx;
		}
		public String getMapy() {
			return mapy;
		}
		public void setMapy(String mapy) {
			this.mapy = mapy;
		}
		public ArrayList<Food> getFood() {
			return food;
		}
		public void setFood(ArrayList<Food> foods) {
			this.food = foods;
		}
	}

	//评论
	public static class Recommend implements Serializable{
		private static final long serialVersionUID = 1L;
		private String id,type;
		private String name,time,content;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		@Override
		public String toString() {
			return "name:" + name + ",time:" + time + ",content:" + content;
		}
		
	}

	/**
	 * 戏曲
	 */
	public static class Pekingopera extends Show {
		private static final long serialVersionUID = 1L;
	}
	
	/**
	 * 演唱会
	 */
	public static class Concert extends Show{
		private static final long serialVersionUID = 1L;
	}

	/**
	 * 音乐会
	 */
	public static class Music extends Show{
		private static final long serialVersionUID = 1L;
	}
	
	/**
	 * 话剧
	 */
	public static class Play extends Show{
		private static final long serialVersionUID = 1L;
	}
	
}
