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
	 * ����ļ��ĺ�׺��
	 * @param file �ļ�
	 * @return String ��׺��
	 */
	public static String getFileExtName(File file){
		String name = file.getName();
		return name.substring(name.lastIndexOf(".") + 1);
	}
	
	/**
	 * ����·��ɾ���ļ�
	 * @param path �ļ�·��
	 * @return �ɹ�����true,ʧ�ܷ���false
	 */
	public static boolean deleteFile(String path){
		//Log.v(TAG,"Delete File:" + path);
		File file = new File(path);
		if(file.exists()){
			//Log.v(TAG,"File Exists");
			boolean flag = file.delete();
			//Log.v(TAG,"�Ƿ�ɹ�ɾ��:" + flag);
			return flag;
		}else{
			//Log.v(TAG,"File Not Exists");
			return true;
		}
	}
	
	/**
	 * ��ʽ������
	 * @param date ��Ҫ��ʽ��������
	 * @return ��ʽ������ַ���
	 */
	public static String formatDate(Date date){
		return sdf.format(date);
	}
	
	
	/**
	 * ��ʽ��С����С��λ��Ϊ2λ
	 * @param value
	 * @return
	 */
	public static String formatDecimal(Object value){
		return df.format(value);
	}
	
}
