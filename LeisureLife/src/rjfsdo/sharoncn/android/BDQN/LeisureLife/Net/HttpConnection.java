package rjfsdo.sharoncn.android.BDQN.LeisureLife.Net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class HttpConnection {
	private static final String TAG = "HttpConnection";

	private static InputStream httpGet(String urlStr,URLParam param) throws IOException {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlStr + param.toString());//new String(param.toString().getBytes("utf-8"),"ISO8859-1"));
			Log.v(TAG,"URL:" + url.toString());
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			return conn.getInputStream();
		} catch (IOException e) {
			//当连接过程中网络处于关闭状态，直接吞掉似乎不太合理
			e.printStackTrace();
			if (conn != null) {
				conn.disconnect();
			}
			//所以这里还是将它抛出去,让调用者去处理
			throw e;
		}
	}

	/**
	 * 从URL地址获得的内容解析成GBK编码的字符串
	 * @param url     连接的URL
	 * @param param   参数
	 * @return String 返回的字符串
	 * @throws IOException
	 */
	public static String getContentString(String url,URLParam param) throws IOException{
		InputStream is = httpGet(url, param);
		if(is == null){
			return null;
		}
		try {
			byte[] bytes = readData(is);
			//Log.v(TAG,"" + new String(bytes,"GBK"));
			//Log.v(TAG,"" + new String(bytes,"GB2312"));
			//Log.v(TAG,"" + new String(bytes,"utf-8"));
			//Log.v(TAG,"" + new String(bytes,"ISO8859-1"));
			return new String(bytes,"GBK");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 从输入流中读取byte数组
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
	 * 从根据图片URL和id号获得图片的输入流
	 * @param strUrl 获取图片的地址
	 * @param imageId  图片的id
	 * @return   图片流
	 * @throws IOException
	 */
	private static InputStream getImageStream(String strUrl, String imageId) throws IOException {
		URLParam param = new URLParam();
		param.addParam(URLProtocol.CMD, URLProtocol.IMAGE_CMD_VALUE);
		param.addParam(URLProtocol.IMAGE, imageId);
		return httpGet(strUrl,param);
	}

	/**
	 * 获得指定编号的image
	 * @param imageId   图片ID
	 * @return Drawable 图片
	 * @throws IOException 
	 */
	public static Drawable getNetImage(String imageId) throws IOException {
		return new BitmapDrawable(getImageStream(URLProtocol.URL, imageId));
	}
}
