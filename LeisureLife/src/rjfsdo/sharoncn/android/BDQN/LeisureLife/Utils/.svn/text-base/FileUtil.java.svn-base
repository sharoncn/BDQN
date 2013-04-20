package rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.os.Environment;
import android.util.Log;

public class FileUtil {
	private static final String TAG = "FileUtil";

	/**
	 * 当SD卡存在时返回SD卡的路径，如果不存在返回NULL
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
	 * @param is 流
	 * @param filePath 指定的文件路径
	 * @return 成功返回true，失败返回false
	 */
	public static boolean saveFile(InputStream is,String filePath) {
		File file = new File(filePath);
		if(!createFile(file.getPath())){
			return false;
		}
		try{
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buff = new byte[1024];
			int len = -1;
			while((len = is.read(buff)) != -1){
				fos.write(buff,0,len);
			}
			is.close();
			fos.flush();
			fos.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 判断图片是否存在
	 * @param path 图片路径
	 * @return 存在返回true，否则返回false
	 */
	public static boolean imageExists(String path){
		File file = new File(path);
		return file.exists();
	}
}
