package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data;

import java.util.HashMap;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Listeners.OnComplatedListener;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Service.DataService;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.ImageCache;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.api.WeiboAPI;
import com.weibo.sdk.android.api.WeiboAPI.COMMENTS_TYPE;

/**
 * 具体的操作转发给DataService完成
 * @author sharoncn
 *
 */
public class WeiboDataManager {
	public static final String TAG = "WeiboDataManager";
	public static final String FLAG_ISSUCCESS = "isSuccess";
	public static final String FLAG_ERR_MSG = "errMsg";
	public static final String FLAG_DATA = "data";
	public static final String FLAG_POSITION = "position";
	public static final String FLAG_IMAGEKEY = "imageKey";

	public static final int MSGTYPE_FAIL = 0x1;
	public static final int MSGTYPE_LOGIN = 0x2;
	public static final int MSGTYPE_PARSECONTENT = 0x3;
	public static final int MSGTYPE_IMAGEOK = 0x04;
	public static final int MSGTYPE_USERID = 0x05;
	public static final int MSGTYPE_USER = 0x06;
	public static long uid = -1;// 保存用户ID

	public static WeiboDataManager me;
	private static Context context;
	private static DataService dataservice;
	private static ImageCache imageCache;
	// 图像请求记录
	private static HashMap<String, HashMap<Integer, Handler>> imageRequestRecord = new HashMap<String, HashMap<Integer, Handler>>();

	private static Handler imageHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WeiboDataManager.MSGTYPE_IMAGEOK:
				Bundle data = msg.getData();
				final int position = data.getInt(WeiboDataManager.FLAG_POSITION);
				final String key = data.getString(WeiboDataManager.FLAG_IMAGEKEY);
				boolean isSuccess = data.getBoolean(WeiboDataManager.FLAG_ISSUCCESS);
				// 既然有返回,必然有请求记录,Handler必然会存在,不存在肯定是逻辑写的有问题
				HashMap<Integer, Handler> records = imageRequestRecord.get(key);
				Handler handler = records.get(position);
				Message resultMsg = handler.obtainMessage();

