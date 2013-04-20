package rjfsdo.sharoncn.android.CartoonReaderWeb.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.client.ClientProtocolException;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Utils.FileComparator;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookInfo;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookType;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Net.Client;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

public class Utils {
	private static final String TAG = "Utils";
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * * 将date格式化为2013-01-10 10:10:10的形式
	 * @param date Date 需要格式化的Date数据
	 * @return String 返回格式化之后的字符串
	 */
	public static String formatDate(Date date){
		sdf.setTimeZone(TimeZone.getDefault());
		return sdf.format(date);
	}
	
	/**
	 * 从xml文件中返回BookInfo列表，此方法使用反射，所以需要实体类的字段与xml中的tag名相同(忽略大小写)
	 * @param xmlFile xml文件
	 * @return List<BookInfo> 返回BookInfo的列表
	 * @throws XmlPullParserException 
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static List<BookInfo> formatBookInfo(File xmlFile) throws XmlPullParserException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new BufferedInputStream(new FileInputStream(xmlFile)),"utf-8");
		return formatBookInfo(parser);
	}
	
	/**
	 * 从xml(字符串)中返回BookInfo列表，此方法使用反射，所以需要实体类的字段与xml中的tag名相同(忽略大小写)。
	 * 如果需要从文件中获取列表，请使用formatResult(File xmlFile)方法
	 * @param content xml字符串
	 * @return List<BookInfo> 返回BookInfo的列表
	 * @throws XmlPullParserException 
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @see Utils.formatResult
	 */
	public static List<BookInfo> formatBookInfo(String content) throws XmlPullParserException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new ByteArrayInputStream(content.getBytes()),"utf-8");
		
		return formatBookInfo(parser);
	}
	
	//从parser中提取数据
	private static List<BookInfo> formatBookInfo(XmlPullParser parser) throws XmlPullParserException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		List<BookInfo> books = new ArrayList<BookInfo>();
		
		int type = parser.getEventType();
		BookInfo book = null;
		Class<?> clazz = null;
		Method[] methods = null;
		while(type != XmlPullParser.END_DOCUMENT){
			switch(type){
			case XmlPullParser.START_DOCUMENT:
				Log.i(TAG, "START_DOCUMENT");
				break;
			case XmlPullParser.START_TAG:
				Log.i(TAG, "START_TAG");
				if(parser.getName().equalsIgnoreCase("books")){
					break;
				}
				if(parser.getName().equalsIgnoreCase("book")){
					book = new BookInfo();
					clazz = BookInfo.class;
					methods = clazz.getMethods();
				}else{
					String name = parser.getName();
					String value = parser.nextText();
					for(Method method:methods){
						//Log.w(TAG,"Method Name:" + method.getName());
						if(method.getName().equalsIgnoreCase("set" + name)){
							method.invoke(book, value);
							Log.i(TAG,"匹配到Method Name:" + method.getName());
							break;
						}
					}
				}
				break;
			case XmlPullParser.END_TAG:
				Log.i(TAG, "END_TAG");
				if(parser.getName().equalsIgnoreCase("book")){
					books.add(book);
					downloadFile(Constants.URL + book.getCoverPath(), Constants.PICTEMP);
				}
				break;
			}
			type = parser.next();
		}
		
		return books;
	}
	
	/**
	 * 从xml文件中返回BookType列表，此方法使用反射，所以需要实体类的字段与xml中的tag名相同(忽略大小写)
	 * @param xmlFile xml文件
	 * @return List<BookInfo> 返回BookType的列表
	 * @throws XmlPullParserException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static List<BookType> formatBookType(File xmlFile) throws XmlPullParserException, IllegalArgumentException, IOException, IllegalAccessException, InvocationTargetException{
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new BufferedInputStream(new FileInputStream(xmlFile)),"utf-8");
		return formatBookType(parser);
	}
	
	/**
	 * 从xml(字符串)中返回BookType列表，此方法使用反射，所以需要实体类的字段与xml中的tag名相同(忽略大小写)。
	 * 如果需要从文件中获取列表，请使用formatResult(File xmlFile)方法
	 * @param content xml字符串
	 * @return List<BookInfo> 返回BookType的列表
	 * @throws XmlPullParserException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @see Utils.formatResult
	 */
	public static List<BookType> formatBookType(String content) throws XmlPullParserException, IllegalArgumentException, IOException, IllegalAccessException, InvocationTargetException{
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new ByteArrayInputStream(content.getBytes()),"utf-8");
		return formatBookType(parser);
	}
	
	
	//从parser中提取数据
	private static List<BookType> formatBookType(XmlPullParser parser) throws XmlPullParserException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		List<BookType> types = new ArrayList<BookType>();
		List<BookType> childTypes = new ArrayList<BookType>();
		
		int eventType = parser.getEventType();
		BookType type = null;
		Class<?> clazz = null;
		Method[] methods = null;
		while(eventType != XmlPullParser.END_DOCUMENT){
			switch(eventType){
			case XmlPullParser.START_DOCUMENT:
				Log.i(TAG, "START_DOCUMENT");
				break;
			case XmlPullParser.START_TAG:
				Log.i(TAG, "START_TAG");
				if(parser.getName().equalsIgnoreCase("books")){
					break;
				}
				if(parser.getName().equalsIgnoreCase("type")){
					type = new BookType();
					clazz = BookType.class;
					methods = clazz.getMethods();
				}else{
					String name = parser.getName();
					String value = parser.nextText();
					for(Method method:methods){
						//Log.w(TAG,"Method Name:" + method.getName());
						if(method.getName().equalsIgnoreCase("set" + name)){
							method.invoke(type, value);
							Log.i(TAG,"匹配到Method Name:" + method.getName());
							break;
						}
					}
				}
				break;
			case XmlPullParser.END_TAG:
				Log.i(TAG, "END_TAG");
				if(parser.getName().equalsIgnoreCase("type")){
					if(type.getTypeCode().length() > 3){
						childTypes.add(type);
					}else{
						types.add(type);
					}
				}
				break;
			}
			eventType = parser.next();
		}
		for(BookType t:types){
			for(int i = 0;i < childTypes.size();i++){
				BookType child = childTypes.get(i);
				if(child.getTypeCode().startsWith(t.getTypeCode())){
					t.getChildType().add(child);
				}
			}
		}
		childTypes = null;
		return types;
	}

	
	/**
	 * 将获得的xml字符串写入文件,路径为Constant.XMLTEMP
	 * @see Constants.XMLTEMP
	 * @param content 传入的xml字符串
	 * @param fileName 写入的文件名
	 * @return 如果写入成功返回true，写入失败返回false
	 */
	public static boolean write2Temp(String content,String fileName) {
		Log.v(TAG,content);
		if(!sdcardExsits()){
			return false;
		}
		File tempdir = new File(Constants.XMLTEMP);
		if(!tempdir.exists()){
			boolean isSuccess = tempdir.mkdirs();
			if(!isSuccess){
				Log.v(TAG,"创建文件夹失败:" + tempdir.getPath());
				return false;
			}
		}
		File tempFile = new File(tempdir,fileName);
		if(tempFile.exists()){
			tempFile.delete();
		}
		try {
			Log.v(TAG,tempFile.getPath());
			tempFile.createNewFile();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile),"utf-8"));
			bw.write(content);
			bw.flush();
			bw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void showMsg(Context context,String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 判断SDCard是否已经挂载
	 * @return boolean 已挂载，返回true，其他，返回false。
	 */
	public static boolean sdcardExsits(){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 下载文件，下载ZIP和封面图片时使用
	 * @param url URL地址
	 * @param savePath 保存的路径
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void downloadFile(String url,String savePath) throws ClientProtocolException, IOException{
		String fileName = url.substring(url.lastIndexOf("/") + 1);
		Log.v(TAG,"fileName:" + fileName);
		InputStream is;
		File dir = new File(savePath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		File saveFile = new File(dir,fileName);
		if(!saveFile.exists()){
			is = Client.requestFile(url);
			saveFile.createNewFile();
		}else{
			return;//文件已经存在直接返回
		}
		
		OutputStream os = new FileOutputStream(saveFile);
		byte[] buffer = new byte[1024];
		int len = -1;
		while((len = is.read(buffer)) != -1){
			os.write(buffer, 0, len);
		}
		is.close();
		os.flush();
		os.close();
	}
	
	/**
	 * 获得一个宽45高48的Bitmap图片，nopic的分辨率
	 * @param context
	 * @param path
	 * @return
	 */
	public static Bitmap getCoverImage(Context context,String path){
		Log.v(TAG,"图片路径:" + path);
		String fileName = path.substring(path.lastIndexOf("/"));
		String localPath = Constants.PICTEMP + fileName;
		File picFile = new File(localPath);
		if(!picFile.exists()){
			BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(R.drawable.nopic);
			return drawable.getBitmap();
		}
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(localPath,opts);
		//opts.inSampleSize = (opts.outWidth / holder.imagePath.getWidth() + opts.outHeight / holder.imagePath.getHeight()) / 2;
		opts.inSampleSize = (opts.outWidth / 45 + opts.outHeight / 48) / 2;
		opts.inJustDecodeBounds = false;
		bmp = BitmapFactory.decodeFile(localPath,opts);
		return bmp;
	}
	
	/**
	 * 根据文件的服务器路径获得本地存储的路径
	 * @param remotePath
	 * @return
	 */
	public static String getLocalPath(String remotePath){
		String fileName = remotePath.substring(remotePath.lastIndexOf("/") + 1);
		Log.v(TAG,"服务器路径为：" + remotePath + "  获得的文件名为:" + fileName);
		return Constants.ZIPTEMP + "/" + fileName;
	}
	
	/**
	 * 解压文件，解压到压缩文件路径移除后缀部分而形成的新路径中。比如：/sdcard/file.zip,解压路径为:/sdcard/file
	 * @param filePath 压缩文件路径
	 */
	public static boolean unZipFile(String filePath){
		Log.v(TAG,"filePath:" + filePath);
		File file = new File(filePath);
		if(!file.exists()){
			return false;
		}
		try {
			ZipFile zip = new ZipFile(file.getPath(),"GBK");
			@SuppressWarnings("rawtypes")
			Enumeration enums = zip.getEntries();
			
			while(enums.hasMoreElements()){
				ZipEntry entry = (ZipEntry) enums.nextElement();
				String source = entry.getName();
				String tmp = file.getPath();
				Log.v(TAG,"tmp:" + tmp);
				File targetParent = new File(tmp.substring(0, tmp.lastIndexOf(".") - 1));
				File target = new File(targetParent,source);
				if(entry.isDirectory()){
					target.mkdirs();
				}else{
					InputStream is = zip.getInputStream(entry);
					File parent = target.getParentFile();
					if(!parent.exists()){
						parent.mkdirs();
					}
					OutputStream os = new FileOutputStream(target);
					byte[] buff = new byte[4096];
					int len = -1;
					while((len = is.read(buff)) != -1){
						os.write(buff,0,len);
					}
					is.close();
					os.flush();
					os.close();
				}
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 将表中UnZipPath路径转换成本地阅读路径，因为事先不知道解压后路径下的目录结构，所以需要搜索以确定是否有图片
	 * 出现任何问题都将返回null
	 * @param path 
	 * @param position
	 * @return
	 */
	public static String getImagePath(String path,int position){
		//如果传递的路径不存在直接返回null
		File file = new File(path);
		if(!file.exists()){
			Log.i(TAG, "阅读路径不存在");
			return null;
		}
		//如果是文件并且是图片，返回图片路径，如果不是图片返回null
		if(!file.isDirectory()){
			if(rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Utils.Utils.isPic(file.getPath())){
				return file.getPath();
			}else{
				return null;
			}
		}
		//是目录的情况
		File[] files = getFiles(file);
		if(files.length < 1){
			return null;
		}
		Log.v(TAG,"Path:" + file.getPath() + "  得到的文件数量：" + files.length);
		Arrays.sort(files, FileComparator.getInstance());
		Log.i(TAG, "position:" + position);
		if(position > files.length - 1){
			return files[0].getPath();
		}else{
			return files[position].getPath();
		}
	}
	
	//通过循环获得自定目录下的所有图片
	private static File[] getFiles(File root){
		File[] files = root.listFiles(filter);
		if(files.length == 0){
			List<File> fileList = new ArrayList<File>();
			files = root.listFiles();
			for(File file:files){
				if(file.isDirectory()){
					File[] fs = getFiles(file);
					for(File f:fs){
						fileList.add(f);
					}
				}
			}
			files = fileList.toArray(new File[]{});
		}
		return files;
	}
	
	//文件过滤器,过滤非图片文件
	//参见rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Utils.Utils.isPic
	private static FileFilter filter = new FileFilter(){
		@Override
		public boolean accept(File file) {
			Log.v(TAG,"执行filter");
			return rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Utils.Utils.isPic(file.getPath());
		}
	};
}
