package rjfsdo.sharoncn.android.BDQN.ImageViewer.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.ImageViewer.Models.ImageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;

public class ImageFinder {
	private static final String TAG = "ImageFinder";

	/**
	 * 返回图片目录获取的图片列表,返回的图片为宽度为80的缩略图
	 * @return 如果返回NULL表示未能获取到图片
	 */
	public static List<ImageInfo> findImages(){
		List<ImageInfo> images = findImagesWithoutBmp();
		return images;
	}
	
	/**
	 * 返回图片目录获取的图片列表
	 * @return 如果返回NULL表示未能获取到图片
	 */
	public static List<ImageInfo> findImagesWithoutBmp(){
		ArrayList<ImageInfo> images = new ArrayList<ImageInfo>();
		File dir = new File(Constants.IMAGE_PATH);
		if(!dir.exists()){
			return null;
		}
		getImages(images, dir.getPath());
		return images;
	}
	
	//迭代遍历图片
	private static void getImages(List<ImageInfo> images, String path){
		File root = new File(path);
		if(!root.exists()){
			return;
		}
		File[] files;
		File[] dirs;
		if(root.isDirectory()){
			files = root.listFiles(ImageFilter.getInstance());
			if(files == null){
				return;
			}
			for(File file:files){
				ImageInfo imageinfo = new ImageInfo();
				imageinfo.setName(file.getName());
				imageinfo.setPath(file.getPath());
				imageinfo.setSize(file.length());
				imageinfo.setTime(new Date(file.lastModified()));
				images.add(imageinfo);
			}
			dirs = root.listFiles(DirectoryFilter.getInstance());
			if(dirs == null){
				return;
			}
			for(File dir:dirs){
				getImages(images, dir.getPath());
			}
		}
	}
	
	/**
	 * 根据传递过来的ImageInfo对象获得一个符合尺寸的Bitmap对象
	 * @param image 传递过来的ImageInfo对象
	 * @param _width 期望的宽度
	 * @param _height 期望的高度
	 * @return 得到的bitmap
	 */
	public static Bitmap inflateImage(ImageInfo image,int _width,int _height){
			Options opts = new Options();
			Log.v(TAG,"DecodeBitmap_Path:" + image.getPath());
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(image.getPath(),opts);
			//opts.inSampleSize = (opts.outWidth / 45 + opts.outHeight / 48) / 2;
			//Log.v(TAG,"opts.outWidth/opts.outHeight:" + opts.outWidth + "/" + opts.outHeight);
			opts.inSampleSize = computeSampleSize(opts, -1, _width * _height);
			Log.v(TAG,"opts.inSampleSize:" + opts.inSampleSize);
			opts.inJustDecodeBounds = false;
			Bitmap bmp = BitmapFactory.decodeFile(image.getPath(),opts);
			Log.v(TAG,"bmp.getWidth()/bmp.getHeight():" + bmp.getWidth() + "/" + bmp.getHeight());
			return bmp;
	}
	
	
	/**
	 * 计算合适的inSampleSize值(android源代码中示例)
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options,int minSideLength, int maxNumOfPixels) {
	    int initialSize = computeInitialSampleSize(options, minSideLength,
	            maxNumOfPixels);
	    int roundedSize;
	    if (initialSize <= 8) {
	        roundedSize = 1;
	        while (roundedSize < initialSize) {
	            roundedSize <<= 1;
	        }
	    } else {
	        roundedSize = (initialSize + 7) / 8 * 8;
	    }
	    return roundedSize;
	}
	
	private static int computeInitialSampleSize(BitmapFactory.Options options,int minSideLength, int maxNumOfPixels) {
	    double w = options.outWidth;
	    double h = options.outHeight;
	    
	    int lowerBound = (maxNumOfPixels == -1) ? 1 :(int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
	    int upperBound = (minSideLength == -1) ? 128 :(int) Math.min(Math.floor(w / minSideLength),Math.floor(h / minSideLength));
	    if (upperBound < lowerBound) {
	        return lowerBound;
	    }
	    
	    if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
	        return 1;
	    } else if (minSideLength == -1) {
	        return lowerBound;
	    } else {
	        return upperBound;
	    }
	} 
}
