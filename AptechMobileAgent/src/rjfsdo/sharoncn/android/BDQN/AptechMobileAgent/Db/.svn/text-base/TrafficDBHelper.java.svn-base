package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Db;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Constants;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TrafficDBHelper extends SQLiteOpenHelper {
	private static final int version = 1;
	private static final String TAG = "TrafficDBHelper";
	public static final String COLUMN_DAY = "day";
	public static final String COLUMN_STARTTRAFFIC = "startTraffic";
	public static final String COLUMN_ENDTRAFFIC = "endTraffic";
	public static final String COLUMN_STARTTRAFFICUPLOAD = "startTrafficUpload";
	public static final String COLUMN_ENDTRAFFICUPLOAD = "endTrafficUpload";
	public static final String COLUMN_STARTTRAFFICDOWNLOAD = "startTrafficDownload";
	public static final String COLUMN_ENDTRAFFICDOWNLOAD = "endTrafficDownload";
	public static final String COLUMN_STARTALLPACKAGES = "startAllPackages";
	public static final String COLUMN_ENDALLPACKAGES = "endAllPackages";
	
	public TrafficDBHelper(Context context) {
		super(context, Constants.TRAFFIC_DB_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		final String sql = "CREATE TABLE IF NOT EXISTS " + Constants.TRAFFIC_TABLE_NAME + "(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," + 
				"day CHAR(15) NOT NULL UNIQUE," +
				"startTraffic INTEGER," +
				"endTraffic INTEGER," +
				"startTrafficUpload INTEGER," +
				"endTrafficUpload INTEGER," +
				"startTrafficDownload INTEGER," +
				"endTrafficDownload INTEGER," +
				"startAllPackages INTEGER," +
				"endAllPackages INTEGER)";
		db.execSQL(sql);
		Log.i(TAG, Constants.TRAFFIC_DB_NAME + " is created!");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
