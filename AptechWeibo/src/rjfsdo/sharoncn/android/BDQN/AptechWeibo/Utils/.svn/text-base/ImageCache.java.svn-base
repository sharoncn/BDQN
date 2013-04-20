package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 图片缓存
 * 此类只负责缓存图片
 * 
 * @author sharoncn
 * 
 */
public class ImageCache {
	public static final String FILE_EXT = ".png";
	private static final String TAG = "ImageCache";
	private static ImageCache me;
	private static HashMap<String, WeightImage> mCache = new HashMap<String, WeightImage>();
	private static HashMap<String, String> mProjection = new HashMap<String, String>();

	// private static ReferenceQueue<Bitmap> queue;//已失效队列，暂未使用
	// private static Context mContext;

	static {
		me = new ImageCache();
		// queue = new ReferenceQueue<Bitmap>();
	}

	private ImageCache() {
	}

	public static ImageCache getInstance(Context context) {
		// mContext = context;
		Log.v(TAG, "mProjection is " + mProjection);
		if (mProjection.size() <= 0) {
			new GetProjectionThread().start();
		}
		return me;
	}

	/**
	 * 向ImageCache中放入一张图片
	 * 
	 * @param key
	 *            key为图片的URL
	 * @param value
	 *            图片
	 * @return 是否放置成功
	 */
	public boolean put(String key, Bitmap value) {
		Log.v(TAG, "key is:" + key);
		if (!mCache.containsKey(key)) {
			protectedCache();
			mCache.put(key, weightBitmap(value));
			if (!mProjection.containsKey(key)) {
				String uuid = UUID.randomUUID().toString();
				mProjection.put(key, uuid);
				new SaveImageThread(uuid + FILE_EXT, value).start();
			}
			return true;
		} else {
			return false;
		}
	}
	
	private void put(String key, WeightImage value){
		protectedCache();
		mCache.put(key, value);
	}

	private void protectedCache(){
		Log.i(TAG,"缓存中图片数量:" + mCache.size());
		if(mCache.size() < Constants.IMAGECACHE_MAX){
			return;
		}
		Log.i(TAG,"缓存达到最大容量，开始清理缓存");
		WeightImage current = null;
		String currentKey = null;
		final Iterator<String> it = mCache.keySet().iterator();
		final long now = new Date().getTime();
		while(it.hasNext()){
			String key = it.next();
			if(current == null){
				current = mCache.get(key);
				currentKey = key;
				current.calculateWeight(now);
				continue;
			}
			WeightImage another = mCache.get(key);
			another.calculateWeight(now);
			if(current.compareTo(another) == 1){
				current = another;
				currentKey = key;
			}
		}
		current.destory();
		mCache.remove(currentKey);
	}
	
	private WeightImage weightBitmap(Bitmap bmp) {
		return new WeightImage(bmp);
	}

	/**
	 * 得到一张图片
	 * 
	 * @param key
	 *            图片URL
	 * @return 图片或者NULL
	 */
	public Bitmap get(String key) {
		// 如果缓存中有,在缓存中拿
		if (mCache.containsKey(key)) {
			// 这软引用也失效的太快了吧，刚加载了几张图片，还没保存到SD卡就已经失效了。
			// 我在真机上测试，可能手机启动的应用太多，12:58:52图片首次加载，再次使用12:58:55，间隔只3秒，图片引用已经失效了
			// 这时图片还未能写入到SD卡，测试图片compress到SD卡需要10秒。所以又去网上下载。
			// 这样真不行。我觉得还是不使用软引用，mCache中直接放置bitmap强引用，做一种策略去手动清理，比如根据getImage次数加权处理
			// 图片的手动回收。
			if (mCache.get(key).isRecycled) {
				Log.v(TAG, "有Key，但是引用已经失效");
				// 软引用已经失效,那么需要清理软引用,其他的可以不管。根据逻辑会在SD卡中查找，如果找不到会再次下载
				mCache.remove(key);
			} else {
				Bitmap bmp = mCache.get(key).getBmp();
				// 如果缓存中有，返回图片
				return bmp;
			}
		}
		// 如果缓存中没有,如果有映射,尝试从文件中拿
		if (mProjection.containsKey(key)) {
			Bitmap bmp = getImageFromFile(key, mProjection.get(key) + FILE_EXT);
			// 也有可能有映射,但是图片被清理了
			if (bmp != null) {
				// 如果缓存中没有，而SD卡中有，那么再次加入缓存中
				put(key, weightBitmap(bmp));
				return bmp;
			}
		}
		// 如果还拿不到,自己去网上下
		return null;
	}

