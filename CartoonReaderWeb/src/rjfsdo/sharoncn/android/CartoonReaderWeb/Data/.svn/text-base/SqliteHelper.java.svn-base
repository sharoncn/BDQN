package rjfsdo.sharoncn.android.CartoonReaderWeb.Data;

import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Constants;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 我加入了一个字段，用于记录最近阅读时间
 * @author sharoncn
 *
 */
public class SqliteHelper extends SQLiteOpenHelper {
	private static final String TAG = "SqliteHelper";
	private static final int VERSION = 1;

	public SqliteHelper(Context context) {
		super(context, Constants.DATABASE_NAME, null, VERSION);
	}
	
//	private String bookId;//id-
//	private String bookName;//书名-
//	private String bookContent;//简介-
//	private String coverPath;//封面路径(SDCard中的路径)-
//	private String zipPath;//压缩包路径(SDCard中的路径)-
//	private String writer;//作者
//	private String countryName;//国家
//	private String classId;//所属类别编号-
//	private String className;//所属类编名称-
//	private String bookSize;//漫画大小
//	private String createDate;//创建日期
//	private String hitNum;//点击数量
//	private String grade;//评价星级-
//	private String gradeNums;//评价数量-
//	private String recommend;//推荐标记
//	private String page;//阅读页数
//	private String pageTotal;//总页数-
//	private String lastReadDate;//最后阅读时间
//	private String lastReadedPage;//最后阅读页数-
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Constants.DATATABLE_NAME + "(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"bookId VARCHAR(50) UNIQUE," +
				"title VARCHAR(50)," +
				"content VARCHAR(100)," +
				"classId VARCHAR(50)," +
				"className VARCHAR(50)," +
				"createTime datetime," +
				"bookPath VARCHAR(50)," +
				"bookCover VARCHAR(50)," +
				"grade VARCHAR(100)," +
				"gradeNums VARCHAR(100)," +
				"unzipBookPath VARCHAR(100)," + //下载的此漫画压缩包路径
				"position VARCHAR(50)," +//已经阅读的页数
				"totalPage VARCHAR(100)," +
				"lastReadDate datetime," +
				"iscollection CHAR(1)," +//标记是否已收藏，默认值0，表示未收藏，1表示已收藏
				"isread CHAR(1))");//标记是否已读，默认值为0，表示未读，1表示已读
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Constants.DATATABLE_FEEDBACK + "(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"feedbackTime datetime,content TEXT)");
		Log.i(TAG,"DataBase Created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
