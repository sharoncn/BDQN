package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Constants;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//准备设计成这样,当网络没问题,则从网络下载到某个表数据并清除数据库这个表的数据
//并重新写入。如果没有下载到这个表的数据就从数据库读取。这么设计起来挺复杂的。
//要考虑到，数据库中的数据什么时候清理，那些内容可以被清理。
//先写网络获取部分，如果有时间在思考这部分内容吧
public class WeiboDBHelper extends SQLiteOpenHelper {
	private static final String TAG = "WeiboDBHelper";

	public WeiboDBHelper(Context context) {
		super(context, Constants.DATABASE_NAME, null, Constants.VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createUser(db, Constants.TABLE_USER);
		createComment(db, Constants.TABLE_COMMENT);
		createStatus(db, Constants.TABLE_STATUS);
		createFavorite(db, Constants.TABLE_FAVORITE);
		createTag(db, Constants.TABLE_TAG);
		createVisible(db, Constants.TABLE_VISIBLE);
	}

	private void createVisible(SQLiteDatabase db, String tableVisible) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + tableVisible + "(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"type INTEGER," +
				"list_id INTEGER)");
		Log.i(TAG, tableVisible + " is created!");
	}

	private void createTag(SQLiteDatabase db, String tableTag) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + tableTag + "(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"id VARCHAR(50)," +
				"tag VARCHAR(50)," +
				"count INTEGER)");
		Log.i(TAG, tableTag + " is created!");
	}

	private void createFavorite(SQLiteDatabase db, String tableFavorite) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + tableFavorite + "(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"statusid INTEGER," +
				"tagid INTEGER," +
				"favorited_time VARCHAR(50)," +
				"FOREIGN KEY(statusid) REFERENCES " + Constants.TABLE_STATUS + "(_id)," +
				"FOREIGN KEY(tagid) REFERENCES " + Constants.TABLE_TAG + "(_id))");
		Log.i(TAG, tableFavorite + " is created!");
	}

	private void createStatus(SQLiteDatabase db, String tableStatus) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + tableStatus + "(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"created_at VARCHAR(50)," +
				"id VARCHAR(50)," +
				"pid VARCHAR(50)," +
				"mid VARCHAR(50)," +
				"idstr VARCHAR(50)," +
				"text VARCHAR(255)," +
				"source VARCHAR(50)," +
				"favorited VARCHAR(50)," +
				"truncated VARCHAR(50)," +
				"in_reply_to_status_id VARCHAR(50)," +
				"in_reply_to_user_id VARCHAR(50)," +
				"in_reply_to_screen_name VARCHAR(50)," +
				"geo VARCHAR(50)," +
				"reposts_count VARCHAR(50)," +
				"comments_count VARCHAR(50)," +
				"attitudes_count VARCHAR(50)," +
				"mlevel VARCHAR(50)," +
				"thumbnail_pic VARCHAR(50)," +
				"bmiddle_pic VARCHAR(50)," +
				"original_pic VARCHAR(50)," +
				"visibleid INTEGER," +
				"FOREIGN KEY(visibleid) REFERENCES " + Constants.TABLE_VISIBLE + "(_id))");
		Log.i(TAG, tableStatus + " is created!");
	}

	private void createComment(SQLiteDatabase db, String tableComment) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + tableComment + "(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"id VARCHAR(50)," +
				"text VARCHAR(255)," +
				"source VARCHAR(50)," +
				"mid VARCHAR(50)," +
				"idstr VARCHAR(50)," +
				"created_at VARCHAR(50)," +
				"userid INTEGER," +
				"statusid INTEGER," +
				"FOREIGN KEY(statusid) REFERENCES " + Constants.TABLE_STATUS + "(_id)," +
				"FOREIGN KEY(userid) REFERENCES " + Constants.TABLE_USER + "(_id))");
		Log.i(TAG, tableComment + " is created!");
	}

	private void createUser(SQLiteDatabase db, String tableUser) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + tableUser + "(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"id VARCHAR(50)," +
				"idstr VARCHAR(50)," +
				"screen_name VARCHAR(50)," +
				"name VARCHAR(50)," +
				"province VARCHAR(50)," +
				"city VARCHAR(50)," +
				"location VARCHAR(50)," +
				"description VARCHAR(50)," +
				"url VARCHAR(50)," +
				"profile_image_url VARCHAR(50)," +
				"profile_url VARCHAR(50)," +
				"domain VARCHAR(50)," +
				"weihao VARCHAR(50)," +
				"gender VARCHAR(50)," +
				"followers_count VARCHAR(50)," +
				"friends_count VARCHAR(50)," +
				"statuses_count VARCHAR(50)," +
				"favourites_count VARCHAR(50)," +
				"created_at VARCHAR(50)," +
				"following VARCHAR(50)," +
				"allow_all_act_msg VARCHAR(50)," +
				"geo_enabled VARCHAR(50)," +
				"verified VARCHAR(50)," +
				"verified_type VARCHAR(50)," +
				"remark VARCHAR(50)," +
				"allow_all_comment VARCHAR(50)," +
				"avatar_large VARCHAR(50)," +
				"verified_reason VARCHAR(50)," +
				"follow_me VARCHAR(50)," +
				"online_status VARCHAR(50)," +
				"bi_followers_count VARCHAR(50)," +
				"lang VARCHAR(50)," +
				"star VARCHAR(50)," +
				"mbtype VARCHAR(50)," +
				"mbrank VARCHAR(50)," +
				"block_word VARCHAR(50)," +
				"statusid INTEGER," +
				"FOREIGN KEY(statusid) REFERENCES " + Constants.TABLE_STATUS + "(_id))");
		Log.i(TAG, tableUser + " is created!");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
