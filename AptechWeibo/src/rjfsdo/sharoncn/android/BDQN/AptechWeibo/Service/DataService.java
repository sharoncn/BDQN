package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Service;

import java.io.IOException;
import java.lang.reflect.Method;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.ResponseHolder;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboDataManager;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboPreference;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Listeners.OnComplatedListener;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Constants;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Util;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.api.AccountAPI;
import com.weibo.sdk.android.api.CommentsAPI;
import com.weibo.sdk.android.api.FavoritesAPI;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.api.UsersAPI;
import com.weibo.sdk.android.net.RequestListener;

/**
 * 请求的具体实现在这里，WeiboApi中不同的Api类型对应不同功能请求,DataService只需要实现每种API的调用
 * 请求的具体方法由DataManager确定
 * @author sharoncn
 *
 */
public class DataService extends Service {
	protected static final String TAG = "DataService";
	private static Oauth2AccessToken accessToken;
	private static StatusesAPI statusApi;
	private static CommentsAPI commentsApi;
	private static UsersAPI usersApi;
	private static FavoritesAPI favoritesApi;
	private static AccountAPI accountApi;
	private static WeiboDataManager dm;
	private RequestListener request;
	private Method method = null;
	private static WeiboPreference preference;
	private Handler remoteHandler;
	private Context mContext;
	private String cmd;
	private Object[] args;

	private Handler localHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WeiboDataManager.MSGTYPE_LOGIN:
				boolean isLogin = false;
				Bundle data = msg.getData();
				String token = data.getString(Weibo.KEY_TOKEN);
				String expires = data.getString(Weibo.KEY_EXPIRES);
				String refreshToken = data.getString(Weibo.KEY_REFRESHTOKEN);
				accessToken = new Oauth2AccessToken(token, expires);

				if (accessToken.isSessionValid()) {
					isLogin = true;
					// 写入到preference
					preference.saveAccessToken(token);
					preference.saveExpiresTime(expires);
					preference.saveRefreshToken(refreshToken);

					if (method != null) {
						try {
							method.invoke(DataService.this, new Object[] { request, cmd, args });
						} catch (Exception e) {
							e.printStackTrace();
						}
						method = null;
					}
				}

