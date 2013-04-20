package rjfsdo.sharoncn.android.BDQN.ImageViewer.Adapter;

import java.util.List;

import rjfsdo.sharoncn.android.BDQN.ImageViewer.Models.ImageInfo;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Utils.ImageFinder;

/**
 * һ����̬�࣬���ڱ�������ͼƬ��Ϣ��Ϊ����Activity�е�Adapter�ṩ���ݣ�������ȥ������ͬ�����鷳
 * @author sharoncn
 *
 */
public class DataProvider {
	private static List<ImageInfo> images;
	
	/**
	 * ������ִ��initData()����
	 * @param position
	 * @return �����ȷɾ������true�����򷵻�false
	 */
	public static boolean deleteImage(int position){
		if(images != null && position > images.size() - 1){
			return false;
		}
		images.remove(position);
		return true;
	}
	
	/**
	 * ������ִ��
	 */
	public static void initData(){
		images = ImageFinder.findImages();
	}
	
	/**
	 * ������ִ��initData()����
	 * @return �������е�����
	 */
	public static List<ImageInfo> getAllImages(){
		return images;
	}
}
