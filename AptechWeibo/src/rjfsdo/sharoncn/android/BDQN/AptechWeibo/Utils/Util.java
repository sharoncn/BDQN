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
	 * Toast��ʾ��ʾ��Ϣ
	 * 
	 * @param context
	 * @param resId
	 */
	public static void showMsg(Context context, int resId) {
		showMsg(context, context.getString(resId));
	}

	/**
	 * Toast��ʾ��ʾ��Ϣ
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showMsg(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * ��ʾProgressDialog
	 * 
	 * @param context
	 *            Context
	 * @param msg
	 *            �ı�
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, String msg) {
		ProgressDialog pd = new ProgressDialog(context);
		pd.setMessage(msg);
		pd.show();
		return pd;
	}

	/**
	 * ��ʾProgressDialog
	 * 
	 * @param context
	 *            Context
	 * @param resId
	 *            �ı�ID
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
	 * ��SD������ʱ����SD����·������������ڷ���NULL
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
	 * �ж�SD���Ƿ����
	 * 
	 * @return ��SD���ѹ��ػ����ѹ��ص���ֻ��ʱ����true�������������false
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
	 * �����ļ������Ҫ�������ļ���·�������ڻᴴ����·��
	 * 
	 * @param filePath
	 *            Ҫ�������ļ�·��
	 * @return ����ļ�����ֱ�ӷ���true����������쳣�����ߴ���ʧ�ܣ��򷵻�false
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
	 * ��Streamд�뵽ָ���ļ�
	 * 
	 * @param is
	 *            ��
	 * @param filePath
	 *            ָ�����ļ�·��
	 * @return �ɹ�����true��ʧ�ܷ���false
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
	 * ���ļ��ж�ȡ�ַ���
	 * 
	 * @param path
	 *            �ļ�·��
	 * @return ��ȡ���ַ�������NULL
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
	 * ͨ��URL������
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
			// �����ӹ��������紦�ڹر�״̬��ֱ���̵��ƺ���̫����
			e.printStackTrace();
			if (conn != null) {
				conn.disconnect();
			}
			// �������ﻹ�ǽ����׳�ȥ,�õ�����ȥ����
			throw e;
		}
	}

	/**
	 * ����������һ��ͼƬ
	 * 
	 * @param url
	 *            URL
	 * @return ͼƬ
	 * @throws IOException
	 */
	public static Bitmap getImageFromNet(String url) throws IOException {
		// InputStream is = httpGet(url);
		//
		// Log.v(TAG,is.toString());
		return BitmapFactory.decodeStream(httpGet(url));
	}

	/**
	 * ��URL��ַ��õ����ݽ�����charset������ַ���
	 * 
	 * @param url
	 *            ���ӵ�URL
	 * @param charset �ַ�����
	 * @return String ���ص��ַ���
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
	 * ���������ж�ȡbyte����
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
	 * ȷ��json�¸�value������,��ʱ�ǵ��̵߳���,��ֹ���������ͬ��
	 * @param jr JsonReader
	 * @return ���ط������͵�value
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
	 * Ϊ����������ݣ���ʱ�ǵ��̵߳���,��ֹ���������ͬ��
	 * @param instance  Ҫ���Ķ���
	 * @param name      ���õķ�����
	 * @param value     ֵ
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
