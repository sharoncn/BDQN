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

	// �ṩ���ֻ�����ݵķ�����DataManager����Service��������ݣ�Service��������ģ��
	/**
	 * ��ȡ�б���Ϣ
	 * @param clazz  ���ݻ�ȡ�����ݹ���һϵ��clazz���͵Ķ���
	 * @param flag   ��־������������ʹ�ã�����Ϊ��־ʹ�ã���ʶ˭������
	 * @param handler ����ȡ������֪ͨ�ĸ�handler
	 * @param args   ���ӷ�����ʱ��Ҫ�Ĳ���
	 */
	public void getList(Class<?> clazz,int flag, Handler handler, Map<String, String> args) {
		new RequestThread(clazz, flag, handler, args).start();
	}

	/**
	 * ��ȡ��������
	 * @param flag ��־������������ʹ�ã�����Ϊ��־ʹ��
	 * @param handler ����ȡ������֪ͨ�ĸ�handler
	 * @param args ���ӷ�����ʱ��Ҫ�Ĳ���
	 * @param obj  Ϊ�ĸ����������ϸ��Ϣ
	 */
	public void getDetail(int flag, Handler handler, Map<String, String> args, Object obj) {
		new RequestThread(obj, flag, handler, args).start();
	}


	/**
	 * �������ϻ�ȡͼƬ
	 * @param imageId ͼƬ��ID
	 * @return  ���ͼƬ����NULL
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
		private boolean isDetail;//���ڱ�ʾ�ǹ��������������

		/**
		 * ����Ҫ�������ʱʹ�ô˹��췽��
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
		 * ����Ҫ������ʱʹ�ô˹��췽��
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
					// ����Ƿ������ۣ���ô����Ҫ����һ�����۶���
					// ���д��ݹ�����Flagֻ��FLAG_SEND_RECOMMAND������ʹ�ã���������������Message��what��
					// ���ǣ���Activity��handleMessageʱ��ʹ�õ���default���жϣ����ԣ����what��ʵҲû
					// �ǳ��ϸ�����塣
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
				//������������ϵļ�⡣���û�����������½ʱ�����磬��ȡ����ʱû���磬����ɱ�����
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