				if (isSuccess) {
					// 如果成功将图片缓存起来
					final Bitmap d = (Bitmap) data.getParcelable(WeiboDataManager.FLAG_DATA);
					if (imageCache == null) {
						imageCache = ImageCache.getInstance(context);
					}
					imageCache.put(key, d);
				}
				resultMsg.what = WeiboDataManager.MSGTYPE_IMAGEOK;
				records.remove(position);
				resultMsg.setData(data);
				resultMsg.sendToTarget();
				break;
			}
			super.handleMessage(msg);
		}
	};

	static {
		me = new WeiboDataManager();
	}

	private WeiboDataManager() {
	}

	public static WeiboDataManager getInstance(Context _context) {
		context = _context;
		imageCache = ImageCache.getInstance(_context);
		return me;
	}

	public void getFriendsTimeLine(ResponseHolder holder, int page) {
		if (dataservice != null) {
			// api.friendsTimeline(0, 0, 20, page, false, WeiboAPI.FEATURE.ALL,
			// false, l);
			Object[] args = new Object[] { 0, 0, 20, page, false, WeiboAPI.FEATURE.ALL, false, holder };
			dataservice.requestStatus(holder, "friendsTimeline", args);
		} else {
			Log.w(TAG, "dataservice is null");
		}
	}

	/**
	 * Alt我
	 * 
	 * @param holder
	 * @param page
	 */
	public void getMontions(ResponseHolder holder, int page) {
		if (dataservice != null) {
			// api.mentions(0, 0, 20, page, WeiboAPI.AUTHOR_FILTER.ALL,
			// WeiboAPI.SRC_FILTER.ALL, WeiboAPI.TYPE_FILTER.ALL, false, l);
			Object[] args = new Object[] { 0, 0, 50, page, WeiboAPI.AUTHOR_FILTER.ALL, WeiboAPI.SRC_FILTER.ALL,
					WeiboAPI.TYPE_FILTER.ALL, false, holder };
			dataservice.requestStatus(holder, "mentions", args);
		} else {
			Log.w(TAG, "dataservice is null");
		}
	}

	/**
	 * 对我的评论
	 * 
	 * @param holder
	 * @param page
	 */
	public void getComments(ResponseHolder holder, int page) {
		if (dataservice != null) {
			// commentsApi.toME(0, 0, 50, page, WeiboAPI.AUTHOR_FILTER.ALL,
			// WeiboAPI.SRC_FILTER.ALL, l);
			Object[] args = new Object[] { 0, 0, 50, page, WeiboAPI.AUTHOR_FILTER.ALL, WeiboAPI.SRC_FILTER.ALL, holder };
			dataservice.requestComments(holder, "toME", args);
		} else {
			Log.w(TAG, "dataservice is null");
		}
	}

	/**
	 * 请求用户信息
	 * @param holder
	 * @param uid
	 */
	public void getUserInfo(ResponseHolder holder, long uid) {
		if (dataservice != null) {
			// //usersApi.show(uid, listener);
			Object[] args = new Object[] { uid, holder };
			dataservice.requestUserInfo(holder, "show", args);
		} else {
			Log.w(TAG, "dataservice is null");
		}
	}

	public void getUserId(ResponseHolder holder) {
		if (dataservice != null) {
			// accountApi.getUid(l);
			Object[] args = new Object[] { holder };
			dataservice.requestAccount(holder, "getUid", args);
		} else {
			Log.w(TAG, "dataservice is null");
		}
	}

	/**
	 * 如果传递了refreshToken，则从refreshToken获取accessToken
	 * 
	 * @param refreshToken
	 */
	private void login(Handler handler, String... refreshToken) {
		if (dataservice != null) {
			dataservice.login(handler, context, refreshToken);
		} else {
			Log.w(TAG, "dataservice is null");
		}
	}

	public boolean loginIfNeed(Handler handler, String expires, String token, String refreshToken) {
		if (expires != null && token != null) {
			Oauth2AccessToken accessToken = new Oauth2AccessToken(token, expires);
			if (accessToken.isSessionValid()) {
				return false;
			} else {
				// 目前新浪微博没有提供使用refreshToken刷新accessToken的方法
				// if(refreshToken != null){
				// login(refreshToken);
				// }else{
				// login();
				// }
				login(handler);
				return true;
			}
		} else {
			login(handler);
			return true;
		}
	}

	public void setDataService(DataService _dataService) {
		dataservice = _dataService;
	}

	public DataService getService() {
		return dataservice;
	}

	private static Drawable v, trans;

	public Drawable getImage(String src, Handler handler, int position) {
		Log.i(TAG, "图片路径:" + src);
		if (src.equalsIgnoreCase("true")) {
			if (v == null) {
				v = context.getResources().getDrawable(R.drawable.v);
			}
			return v;
		} else if (src.equalsIgnoreCase("false")) {
			if (trans == null) {
				trans = context.getResources().getDrawable(android.R.color.transparent);
			}
			return trans;
		}
		Bitmap bmp = imageCache.get(src);
		if (bmp != null) {
			Log.i(TAG, "Cache中查找到图片");
			return new BitmapDrawable(bmp);
		} else {
			Log.w(TAG, "Cache中未找到图片,发送网络请求");
			if (imageRequestRecord.containsKey(src)) {
				imageRequestRecord.get(src).put(position, handler);
			} else {
				HashMap<Integer, Handler> value = new HashMap<Integer, Handler>();
				value.put(position, handler);
				imageRequestRecord.put(src, value);
				dataservice.getImage(src, imageHandler, position);
			}
			Log.v(TAG, "getImage:" + src);
			return context.getResources().getDrawable(R.drawable.nohead);
		}

	}

	public void requestProvinces(OnComplatedListener l) {
		if (dataservice != null) {
			dataservice.getProvinces(l);
		} else {
			Log.w(TAG, "dataservice is null");
		}
	}

	/**
	 * 写微博不带图片的
	 * 
	 * @param holder
	 * @param content
	 * @param lat
	 * @param lon
	 */
	public void createStatus(ResponseHolder holder, String content, String lat, String lon) {
		if (dataservice != null) {
			// update( String content, String lat, String lon, RequestListener
			// listener)
			Object[] args = new Object[] { content, lat, lon, holder };
			dataservice.requestStatus(holder, "update", args);
		} else {
			Log.w(TAG, "dataservice is null");
		}
	}

	/**
	 * 写带图片的微博
	 * 
	 * @param holder
	 * @param content
	 * @param imagePath
	 * @param lat
	 * @param lon
	 */
	public void createStatus(ResponseHolder holder, String content, String imagePath, String lat, String lon) {
		if (dataservice != null) {
			// upload( String content, String file, String lat, String lon,
			// RequestListener listener)
			Object[] args = new Object[] { content, imagePath, lat, lon, holder };
			dataservice.requestStatus(holder, "upload", args);
		} else {
			Log.w(TAG, "dataservice is null");
		}
	}

	/**
	 * 收藏微博
	 * 
	 * @param holder
	 * @param statusId
	 */
	public void createFavorite(ResponseHolder holder, String statusId) {
		if (dataservice != null) {
			// upload( String content, String file, String lat, String lon,
			// RequestListener listener)
			Object[] args = new Object[] { Long.parseLong(statusId), holder };
			dataservice.requestFavorites(holder, "create", args);
		} else {
			Log.w(TAG, "dataservice is null");
		}
	}

	/**
	 * 删除收藏
	 * 
	 * @param holder
	 * @param statusId
	 */
	public void deleteFavorite(ResponseHolder holder, String statusId) {
		if (dataservice != null) {
			// upload( String content, String file, String lat, String lon,
			// RequestListener listener)
			Object[] args = new Object[] { Long.parseLong(statusId), holder };
			dataservice.requestFavorites(holder, "destroy", args);
		} else {
			Log.w(TAG, "dataservice is null");
		}
	}

	/**
	 * 转发微博
	 * 
	 * @param holder
	 * @param statusId
	 * @param comment
	 * @param type
	 */
	public void repostStatus(ResponseHolder holder, String statusId, String comment, COMMENTS_TYPE type) {
		if (dataservice != null) {
			// repost
			// long id, String status, COMMENTS_TYPE is_comment,RequestListener
			// listener
			Object[] args = new Object[] { Long.parseLong(statusId), comment, type, holder };
			dataservice.requestStatus(holder, "repost", args);
		} else {
			Log.w(TAG, "dataservice is null");
		}
	}

	/**
	 * 评论微博
	 * 
	 * @param holder
	 * @param comment
	 * @param statusId
	 * @param comment_ori
	 */
	public void commentStatus(ResponseHolder holder, String comment, String statusId, boolean comment_ori) {
		if (dataservice != null) {
			// String comment, long id, boolean comment_ori, RequestListener
			// listener
			Object[] args = new Object[] { comment, Long.parseLong(statusId), false, holder };
			dataservice.requestComments(holder, "create", args);
		} else {
			Log.w(TAG, "dataservice is null");
		}
	}

	/**
	 * 根据ID获得一条微博
	 * 
	 * @param holder
	 * @param id
	 */
	public void getSingleStatus(ResponseHolder holder, String statusId) {
		if (dataservice != null) {
			// show( long id, RequestListener listener)
			Object[] args = new Object[] { Long.parseLong(statusId), holder };
			dataservice.requestStatus(holder, "show", args);
		} else {
			Log.w(TAG, "dataservice is null");
		}
	}

	/**
	 * 获得收藏列表
	 * @param holder
	 * @param page
	 */
	public void getFavorites(ResponseHolder holder, int page) {
		if (dataservice != null) {
			// favorites(int count, int page, RequestListener listener)
			Object[] args = new Object[] {50, page , holder };
			dataservice.requestFavorites(holder, "favorites", args);
		} else {
			Log.w(TAG, "dataservice is null");
		}
	}

	/**
	 * 根据ID删除收藏
	 * @param holder
	 * @param id
	 */
	public void deleteFavorite(ResponseHolder holder, long id) {
		if (dataservice != null) {
			// destroy( long id, RequestListener listener)
			Object[] args = new Object[] {id, holder };
			dataservice.requestFavorites(holder, "destroy", args);
		} else {
			Log.w(TAG, "dataservice is null");
		}
	}
}
