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
 * ���ݹ������������������ݻ�ȡ������
 * Activities��DataManager�������ݣ�DataManagerת�������DataService��DataService������ģ��
 * ������DataManager����ʲô��û��������ת����һ�»�ȡ�������ݵ����󣬸о����С���ˡ�
 * ���������Preference�Ĺ��ܺ����ݿ�Ĺ��ܿ��Ŷ�����DataManager���о�DataManager��̫ӷ���ˡ�
 * ���ڲ�����ͷ����˺ܶ���ڲ��ࡣ
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

	//�����ȡ��ͼƬ
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
	
//	û����������Ҫ��DataService������ֻ��DataManager�����Բ���Ҫ�ṩ����ӿڡ�
//	public DataService getDataService() {
//		return dataService;
//	}
	
	/**
	 * ���ͼƬ
	 * @param index   ͨ��Ϊlist���ͼƬ��Ŀǰ��ƣ����index�����Ǳ����
	 * @param imageId ͼƬid
	 * @param l       ����ȡ��ͼƬ֪ͨ���������
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
	 * ��ͼƬ�����л�ȡͼƬ
	 * @param imageId  ͼƬ��ID
	 * @return  ͼƬ����null
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
				Log.v(TAG,"ImageThread ����images");
				images.put(imageId, dataService.getImage(imageId));
			}
			Log.v(TAG,"ImageThread �ͷ�images");
			if(l != null){
				l.onComplated(index, imageId);
			}
		}
	}
	
	/**
	 * ��ȡ��ϸ��Ϣ
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
	 * ��ȡ�б�
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
	 * ��Ӱ
	 */
	public static class Movie extends BaseMovie{
		private static final long serialVersionUID = 1L;
	}

	/**
	 * ������ӳ
	 */
	public static class WillMovie extends BaseMovie{
		private static final long serialVersionUID = 1L;
	}

	/**
	 * չ��
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
	 * ��ʳ
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

	//����
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
	 * Ϸ��
	 */
	public static class Pekingopera extends Show {
		private static final long serialVersionUID = 1L;
	}
	
	/**
	 * �ݳ���
	 */
	public static class Concert extends Show{
		private static final long serialVersionUID = 1L;
	}

	/**
	 * ���ֻ�
	 */
	public static class Music extends Show{
		private static final long serialVersionUID = 1L;
	}
	
	/**
	 * ����
	 */
	public static class Play extends Show{
		private static final long serialVersionUID = 1L;
	}
	
}