				if (remoteHandler != null) {
					Log.v(TAG, "发送远程消息");
					Message remoteMsg = remoteHandler.obtainMessage();
					remoteMsg.what = WeiboDataManager.MSGTYPE_LOGIN;
					data.putBoolean(WeiboDataManager.FLAG_ISSUCCESS, isLogin);
					remoteMsg.setData(data);
					remoteMsg.sendToTarget();
					remoteHandler = null;
				}
				break;
			case WeiboDataManager.MSGTYPE_FAIL:
				Log.w(TAG, msg.getData().getString(WeiboDataManager.FLAG_ERR_MSG));
				if (remoteHandler != null) {
					Log.v(TAG, "发送远程消息");
					Message remoteMsg = remoteHandler.obtainMessage();
					remoteMsg.what = WeiboDataManager.MSGTYPE_LOGIN;
					remoteMsg.setData(msg.getData());
					remoteMsg.sendToTarget();
					remoteHandler = null;
				}
				break;
			}
			super.handleMessage(msg);
		}
	};

	public DataService() {
		dm = WeiboDataManager.getInstance(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.v(TAG, "SERVICE IS STARTED");
		if (preference == null)
			preference = WeiboPreference.getInstance(DataService.this);
		if (dm.getService() == null)
			dm.setDataService(this);
		super.onStart(intent, startId);
	}

	
	/**
	 * 登录
	 * @param _handler       接收消息的handler
	 * @param context        
	 * @param refreshToken
	 */
	public void login(Handler _handler, Context context, String... refreshToken) {
		this.remoteHandler = _handler;
		if(mContext == null) mContext = context;
		if (refreshToken != null && refreshToken.length > 0) {
			refreshAccessToken();
		} else {
			Weibo weibo = Weibo.getInstance(Constants.APP_KEY, Constants.REDIRECT_URL);
			weibo.authorize(context, new ResponseHolder(this.localHandler));
		}
	}

	//重新登录
	private void reLogin(String methodName, RequestListener l, String cmd, Object[] args) {
		Log.i(TAG, "Relogin");
		try {
			this.method = this.getClass().getMethod(methodName,
					new Class<?>[] { RequestListener.class, String.class, Object[].class });
			this.request = l;
			this.cmd = cmd;
			this.args = args;
			login(remoteHandler, mContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void refreshAccessToken() {
		// 貌似新浪没有提供刷新Token的方法
	}

	/**
	 * 检查Token是都有效，如果无效会重新登录
	 * 
	 * @param methodName
	 * @param l
	 * @param cmd
	 * @param args
	 * @return
	 */
	private boolean checkToken(String methodName, RequestListener l, String cmd, Object[] args) {
		if (accessToken == null) {
			initAccessToken();
		}

		if (!accessToken.isSessionValid()) {
			reLogin(methodName, l, cmd, args);
			return false;
		}

		return true;
	}

	//请求
	private void request(Object api, String cmd, Object[] args) {
		Method[] methods = api.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().equalsIgnoreCase(cmd)) {
				try {
					method.invoke(api, args);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	/**
	 * 请求微博
	 * @param l
	 * @param cmd
	 * @param args
	 */
	public void requestStatus(RequestListener l, String cmd, Object[] args) {
		if (!checkToken("requestStatus", l, cmd, args))
			return;
		if (statusApi == null) {
			statusApi = new StatusesAPI(accessToken);
		}
		Log.v(TAG, "requestStatus");

		request(statusApi, cmd, args);
	}

	/**
	 * 请求收藏
	 * @param l
	 * @param cmd
	 * @param args
	 */
	public void requestFavorites(RequestListener l, String cmd, Object[] args) {
		if (!checkToken("requestFavorites", l, cmd, args))
			return;

		if (favoritesApi == null) {
			favoritesApi = new FavoritesAPI(accessToken);
		}

		request(favoritesApi, cmd, args);
	}

	/**
	 * 请求评论
	 * @param l
	 * @param cmd
	 * @param args
	 */
	public void requestComments(RequestListener l, String cmd, Object[] args) {
		if (!checkToken("requestComments", l, cmd, args))
			return;

		if (commentsApi == null) {
			commentsApi = new CommentsAPI(accessToken);
		}

		request(commentsApi, cmd, args);
	}

	/**
	 * 请求用户信息
	 * @param l
	 * @param cmd
	 * @param args
	 */
	public void requestUserInfo(RequestListener l, String cmd, Object[] args) {
		if (!checkToken("requestUserInfo", l, cmd, args))
			return;

		if (usersApi == null) {
			usersApi = new UsersAPI(accessToken);
		}

		request(usersApi, cmd, args);
	}

	/**
	 * 请求账户信息
	 * @param l
	 * @param cmd
	 * @param args
	 */
	public void requestAccount(RequestListener l, String cmd, Object[] args) {
		if (!checkToken("requestUserId", l, cmd, args))
			return;

		if (accountApi == null) {
			accountApi = new AccountAPI(accessToken);
		}

		request(accountApi, cmd, args);
	}

	private void initAccessToken() {
		String token = preference.getAccessToken();
		String expires = preference.getExpiresTime();
		Log.v(TAG, token + "   " + expires);
		accessToken = new Oauth2AccessToken(token, expires);
	}

	/**
	 * 得到图片
	 * 
	 * @param src
	 *            图片的URL
	 * @param handler
	 *            接受消息的handler
	 * @param position
	 *            图片在list中的position
	 */
	public void getImage(String src, Handler handler, int position) {
		new ImageThread(src, handler, position).start();
	}

	class ImageThread extends Thread {
		private String src;
		private Handler handler;
		private int position = -1;

		public ImageThread(String src, Handler handler, int position) {
			this.src = src;
			this.handler = handler;
			this.position = position;
		}

		@Override
		public void run() {
			Message msg = handler.obtainMessage();
			msg.what = WeiboDataManager.MSGTYPE_IMAGEOK;
			Bundle data = new Bundle();
			msg.setData(data);
			try {
				Bitmap bmp = Util.getImageFromNet(src);
				if (bmp != null) {
					data.putBoolean(WeiboDataManager.FLAG_ISSUCCESS, true);
					data.putParcelable(WeiboDataManager.FLAG_DATA, bmp);
				} else {
					data.putBoolean(WeiboDataManager.FLAG_ISSUCCESS, false);
				}
			} catch (IOException e) {
				data.putBoolean(WeiboDataManager.FLAG_ISSUCCESS, false);
				e.printStackTrace();
			}
			data.putString(WeiboDataManager.FLAG_IMAGEKEY, src);
			data.putInt(WeiboDataManager.FLAG_POSITION, position);
			msg.sendToTarget();
		}
	}

	/**
	 * 获得城市列表
	 * 
	 * @param l
	 */
	public void getProvinces(final OnComplatedListener l) {
		new Thread() {
			@Override
			public void run() {
				try {
					l.onComplated(Util.getContentString(Constants.PROVINCE_URL, "utf-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

}
