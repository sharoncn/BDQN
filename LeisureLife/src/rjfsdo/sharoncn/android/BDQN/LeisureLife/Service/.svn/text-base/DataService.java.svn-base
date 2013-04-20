package rjfsdo.sharoncn.android.BDQN.LeisureLife.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.HttpConnection;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLParam;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLProtocol;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

/**
 * @author sharoncn
 *
 */
public class DataService extends Service {
	private static final String TAG = "DataService";
	private static IBinder binder;
	private static boolean isRunning = false;

	@Override
	public IBinder onBind(Intent intent) {
		Log.v(TAG, "DataService onBind");
		if (binder == null) {
			binder = new DataBinder();
		}
		return binder;
	}

	public class DataBinder extends Binder {
		public DataService getService() {
			return DataService.this;
		}
	}

	public static boolean isStart() {
		return isRunning;
	}

	@Override
	public void onDestroy() {
		Log.v(TAG, "DataService onDestroy");
		isRunning = false;
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.v(TAG, "DataService onStart");
		DataManager.getInstance(this).setDataService(this);
		isRunning = true;
		super.onStart(intent, startId);
	}

	// 提供各种获得数据的方法，DataManager调用Service来获得数据，Service操作网络模块
	/**
	 * 获取列表信息
	 * @param clazz  根据获取的数据构造一系列clazz类型的对象
	 * @param flag   标志。方法本身并不使用，仅作为标志使用，标识谁在请求
	 * @param handler 当获取完数据通知哪个handler
	 * @param args   连接服务器时需要的参数
	 */
	public void getList(Class<?> clazz,int flag, Handler handler, Map<String, String> args) {
		new RequestThread(clazz, flag, handler, args).start();
	}

	/**
	 * 获取详情数据
	 * @param flag 标志。方法本身并不使用，仅作为标志使用
	 * @param handler 当获取完数据通知哪个handler
	 * @param args 连接服务器时需要的参数
	 * @param obj  为哪个对象填充详细信息
	 */
	public void getDetail(int flag, Handler handler, Map<String, String> args, Object obj) {
		new RequestThread(obj, flag, handler, args).start();
	}


	/**
	 * 从网络上获取图片
	 * @param imageId 图片的ID
	 * @return  获得图片或者NULL
	 */
	public Drawable getImage(String imageId) {
		Log.v(TAG, "getImage  id:" + imageId);
		try {
			return HttpConnection.getNetImage(imageId);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	class RequestThread extends Thread {
		private int flag;
		private Handler handler;
		private Map<String, String> args;
		private Class<?> clazz;
		private Object obj;
		private boolean isDetail;//用于标示是构造对象还是填充对象

		/**
		 * 当需要构造对象时使用此构造方法
		 * @param clazz
		 * @param flag
		 * @param handler
		 * @param args
		 */
		public RequestThread(Class<?> clazz, int flag, Handler handler, Map<String, String> args) {
			isDetail = false;
			this.flag = flag;
			this.handler = handler;
			this.args = args;
			this.clazz = clazz;
		}

		/**
		 * 当需要填充对象时使用此构造方法
		 * @param obj
		 * @param flag
		 * @param handler
		 * @param args
		 */
		public RequestThread(Object obj, int flag, Handler handler, Map<String, String> args) {
			isDetail = true;
			this.flag = flag;
			this.handler = handler;
			this.args = args;
			this.obj = obj;
		}

		@Override
		public void run() {
			URLParam param = new URLParam();
			String result;
			Iterator<String> it = args.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				param.addParam(key, args.get(key));
			}

			try {
				result = HttpConnection.getContentString(URLProtocol.URL, param);
				ArrayList<Serializable> data;
				Message msg = new Message();
				Bundle bundle = new Bundle();
	
				if (result != null && result.contains("'code':'0'")) {
					bundle.putBoolean(DataManager.FLAG_ISSUCCESS, true);
					// 如果是发送评论，那么不需要构造一个评论对象
					// 所有传递过来的Flag只有FLAG_SEND_RECOMMAND真正被使用，其他仅仅是用做Message的what。
					// 但是，在Activity中handleMessage时，使用的是default来判断，所以，这个what其实也没
					// 非常严格的意义。
					if (flag != DataManager.FLAG_SEND_RECOMMAND) {
						if (isDetail) {
							Util.parseJsonDetail(obj, result);
							bundle.putSerializable(DataManager.FLAG_DATA, (Serializable) obj);
						} else {
							data = Util.parseJsonString(clazz, result);
							bundle.putSerializable(DataManager.FLAG_DATA, data);
						}
					}
				} else {
					bundle.putBoolean(DataManager.FLAG_ISSUCCESS, false);
				}
				msg.setData(bundle);
				msg.what = flag;
				handler.sendMessage(msg);
			} catch (IOException e) {
				//加入了网络故障的检测。如果没有这个，当登陆时有网络，获取数据时没网络，会造成崩溃。
				if(e.getMessage().contains("Network is unreachable")){
					Message msg = new Message();
					msg.what = DataManager.FLAG_TOAST_MSG;
					Bundle data = new Bundle();
					data.putInt(DataManager.FLAG_MSG, R.string.networkunreachable);
					data.putBoolean(DataManager.FLAG_ISSUCCESS, false);
					msg.setData(data);
					handler.sendMessage(msg);
				}
				e.printStackTrace();
			}
		}
	}
}
