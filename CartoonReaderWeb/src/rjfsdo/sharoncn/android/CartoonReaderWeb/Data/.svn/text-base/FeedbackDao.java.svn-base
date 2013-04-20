package rjfsdo.sharoncn.android.CartoonReaderWeb.Data;

import java.util.Date;

import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Constants;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Utils;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class FeedbackDao {
	private static SQLiteDatabase db = null;
	private static SqliteHelper helper = null;
	private static final FeedbackDao me = new FeedbackDao();

	private FeedbackDao(){}
	
	public static FeedbackDao getInstance(Context context){
		if(helper == null) helper = new SqliteHelper(context);
		if(db == null) db = helper.getWritableDatabase();
		return me;
	}
	
	/**
	 * 将反馈写入数据库
	 * @param content String 反馈内容
	 * @return 成功返回rowid，失败返回-1
	 */
	public long feedBack(String content){
		ContentValues values = new ContentValues();
		values.put("feedbackTime", Utils.formatDate(new Date()));
		values.put("content", content);
		return db.insert(Constants.DATATABLE_FEEDBACK, null, values);
	}
}