	/**
	 * 从文件中获得一张图片
	 * 
	 * @param key
	 *            图片的文件名
	 * @return 图片或者null
	 */
	private Bitmap getImageFromFile(String key, String fileName) {
		final String root = Util.getRootDir();
		if (root != null) {
			File file = new File(root + Constants.IMAGE_CACHE_DIR + "/" + fileName);
			if (file.exists()) {
				return BitmapFactory.decodeFile(file.getPath());
			}
		}

		return null;
	}

	/**
	 * 保存映射列表
	 */
	public void saveProjection() {
		new SaveProjectionThread().start();
	}

	static class GetProjectionThread extends Thread {
		@Override
		public void run() {
			synchronized (mProjection) {
				Gson gson = new Gson();
				String json = Util.readTextFromFile(Constants.CACHE_DIR + "/projection.txt");
				if (json == null) {
					return;
				}
				mProjection = gson.fromJson(json, new TypeToken<HashMap<String, String>>() {
				}.getType());
			}
		}
	}

	class SaveProjectionThread extends Thread {
		@Override
		public void run() {
			synchronized (mProjection) {
				Gson gson = new Gson();
				String json = gson.toJson(mProjection);
				Util.saveFile(new ByteArrayInputStream(json.getBytes()), Constants.CACHE_DIR + "/projection.txt");
			}
		}
	}

	class SaveImageThread extends Thread {
		private String fileName;
		private Bitmap bmp;

		public SaveImageThread(String fileName, Bitmap bmp) {
			if (fileName == null || "".equals(fileName) || bmp == null) {
				throw new IllegalArgumentException("参数不合法");
			}
			this.fileName = fileName;
			this.bmp = bmp;
		}

		@Override
		public void run() {
			final String filePath = Constants.IMAGE_CACHE_DIR + "/" + fileName;
			write2File(filePath);
		}

		private synchronized boolean write2File(String path) {
			if (!Util.createFile(path)) {
				return false;
			}
			File file = new File(path);
			try {
				FileOutputStream fos = new FileOutputStream(file);
				if (bmp.compress(Bitmap.CompressFormat.PNG, 100, fos)) {
					fos.flush();
					fos.close();
					return true;
				}
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	class WeightImage implements Comparable<WeightImage>{
		private Bitmap bmp;
		private double weight = 0;
		private boolean isRecycled = false;
		private long time = 0l;
		private int invokeCount = 0;

		public WeightImage(Bitmap bmp) {
			this.setBmp(bmp);
		}

		public Bitmap getBmp() {
			invokeCount++;
			return bmp;
		}

		public void setBmp(Bitmap bmp) {
			if(this.bmp != null){
				return;
			}
			this.bmp = bmp;
			time = new Date().getTime();
		}

		public void destory() {
			this.weight = 0;
			this.bmp.recycle();
			isRecycled = true;
			this.bmp = null;
		}

		public boolean isRecycled() {
			return isRecycled;
		}

		public double calculateWeight(long timeNow) {
			weight = invokeCount * 100000 /(timeNow - time);
			return weight;
		}

		@Override
		public int compareTo(WeightImage another) {
			double sub = this.weight - another.weight;
			if(sub > 0){
				return 1;
			}else if(sub < 0){
				return -1;
			}
			return 0;
		}
	}
}
