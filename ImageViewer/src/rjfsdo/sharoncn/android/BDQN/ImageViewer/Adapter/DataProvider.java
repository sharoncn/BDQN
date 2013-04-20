package rjfsdo.sharoncn.android.BDQN.ImageViewer.Adapter;

import java.util.List;

import rjfsdo.sharoncn.android.BDQN.ImageViewer.Models.ImageInfo;
import rjfsdo.sharoncn.android.BDQN.ImageViewer.Utils.ImageFinder;

/**
 * 一个静态类，用于保存所有图片信息，为两个Activity中的Adapter提供数据，这样免去了数据同步的麻烦
 * @author sharoncn
 *
 */
public class DataProvider {
	private static List<ImageInfo> images;
	
	/**
	 * 请首先执行initData()方法
	 * @param position
	 * @return 如果正确删除返回true，否则返回false
	 */
	public static boolean deleteImage(int position){
		if(images != null && position > images.size() - 1){
			return false;
		}
		images.remove(position);
		return true;
	}
	
	/**
	 * 请首先执行
	 */
	public static void initData(){
		images = ImageFinder.findImages();
	}
	
	/**
	 * 请首先执行initData()方法
	 * @return 返回所有的数据
	 */
	public static List<ImageInfo> getAllImages(){
		return images;
	}
}
