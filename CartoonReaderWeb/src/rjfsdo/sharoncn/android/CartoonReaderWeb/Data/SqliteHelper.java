package rjfsdo.sharoncn.android.CartoonReaderWeb.Data;

import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Constants;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * �Ҽ�����һ���ֶΣ����ڼ�¼����Ķ�ʱ��
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
//	private String bookName;//����-
//	private String bookContent;//���-
//	private String coverPath;//����·��(SDCard�е�·��)-
//	private String zipPath;//ѹ����·��(SDCard�е�·��)-
//	private String writer;//����
//	private String countryName;//����
//	private String classId;//���������-
//	private String className;//�����������-
//	private String bookSize;//������С
//	private String createDate;//��������
//	private String hitNum;//�������
//	private String grade;//�����Ǽ�-
//	private String gradeNums;//��������-
//	private String recommend;//�Ƽ����
//	private String page;//�Ķ�ҳ��
//	private String pageTotal;//��ҳ��-
//	private String lastReadDate;//����Ķ�ʱ��
//	private String lastReadedPage;//����Ķ�ҳ��-
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
				"unzipBookPath VARCHAR(100)," + //���صĴ�����ѹ����·��
				"position VARCHAR(50)," +//�Ѿ��Ķ���ҳ��
				"totalPage VARCHAR(100)," +
				"lastReadDate datetime," +
				"iscollection CHAR(1)," +//����Ƿ����ղأ�Ĭ��ֵ0����ʾδ�ղأ�1��ʾ���ղ�
				"isread CHAR(1))");//����Ƿ��Ѷ���Ĭ��ֵΪ0����ʾδ����1��ʾ�Ѷ�
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Constants.DATATABLE_FEEDBACK + "(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"feedbackTime datetime,content TEXT)");
		Log.i(TAG,"DataBase Created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
