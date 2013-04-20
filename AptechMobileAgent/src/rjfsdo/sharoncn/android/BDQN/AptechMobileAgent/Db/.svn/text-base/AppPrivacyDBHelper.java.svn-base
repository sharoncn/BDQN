package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Db;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Constants;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppPrivacyDBHelper extends SQLiteOpenHelper {
	private static final String TAG = "AppPrivacyDBHelper";
	private static final int version = 1;

	public AppPrivacyDBHelper(Context context) {
		super(context, Constants.PRIVACY_DB_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		final String sql = "CREATE TABLE IF NOT EXISTS " + Constants.PRIVACY_TABLE_NAME + "(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," + 
				"packageName VARCHAR(255) UNIQUE)";
		db.execSQL(sql);
		Log.i(TAG, Constants.PRIVACY_TABLE_NAME + " is created!");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
