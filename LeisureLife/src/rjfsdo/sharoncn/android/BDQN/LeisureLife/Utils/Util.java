package rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Recommend;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Service.DataService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.google.gson.stream.JsonReader;

public class Util {
	private static final String TAG = "Util";

	public static ProgressDialog showProgressDialog(Context context, String msg) {
		ProgressDialog pd = new ProgressDialog(context);
		pd.setMessage(msg);
		pd.show();
		return pd;
	}

	/**
	 * 分解UID,实现依赖于json字符串的结构
	 * 
	 * @param result
	 *            从服务器获得的json字符串
	 * @return 返回分解出来的UID
	 */
	public static String getUid(String result) {
		Log.v(TAG, result);
		String uid = null;
		JsonReader jr = new JsonReader(new StringReader(result));
		jr.setLenient(true);
		try {
			jr.beginObject();
			while (jr.hasNext()) {
				String key = jr.nextName();
				String value = jr.nextString();
				Log.v(TAG, "name:" + key);
				Log.v(TAG, "value:" + value);
				if (key.equalsIgnoreCase("uid")) {
					uid = value;
				}
			}
			jr.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return uid;
	}

	/**
	 * 将dp或者dip转换为px
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2Px(Context context, int dp) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		int pixs = dp * (dm.densityDpi / 160);
		return pixs;
	}

	/**
	 * Toast显示提示信息
	 * 
	 * @param context
	 * @param resId
	 */
	public static void showMsg(Context context, int resId) {
		showMsg(context, context.getString(resId));
	}

	/**
	 * Toast显示提示信息
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showMsg(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * 解析json并填充对象
	 * 
	 * @param obj
	 *            要填充的对象
	 * @param jsonStr
	 *            json数据
	 */
	public static void parseJsonDetail(Object obj, String jsonStr) {
		try {
			JsonReader jr = new JsonReader(new StringReader(jsonStr));
			jr.setLenient(true);
			jr.beginObject();
			while (jr.hasNext()) {
				String name = jr.nextName();
				String value = null;
				if (name.equalsIgnoreCase("list")) {
					jr.beginArray();
					ArrayList<Serializable> data = new ArrayList<Serializable>();
					Class<?> innerClazz = obj.getClass().getDeclaredClasses()[0];
					Constructor<?> cst = innerClazz.getConstructor();
					while (jr.hasNext()) {
						Object instance = cst.newInstance(new Object[] {});
						data.add((Serializable) instance);
						jr.beginObject();
						while (jr.hasNext()) {
							name = jr.nextName();
							if (name.matches("[a-zA-Z]id")) {
								// Log.v(TAG, name + " matches()");
								name = "id";
							}
							value = jr.nextString();
							// Log.v(TAG, "name:" + name);
							// Log.v(TAG, "value:" + value);
							constructObject(instance, name, value);
						}
						// 假定类中有一个方法为:set + 内部类名称 的方法
						constructObject(obj, innerClazz.getSimpleName(), data);
						jr.endObject();
					}
					jr.endArray();
				} else {
					value = jr.nextString();
					constructObject(obj, name, value);
					// Log.v(TAG, "value:" + value);
				}
			}
			jr.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析json并构造一系列对象，必须有默认构造器
	 * 
	 * @param clazz
	 *            对象类
	 * @param jsonStr
	 *            json数据
	 * @return 构造的对象数组
	 */
	public static ArrayList<Serializable> parseJsonString(Class<?> clazz, String jsonStr) {
		ArrayList<Serializable> data = new ArrayList<Serializable>();
		// Log.v(TAG, clazz.getName());
		try {
			JsonReader jr = new JsonReader(new StringReader(jsonStr));
			jr.setLenient(true);
			jr.beginObject();
			while (jr.hasNext()) {
				String name = jr.nextName();
				String value = null;
				if (name.equalsIgnoreCase("list")) {
					jr.beginArray();
					Constructor<?> cst = clazz.getConstructor();
					while (jr.hasNext()) {
						Object instance = cst.newInstance(new Object[] {});
						data.add((Serializable) instance);
						jr.beginObject();
						while (jr.hasNext()) {
							name = jr.nextName();
							if (name.matches("[a-zA-Z]id")) {
								// Log.v(TAG, name + " matches()");
								name = "id";
							}
							value = jr.nextString();
							// Log.v(TAG, "name:" + name);
							// Log.v(TAG, "value:" + value);
							constructObject(instance, name, value);
						}
						jr.endObject();
					}
					jr.endArray();
				} else {
					value = jr.nextString();
					// Log.v(TAG, "value:" + value);
				}
			}
			jr.endObject();

			// Gson gson = new Gson();
			// JsonElement je = gson.toJsonTree(jsonStr);
			// Type t = clazz.getClass();
			// Constructor<?> cst = clazz.getConstructor();
			// Object instance = cst.newInstance(new Object[]{});
			// Method[] methods = clazz.getMethods();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * 填充对象
	 * 
	 * @param instance
	 *            要填充的对像
	 * @param name
	 *            对象字段名(必须有set方法支持)
	 * @param value
	 *            要填充的值
	 */
	private static void constructObject(Object instance, String name, Object value) {
		// try {
		// Method method = instance.getClass().getMethod("set" + name, new
		// Class<?>[]{String.class});
		// method.invoke(instance, new Object[]{value});
		// } catch(Exception e) {
		// e.printStackTrace();
		// }
		Method[] methods = instance.getClass().getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			// Log.v(TAG, "Method Name:" + methodName);
			if (methodName.equalsIgnoreCase("set" + name)) {
				// Log.v(TAG, "匹配到方法:" + methodName);
				try {
					method.invoke(instance, new Object[] { value });
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 重新计算ListView的高度，让ListView完全展示其内容
	 * 
	 * @param listView
	 */
	public static void resizeListView(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		final int count = listAdapter.getCount();
		for (int i = 0; i < count; i++) {
			final View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * 将评论列表转化为字符串
	 * 
	 * @param rs
	 * @return
	 */
	public static String recommendList2String(List<Recommend> rs) {
		final StringBuffer sb = new StringBuffer();
		final int count = rs.size();
		for (int i = 0; i < count; i++) {
			sb.append(rs.get(i).toString());
			if (i != count - 1) {
				sb.append("|");
			}
		}
		return sb.toString();
	}

	/**
	 * 销毁百度地图对象
	 * 
	 * @param context
	 */
	public static void destoryMapManger(Context context) {
		BMapManager map = MapManager.getInstance(context).getMapManager();
		if (map != null) {
			map.stop();
			map.destroy();
			map = null;
		}
	}

	/**
	 * 启动服务
	 * 
	 * @param context
	 */
	public static void startService(Context context,Class<?> clazz) {
		Intent intent = new Intent(context, clazz);
		context.startService(intent);
	}

	/**
	 * 停止服务
	 * 
	 * @param context
	 */
	public static void stopService(Context context,Class<?> clazz) {
		Intent intent = new Intent(context, clazz);
		context.stopService(intent);
	}

	/**
	 * 退出
	 * @param context
	 */
	public static void exit(Context context) {
		Util.destoryMapManger(context);
		Util.stopService(context,DataService.class);
		System.exit(0);
	}
}
