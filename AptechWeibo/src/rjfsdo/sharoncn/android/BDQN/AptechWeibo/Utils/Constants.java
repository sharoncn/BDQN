package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils;

import android.os.Environment;

public class Constants {
	/**
	 * 新浪微博APP_KEY
	 */
	public static final String APP_KEY = "373964023";
	/**
	 * 新浪微博回调地址
	 */
	public static final String REDIRECT_URL = "http://weibo.com/u/3177498754";
	
	/**
	 * 音效池最大容量
	 */
	public static final int SOUNDPOOL_MAX = 100;
	/**
	 * 音效开关
	 */
	public static boolean ISPLAYSOUND = true;
	
	/**
	 * 数据库名称
	 */
	public static final String DATABASE_NAME = "aptechweibo.db";
	/**
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	/**
	 * User表名
	 */
	public static final String TABLE_USER = "user";
	/**
	 * comment表名
	 */
	public static final String TABLE_COMMENT = "comment";
	/**
	 * status表名
	 */
	public static final String TABLE_STATUS = "status";
	/**
	 * favorite表名
	 */
	public static final String TABLE_FAVORITE = "favorite";
	/**
	 * tag表名
	 */
	public static final String TABLE_TAG = "tag";
	/**
	 * visible表名
	 */
	public static final String TABLE_VISIBLE = "visible";
	
	public static final String SDCARD = Environment.getExternalStorageDirectory().getPath();
	
	public static final String BASE_DIR = SDCARD + "/aptechweibo";
	
	public static final String IMAGE_CACHE_DIR = BASE_DIR + "/cache/images";
	
	public static final String CACHE_DIR = BASE_DIR + "/cache";
	public static final String PROVINCESFILE = CACHE_DIR + "/provinces.json";
	public static final String PROVINCE_URL = "http://api.t.sina.com.cn/provinces.json";
	/**
	 * 表情放大倍率
	 */
	public static final int MAGNIFICATION = 3;//表情放大倍率
	
	/**
	 * 图片缓存的最大缓存数量
	 */
	public static final int IMAGECACHE_MAX = 200;
	
	/**
	 * 最大输入字数
	 */
	public static final int CONTENT_INPUT_MAX = 140;
}
