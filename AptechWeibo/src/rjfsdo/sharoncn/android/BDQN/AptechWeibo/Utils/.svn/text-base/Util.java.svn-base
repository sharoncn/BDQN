package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class Util {
	private static final String TAG = "Util";

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
	 * 显示ProgressDialog
	 * 
	 * @param context
	 *            Context
	 * @param msg
	 *            文本
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, String msg) {
		ProgressDialog pd = new ProgressDialog(context);
		pd.setMessage(msg);
		pd.show();
		return pd;
	}

	/**
	 * 显示ProgressDialog
	 * 
	 * @param context
	 *            Context
	 * @param resId
	 *            文本ID
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, int resId) {
		return showProgressDialog(context, context.getString(resId));
	}

	public static void dumpJsonString(String json) {
		final String path = Environment.getExternalStorageDirectory()
				+ "/jsonDump.txt";
		File jsonFile = new File(path);
		if (jsonFile.exists()) {
			jsonFile.delete();
		}
		try {
			jsonFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(jsonFile);
			BufferedReader br = new BufferedReader(new StringReader(json));
			String line;
			while ((line = br.readLine()) != null) {
				byte[] buffer = line.getBytes();
				fos.write(buffer, 0, buffer.length);
			}
			br.close();
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 当SD卡存在时返回SD卡的路径，如果不存在返回NULL
	 * 
	 * @return
	 */
	public static String getRootDir() {
		if (sdcardExists()) {
			return Environment.getExternalStorageDirectory().getPath();
		}
		return null;
	}

	/**
	 * 判断SD卡是否存在
	 * 
	 * @return 当SD卡已挂载或者已挂载但是只读时返回true，其他情况返回false
	 */
	public static boolean sdcardExists() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)
				|| Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED_READ_ONLY)) {
			return true;
		}
		return false;
	}

	/**
	 * 创建文件，如果要创建的文件父路径不存在会创建父路径
	 * 
	 * @param filePath
	 *            要创建的文件路径
	 * @return 如果文件存在直接返回true，如果发生异常，或者创建失败，则返回false
	 */
	public static boolean createFile(String filePath) {
		Log.v(TAG, "arg:" + filePath);
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}
		File parent = file.getParentFile();
		try {
			if (!parent.exists()) {
				parent.mkdirs();
			}
			return file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 将Stream写入到指定文件
	 * 
	 * @param is
	 *            流
	 * @param filePath
	 *            指定的文件路径
	 * @return 成功返回true，失败返回false
	 */
	public static boolean saveFile(InputStream is, String filePath) {
		File file = new File(filePath);
		if (!createFile(file.getPath())) {
			return false;
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buff = new byte[1024];
			int len = -1;
			while ((len = is.read(buff)) != -1) {
				fos.write(buff, 0, len);
			}
			is.close();
			fos.flush();
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 从文件中读取字符串
	 * 
	 * @param path
	 *            文件路径
	 * @return 读取的字符串或者NULL
	 */
	public static String readTextFromFile(String path) {
		final File file = new File(path);
		final StringBuffer sb = new StringBuffer();
		if (!file.exists()) {
			return null;
		}
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "utf-8"));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}

	/**
	 * 通过URL返回流
	 * 
	 * @param urlStr
	 * @return
	 * @throws IOException
	 */
	public static InputStream httpGet(String urlStr) throws IOException {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlStr);// new
										// String(param.toString().getBytes("utf-8"),"ISO8859-1"));
			Log.v(TAG, "URL:" + url.toString());
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			return conn.getInputStream();
		} catch (IOException e) {
			// 当连接过程中网络处于关闭状态，直接吞掉似乎不太合理
			e.printStackTrace();
			if (conn != null) {
				conn.disconnect();
			}
			// 所以这里还是将它抛出去,让调用者去处理
			throw e;
		}
	}

	/**
	 * 从网上下载一张图片
	 * 
	 * @param url
	 *            URL
	 * @return 图片
	 * @throws IOException
	 */
	public static Bitmap getImageFromNet(String url) throws IOException {
		// InputStream is = httpGet(url);
		//
		// Log.v(TAG,is.toString());
		return BitmapFactory.decodeStream(httpGet(url));
	}

	/**
	 * 从URL地址获得的内容解析成charset编码的字符串
	 * 
	 * @param url
	 *            连接的URL
	 * @param charset 字符编码
	 * @return String 返回的字符串
	 * @throws IOException
	 */
	public static String getContentString(String url, String charset)
			throws IOException {
		InputStream is = httpGet(url);
		if (is == null) {
			return null;
		}
		try {
			byte[] bytes = readData(is);
			// Log.v(TAG,"" + new String(bytes,"GBK"));
			// Log.v(TAG,"" + new String(bytes,"GB2312"));
			Log.v(TAG,"" + new String(bytes,"utf-8"));
			// Log.v(TAG,"" + new String(bytes,"ISO8859-1"));
			return new String(bytes, charset);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 从输入流中读取byte数组
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private static byte[] readData(InputStream is) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		int len = -1;
		byte[] buff = new byte[1024];
		while ((len = is.read(buff)) != -1) {
			os.write(buff, 0, len);
		}
		byte[] result = os.toByteArray();
		is.close();
		os.close();
		return result;
	}
	
	/**
	 * 确定json下个value的类型,暂时是单线程调用,防止出问题加上同步
	 * @param jr JsonReader
	 * @return 返回符合类型的value
	 */
	public static synchronized Object next(JsonReader jr) {
		try {
			JsonToken token = jr.peek();
			if (token == JsonToken.NULL) {
				jr.nextNull();
				return "null";
			} else if (token == JsonToken.BOOLEAN) {
				return jr.nextBoolean();
			} else if (token == JsonToken.NUMBER) {
				return jr.nextLong();
			} else if (token == JsonToken.STRING) {
				return jr.nextString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "null";
	}
	
	/**
	 * 为对象填充数据，暂时是单线程调用,防止出问题加上同步
	 * @param instance  要填充的对象
	 * @param name      调用的方法名
	 * @param value     值
	 */
	public static synchronized void constructObject(Object instance, String name, Object value) {
		Log.v(TAG, "name:" + name + "  value:" + value);
		Method[] methods = instance.getClass().getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.equalsIgnoreCase("set" + name)) {
				try {
					method.invoke(instance, new Object[] { value });
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
