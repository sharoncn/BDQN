package rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.os.Environment;
import android.util.Log;

public class FileUtil {
	private static final String TAG = "FileUtil";

	/**
	 * ��SD������ʱ����SD����·������������ڷ���NULL
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
	 * @param is ��
	 * @param filePath ָ�����ļ�·��
	 * @return �ɹ�����true��ʧ�ܷ���false
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
	 * �ж�ͼƬ�Ƿ����
	 * @param path ͼƬ·��
	 * @return ���ڷ���true�����򷵻�false
	 */
	public static boolean imageExists(String path){
		File file = new File(path);
		return file.exists();
	}
}
