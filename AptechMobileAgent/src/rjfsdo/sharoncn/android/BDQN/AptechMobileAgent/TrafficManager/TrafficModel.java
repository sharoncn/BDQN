package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.TrafficManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Db.TrafficDBHelper;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Models.TrafficInfo;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Constants;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Util;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TrafficModel {
	private static final String TAG = "TrafficModel";
	private static TrafficDBHelper helper;
	private static TrafficModel me;
	private static SQLiteDatabase db;

	static {
		me = new TrafficModel();
	}

	private TrafficModel() {
	}

	public static TrafficModel getInstance(Context context) {
		if (helper == null)
			helper = new TrafficDBHelper(context);
		if (db == null) {
			db = helper.getWritableDatabase();
		}
		return me;
	}

	/**
	 * 获得当天的流量记录
	 * 
	 * @return
	 */
	public synchronized TrafficInfo getTrafficRecord() {
		final Date now = new Date();
		final String date = Util.formatSimpleDate(now);
		TrafficInfo result = null;
		final Cursor cursor = db.query(Constants.TRAFFIC_TABLE_NAME, null, TrafficDBHelper.COLUMN_DAY + "=?",
				new String[] { date }, null, null, null);
		if (cursor.moveToFirst()) {
			result = new TrafficInfo();
			result.setEndAll(cursor.getDouble(cursor.getColumnIndex(TrafficDBHelper.COLUMN_ENDTRAFFIC)));
			result.setEndUpload(cursor.getDouble(cursor.getColumnIndex(TrafficDBHelper.COLUMN_ENDTRAFFICUPLOAD)));
			result.setEndDownload(cursor.getDouble(cursor.getColumnIndex(TrafficDBHelper.COLUMN_ENDTRAFFICDOWNLOAD)));
			result.setEndPackages(cursor.getLong(cursor.getColumnIndex(TrafficDBHelper.COLUMN_ENDALLPACKAGES)));
			result.setStartAll(cursor.getDouble(cursor.getColumnIndex(TrafficDBHelper.COLUMN_STARTTRAFFIC)));
			result.setStartUpload(cursor.getDouble(cursor.getColumnIndex(TrafficDBHelper.COLUMN_STARTTRAFFICUPLOAD)));
			result.setStartDownload(cursor.getDouble(cursor.getColumnIndex(TrafficDBHelper.COLUMN_STARTTRAFFICDOWNLOAD)));
			result.setStartPackages(cursor.getLong(cursor.getColumnIndex(TrafficDBHelper.COLUMN_STARTALLPACKAGES)));
		}
		cursor.close();
		return result;
	}

	/**
	 * 获取当月的流量记录
	 * @return
	 */
	public synchronized List<TrafficInfo> getMonthTrafficRecord() {
		final Date now = new Date();
		final String date = Util.formatSimpleDate(now);
		final Cursor cursor = db.query(Constants.TRAFFIC_TABLE_NAME, null, TrafficDBHelper.COLUMN_DAY + " like ?",
				new String[] { date.substring(0, date.lastIndexOf("-")) + "%" }, null, null, null);
		Log.v(TAG, "getMonthTrafficRecord:" + cursor.getCount());
		ArrayList<TrafficInfo> traffics = new ArrayList<TrafficInfo>();
		if (cursor.moveToNext()) {
			final TrafficInfo result = new TrafficInfo();
			result.setDay(cursor.getString(cursor.getColumnIndex(TrafficDBHelper.COLUMN_DAY)));
			result.setEndAll(cursor.getDouble(cursor.getColumnIndex(TrafficDBHelper.COLUMN_ENDTRAFFIC)));
			result.setEndUpload(cursor.getDouble(cursor.getColumnIndex(TrafficDBHelper.COLUMN_ENDTRAFFICUPLOAD)));
			result.setEndDownload(cursor.getDouble(cursor.getColumnIndex(TrafficDBHelper.COLUMN_ENDTRAFFICDOWNLOAD)));
			result.setEndPackages(cursor.getLong(cursor.getColumnIndex(TrafficDBHelper.COLUMN_ENDALLPACKAGES)));
			result.setStartAll(cursor.getDouble(cursor.getColumnIndex(TrafficDBHelper.COLUMN_STARTTRAFFIC)));
			result.setStartUpload(cursor.getDouble(cursor.getColumnIndex(TrafficDBHelper.COLUMN_STARTTRAFFICUPLOAD)));
			result.setStartDownload(cursor.getDouble(cursor.getColumnIndex(TrafficDBHelper.COLUMN_STARTTRAFFICDOWNLOAD)));
			result.setStartPackages(cursor.getLong(cursor.getColumnIndex(TrafficDBHelper.COLUMN_STARTALLPACKAGES)));
			traffics.add(result);
		}
		cursor.close();
		return traffics;
	}

	/**
	 * 设置结束流量
	 * 
	 * @param value
	 */
	public synchronized void setEndTraffic(TrafficInfo value) {
		final Date now = new Date();
		final String date = Util.formatSimpleDate(now);
		final ContentValues values = new ContentValues();
		final TrafficInfo last = getTrafficRecord();
		values.put(TrafficDBHelper.COLUMN_ENDTRAFFIC, last.getEndAll() + value.getEndAll());
		values.put(TrafficDBHelper.COLUMN_ENDTRAFFICUPLOAD, last.getEndUpload() + value.getEndUpload());
		values.put(TrafficDBHelper.COLUMN_ENDTRAFFICDOWNLOAD, last.getEndDownload() + value.getEndDownload());
		values.put(TrafficDBHelper.COLUMN_ENDALLPACKAGES, last.getEndPackages() + value.getEndPackages());
		db.update(Constants.TRAFFIC_TABLE_NAME, values, TrafficDBHelper.COLUMN_DAY + "=?", new String[] { date });
	}

	/**
	 * 查询当天记录是否已经存在
	 * 
	 * @return
	 */
	public boolean recordExists() {
		final Date now = new Date();
		final String date = Util.formatSimpleDate(now);
		final Cursor cursor = db.query(Constants.TRAFFIC_TABLE_NAME, null, TrafficDBHelper.COLUMN_DAY + "=?",
				new String[] { date }, null, null, null);
		final boolean result = cursor.moveToFirst();
		cursor.close();
		return result;
	}

	/**
	 * 新一天
	 * 
	 * @param value
	 */
	public void addNewDay(TrafficInfo value) {
		final Date now = new Date();
		final String date = Util.formatSimpleDate(now);
		final ContentValues values = new ContentValues();
		values.put(TrafficDBHelper.COLUMN_DAY, date);
		values.put(TrafficDBHelper.COLUMN_STARTTRAFFIC, 0d);
		values.put(TrafficDBHelper.COLUMN_STARTTRAFFICUPLOAD, 0d);
		values.put(TrafficDBHelper.COLUMN_STARTTRAFFICDOWNLOAD, 0d);
		values.put(TrafficDBHelper.COLUMN_STARTALLPACKAGES, 0d);
		values.put(TrafficDBHelper.COLUMN_ENDTRAFFIC, value.getEndAll());
		values.put(TrafficDBHelper.COLUMN_ENDTRAFFICUPLOAD, value.getEndUpload());
		values.put(TrafficDBHelper.COLUMN_ENDTRAFFICDOWNLOAD, value.getEndDownload());
		values.put(TrafficDBHelper.COLUMN_ENDALLPACKAGES, value.getEndPackages());
		db.insert(Constants.TRAFFIC_TABLE_NAME, null, values);
	}

	public static final String KEY_TOTAL = "total";
	public static final String KEY_UPLOAD = "upload";
	public static final String KEY_DOWNLOAD = "download";

	/**
	 * 获取一天的统计流量
	 * @return
	 */
	public HashMap<String, Double> getDayTotalTraffic() {
		final TrafficInfo onday = getTrafficRecord();
		final HashMap<String, Double> result = new HashMap<String, Double>();
		result.put(KEY_TOTAL, onday.getEndAll() - onday.getStartAll());
		result.put(KEY_UPLOAD, onday.getEndUpload() - onday.getStartUpload());
		result.put(KEY_DOWNLOAD, onday.getEndDownload() - onday.getStartDownload());
		return result;
	}

	/**
	 * 获取一个月的统计流量
	 * @return
	 */
	public HashMap<String, Double> getMonthTotalTraffic() {
		// 得到一个月的流量统计
		final List<TrafficInfo> traffics = getMonthTrafficRecord();
		final int count = traffics.size();
		Log.v(TAG, "traffics.size():" + count);
		if (count == 0) {
			return null;
		}
		
		double all = 0d;
		double up = 0d;
		double down = 0d;
		for(int i = 0; i < count; i++){
			final TrafficInfo t = traffics.get(i);
			all += t.getEndAll();
			up += t.getEndUpload();
			down += t.getEndDownload();
		}
		
		final HashMap<String, Double> result = new HashMap<String, Double>();
		result.put(KEY_TOTAL, all);
		result.put(KEY_UPLOAD, up);
		result.put(KEY_DOWNLOAD, down);
		return result;
	}
	
}
