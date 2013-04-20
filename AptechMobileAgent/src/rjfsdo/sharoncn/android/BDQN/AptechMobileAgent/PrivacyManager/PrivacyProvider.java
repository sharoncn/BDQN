package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.PrivacyManager;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Db.AppPrivacyDBHelper;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Constants;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class PrivacyProvider extends ContentProvider {
	private static final String TAG = "PrivacyProvider";
	private static final int TABLE_CODE = 0x01;
	private static final int ITEM_CODE = 0x02;
	private static final UriMatcher mMatcher;
	public static final String AUTHORITY = "rjfsdo.sharoncn.PrivacyProvider";
	public static final String TABLE = Constants.PRIVACY_TABLE_NAME;
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + TABLE;
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + TABLE;

	private static String _ID = "_id";

	private static AppPrivacyDBHelper mHelper;
	private SQLiteDatabase mDb;

	static {
		mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mMatcher.addURI(AUTHORITY, TABLE, TABLE_CODE);
		mMatcher.addURI(AUTHORITY, TABLE + "/#", ITEM_CODE);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Log.i(TAG, "delete");
		int count = 0;
		switch (mMatcher.match(uri)) {
		case TABLE_CODE:
			count = mDb.delete(TABLE, selection, selectionArgs);
			break;
		case ITEM_CODE:
			String id = uri.getPathSegments().get(1);
			count = mDb.delete(TABLE, _ID + "=" + id + (!TextUtils.isEmpty(selection) ? "AND " + selection : ""),
					selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI:" + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (mMatcher.match(uri)) {
		case TABLE_CODE:
			return CONTENT_TYPE;
		case ITEM_CODE:
			return CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI:" + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.i(TAG, "insert");
		if (mMatcher.match(uri) == ITEM_CODE) {
			throw new IllegalArgumentException("Uri can not match," + uri);
		}
		final long insetID = mDb.insert(TABLE, "", values);
		final Uri returnUri = ContentUris.withAppendedId(uri, insetID);
		getContext().getContentResolver().notifyChange(returnUri, null);
		return returnUri;
	}

	@Override
	public boolean onCreate() {
		Log.i(TAG, "onCreate");
		if (mHelper == null)
			mHelper = new AppPrivacyDBHelper(getContext());
		if (mDb == null)
			mDb = mHelper.getWritableDatabase();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.i(TAG, "query");
		switch (mMatcher.match(uri)) {
		case TABLE_CODE:
			return mDb.query(TABLE, projection, selection, selectionArgs, null, null, sortOrder);
		case ITEM_CODE:
			final StringBuffer sb = new StringBuffer();
			long id = ContentUris.parseId(uri);
			sb.append(_ID + id);
			if (selection != null) {
				sb.append(" AND" + selection);
			}
			return mDb.query(TABLE, projection, sb.toString(), selectionArgs, null, null, sortOrder);
		default:
			throw new IllegalArgumentException("Unknown URI:" + uri);
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		Log.i(TAG, "update");
		int rowid = 0;
		switch (mMatcher.match(uri)) {
		case TABLE_CODE:
			rowid = mDb.update(TABLE, values, selection, selectionArgs);
			break;
		case ITEM_CODE:
			final StringBuffer sb = new StringBuffer();
			long id = ContentUris.parseId(uri);
			sb.append(_ID + id);
			if (selection != null) {
				sb.append(" AND" + selection);
			}
			rowid = mDb.update(TABLE, values, sb.toString(), selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI:" + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowid;
	}

	public void finish() {
		if (mDb != null) {
			mDb.close();
		}
	}
}
