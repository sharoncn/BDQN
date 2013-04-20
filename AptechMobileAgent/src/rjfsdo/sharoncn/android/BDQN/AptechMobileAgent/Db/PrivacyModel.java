package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Db;

import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Constants;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

//提供数据库操作方法
//数据库中记录所有被保护的程序
//数据库表只存放包名就够了
//感觉这个DAO层可要可不要,如果要这一层,还需要传递ContentResolver
public class PrivacyModel {
	private static final String TAG = "PrivacyDao";
	private static PrivacyModel me;
	private static AppPrivacyDBHelper mHelper;
	private static SQLiteDatabase db;
	private static ContentResolver mResolver;

	static {
		me = new PrivacyModel();
	}

	private PrivacyModel() {
	}

	public static PrivacyModel getInstance(Context context, ContentResolver resolver) {
		if (mHelper == null) {
			mHelper = new AppPrivacyDBHelper(context);
		}
		if (db == null) {
			db = mHelper.getWritableDatabase();
		}
		if (mResolver == null) {
			mResolver = resolver;
		}
		return me;
	}

	// 根据包名查找是否已添加锁屏保护
	public boolean find(String pkgName) {
		final Cursor cursor = mResolver.query(Uri.parse(Constants.PRIVACY_CONTENT_URI), null, "packageName=?",
				new String[] { pkgName }, null);
		boolean flag = cursor.moveToFirst();
		cursor.close();
		return flag;
	}

	// 添加锁保护记录
	public void insert(String pkgName) {
		final ContentValues values = new ContentValues();
		values.put("packageName", pkgName);
		mResolver.insert(Uri.parse(Constants.PRIVACY_CONTENT_URI), values);
	}

	// 删除一条锁保护记录
	public void delete(String pkgName) {
		mResolver.delete(Uri.parse(Constants.PRIVACY_CONTENT_URI), "packageName=?", new String[] { pkgName });
	}

	// 查找全部锁保护信息
	public ArrayList<String> findAll() {
		final ArrayList<String> apps = new ArrayList<String>();
		final Cursor cursor = mResolver.query(Uri.parse(Constants.PRIVACY_CONTENT_URI), null, null, null, null);
		// Cursor cursor = db.query(Constants.PRIVACY_TABLE_NAME, null, null,
		// null, null, null, null);

		final int count = cursor.getColumnCount();
		for (int i = 0; i < count; i++) {
			Log.i(TAG, cursor.getColumnName(i));
		}

		while (cursor.moveToNext()) {
			apps.add(cursor.getString(cursor.getColumnIndex("packageName")));// packageName
		}

		Log.v(TAG, "APPS SIZE:" + apps.size());
		cursor.close();
		return apps;
	}

	// 创建修改密码对话框
	public Dialog showPWDDialog(Context context) {
		// 这个Dialog放这一层不太合适吧
		return null;
	}

	public void finish() {
		if (db != null) {
			db.close();
		}
	}
}
