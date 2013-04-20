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
 * ͼƬ����
 * ����ֻ���𻺴�ͼƬ
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

	// private static ReferenceQueue<Bitmap> queue;//��ʧЧ���У���δʹ��
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
	 * ��ImageCache�з���һ��ͼƬ
	 * 
	 * @param key
	 *            keyΪͼƬ��URL
	 * @param value
	 *            ͼƬ
	 * @return �Ƿ���óɹ�
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
		Log.i(TAG,"������ͼƬ����:" + mCache.size());
		if(mCache.size() < Constants.IMAGECACHE_MAX){
			return;
		}
		Log.i(TAG,"����ﵽ�����������ʼ������");
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
	 * �õ�һ��ͼƬ
	 * 
	 * @param key
	 *            ͼƬURL
	 * @return ͼƬ����NULL
	 */
	public Bitmap get(String key) {
		// �����������,�ڻ�������
		if (mCache.containsKey(key)) {
			// ��������ҲʧЧ��̫���˰ɣ��ռ����˼���ͼƬ����û���浽SD�����Ѿ�ʧЧ�ˡ�
			// ��������ϲ��ԣ������ֻ�������Ӧ��̫�࣬12:58:52ͼƬ�״μ��أ��ٴ�ʹ��12:58:55�����ֻ3�룬ͼƬ�����Ѿ�ʧЧ��
			// ��ʱͼƬ��δ��д�뵽SD��������ͼƬcompress��SD����Ҫ10�롣������ȥ�������ء�
			// �����治�С��Ҿ��û��ǲ�ʹ�������ã�mCache��ֱ�ӷ���bitmapǿ���ã���һ�ֲ���ȥ�ֶ������������getImage������Ȩ����
			// ͼƬ���ֶ����ա�
			if (mCache.get(key).isRecycled) {
				Log.v(TAG, "��Key�����������Ѿ�ʧЧ");
				// �������Ѿ�ʧЧ,��ô��Ҫ����������,�����Ŀ��Բ��ܡ������߼�����SD���в��ң�����Ҳ������ٴ�����
				mCache.remove(key);
			} else {
				Bitmap bmp = mCache.get(key).getBmp();
				// ����������У�����ͼƬ
				return bmp;
			}
		}
		// ���������û��,�����ӳ��,���Դ��ļ�����
		if (mProjection.containsKey(key)) {
			Bitmap bmp = getImageFromFile(key, mProjection.get(key) + FILE_EXT);
			// Ҳ�п�����ӳ��,����ͼƬ��������
			if (bmp != null) {
				// ���������û�У���SD�����У���ô�ٴμ��뻺����
				put(key, weightBitmap(bmp));
				return bmp;
			}
		}
		// ������ò���,�Լ�ȥ������
		return null;
	}

	/**
	 * ���ļ��л��һ��ͼƬ
	 * 
	 * @param key
	 *            ͼƬ���ļ���
	 * @return ͼƬ����null
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
	 * ����ӳ���б�
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
				throw new IllegalArgumentException("�������Ϸ�");
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
