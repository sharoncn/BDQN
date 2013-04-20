package rjfsdo.sharoncn.android.BDQN.LeisureLife.Data;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Constants;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SaveDataHelper extends SQLiteOpenHelper{
	private static final int VERSION = 1;
	private static final String TAG = "SaveDataHelper";
	public SaveDataHelper(Context context) {
		super(context, Constants.DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Constants.DATATABLE_NAME + "(" +
				"Saveid INTEGER PRIMARY KEY AUTOINCREMENT," +//����
				"Content VARCHAR(20) UNIQUE," +//�ղػ������
				"Type INTEGER," +//�ղػ������
				"comment TEXT)");//�ղػ������
		Log.i(TAG,"DataBase Created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
