package rjfsdo.sharoncn.android.BDQN.ImageViewer.Utils;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	//private static final String TAG = "Util";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	/**
	 * 获得文件的后缀名
	 * @param file 文件
	 * @return String 后缀名
	 */
	public static String getFileExtName(File file){
		String name = file.getName();
		return name.substring(name.lastIndexOf(".") + 1);
	}
	
	/**
	 * 根据路径删除文件
	 * @param path 文件路径
	 * @return 成功返回true,失败返回false
	 */
	public static boolean deleteFile(String path){
		//Log.v(TAG,"Delete File:" + path);
		File file = new File(path);
		if(file.exists()){
			//Log.v(TAG,"File Exists");
			boolean flag = file.delete();
			//Log.v(TAG,"是否成功删除:" + flag);
			return flag;
		}else{
			//Log.v(TAG,"File Not Exists");
			return true;
		}
	}
	
	/**
	 * 格式化日期
	 * @param date 需要格式化的日期
	 * @return 格式化后的字符串
	 */
	public static String formatDate(Date date){
		return sdf.format(date);
	}
	
	
	/**
	 * 格式化小数，小数位数为2位
	 * @param value
	 * @return
	 */
	public static String formatDecimal(Object value){
		return df.format(value);
	}
	
}
