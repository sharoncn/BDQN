package rjfsdo.sharoncn.android.CartoonReaderWeb.Data;

import java.util.ArrayList;
import java.util.List;

import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookInfo;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Constants;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Utils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BookInfoDaoImpl implements BookInfoDao {
	private static final String TAG = "BookInfoDaoIplm";
	private static SqliteHelper helper = null;
	private static SQLiteDatabase db = null;
	private static BookInfoDaoImpl me;
	
	static{
		me = new BookInfoDaoImpl();
	}
	
	private BookInfoDaoImpl(){}
	
	public static BookInfoDaoImpl getInstance(Context context){
		if(helper == null)helper = new SqliteHelper(context);
		if(db == null)db = helper.getWritableDatabase();
		return me;
	}
	
	@Override
	public void saveBookInfo(List<BookInfo> books) {
		for(BookInfo book:books){
			saveBookInfo(book);
		}
	}

	@Override
	public long saveBookInfo(BookInfo book) {
		ContentValues values = new ContentValues();
		values.put("bookId", book.getBookId());
		values.put("title", book.getBookName());
		values.put("content", book.getBookContent());
		values.put("classId", book.getClassId());
		values.put("className", book.getClassName());
		values.put("createTime", book.getCreateDate());
		values.put("bookPath", book.getZipPath());
		values.put("bookCover", book.getCoverPath());
		values.put("grade", book.getGrade());
		values.put("gradeNums", book.getGradeNums());
		values.put("unzipBookPath", Utils.getLocalPath(book.getZipPath()));
		values.put("position", 0);
		values.put("totalPage", book.getPageTotal());
		values.put("iscollection", 0);
		values.put("isread", 0);
		long returnValue = db.insertWithOnConflict(Constants.DATATABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE );
		Log.i(TAG,"�������");
		return returnValue;
	}

	@Override
	public void deleteBookInfo(String bookId) {
		db.execSQL("DELETE FROM " + Constants.DATATABLE_NAME + " WHERE bookId=" + bookId);
	}

	@Override
	public BookInfo getBookInfo(String bookId) {
		Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.DATATABLE_NAME + " WHERE bookId=?", new String[]{bookId});
		if(cursor.moveToFirst()){
			BookInfo book = cursor2BookInfo(cursor);
			cursor.close();
			return book;
		}
		cursor.close();
		return null;
	}

	//��Cursor����BookInfo����
	private BookInfo cursor2BookInfo(Cursor cursor){
		BookInfo book = new BookInfo();
		book.setId(cursor.getInt(cursor.getColumnIndex("_id")));
		book.setBookId(cursor.getString(cursor.getColumnIndex("bookId")));
		book.setBookName(cursor.getString(cursor.getColumnIndex("title")));
		book.setBookContent(cursor.getString(cursor.getColumnIndex("content")));
		book.setClassId(cursor.getString(cursor.getColumnIndex("classId")));
		book.setClassName(cursor.getString(cursor.getColumnIndex("className")));
		book.setCreateDate(cursor.getString(cursor.getColumnIndex("createTime")));
		book.setZipPath(cursor.getString(cursor.getColumnIndex("bookPath")));
		book.setCoverPath(cursor.getString(cursor.getColumnIndex("bookCover")));
		book.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
		book.setGradeNums(cursor.getString(cursor.getColumnIndex("gradeNums")));
		book.setLastReadDate(cursor.getString(cursor.getColumnIndex("lastReadDate")));
		book.setLastReadedPage(cursor.getString(cursor.getColumnIndex("position")));
		book.setPageTotal(cursor.getString(cursor.getColumnIndex("totalPage")));
		return book;
	}
	
	@Override
	public List<BookInfo> getAllBookInfo() {
		List<BookInfo> books = new ArrayList<BookInfo>();
		Cursor cursor = getAllBookInfoCursor();
		while(cursor.moveToNext()){
			books.add(cursor2BookInfo(cursor));
		}
		cursor.close();
		return books;
	}

	@Override
	public Cursor getAllBookInfoCursor() {
		Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.DATATABLE_NAME, new String[]{});
		return cursor;
	}

	@Override
	public int modifyBookInfo(BookInfo book) {
		ContentValues values = new ContentValues();
		values.put("bookId", book.getBookId());
		values.put("title", book.getBookName());
		values.put("content", book.getBookContent());
		values.put("classId", book.getClassId());
		values.put("className", book.getClassName());
		values.put("createTime", book.getCreateDate());
		values.put("bookPath", book.getZipPath());
		values.put("bookCover", book.getCoverPath());
		values.put("grade", book.getGrade());
		values.put("gradeNums", book.getGradeNums());
		values.put("position", book.getPage());
		values.put("totalPage", book.getPageTotal());
		return db.update(Constants.DATATABLE_NAME, values, "bookId=?", new String[]{book.getBookId()});
	}

	@Override
	public void close(){
		if(db != null && db.isOpen()){
			db.close();
			db = null;
		}
	}

	@Override
	public List<BookInfo> getCollection() {
		List<BookInfo> books = new ArrayList<BookInfo>();
		Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.DATATABLE_NAME + " WHERE iscollection=? ORDER BY createTime DESC", new String[]{"" + 1});
		while(cursor.moveToNext()){
			books.add(cursor2BookInfo(cursor));
		}
		cursor.close();
		return books;
	}

	@Override
	public List<BookInfo> getReadedBookInfo() {
		List<BookInfo> books = new ArrayList<BookInfo>();
		Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.DATATABLE_NAME + " WHERE isread=? ORDER BY lastReadDate DESC", new String[]{"" + 1});
		while(cursor.moveToNext()){
			books.add(cursor2BookInfo(cursor));
		}
		cursor.close();
		return books;
	}
	
	@Override
	public boolean isRecordExists(String bookId){
		Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.DATATABLE_NAME + " WHERE bookId=?", new String[]{bookId});
		Log.i(TAG, "cursor.getCount:" + cursor.getCount());
		if(cursor.getCount() == 0){
			cursor.close();
			return false;
		}else{
			cursor.close();
			return true;
		}
	}
	
	/**
	 * �����ղأ�1�����ղأ�0����ȡ���ղ�
	 * @param bookId
	 * @param isColleciton
	 */
	public boolean setCollection(String bookId,String isColleciton){
		ContentValues values = new ContentValues();
		values.put("iscollection", isColleciton);
		int i = db.update(Constants.DATATABLE_NAME, values, "bookId=?", new String[]{bookId});
		Log.i(TAG,"�����ղصķ���ֵΪ��" + i);
		if(i > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * �����Ķ���ʷ��1�����Ѷ���0����ȡ���Ѷ�
	 * @param bookId
	 * @param isReaded
	 */
	public boolean setReaded(String bookId,String isReaded){
		ContentValues values = new ContentValues();
		values.put("isread", isReaded);
		int i = db.update(Constants.DATATABLE_NAME, values, "bookId=?", new String[]{bookId});
		Log.i(TAG,"�����Ķ���ʷ�ķ���ֵΪ��" + i);
		if(i > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * �õ�bookIdƥ���book����·��
	 * @param id
	 * @return ����ɹ�����·����ʧ�ܷ���null
	 */
	public String getUnZipPath(int bookId){
		Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.DATATABLE_NAME + " WHERE bookId=?", new String[]{"" + bookId});
		String path = null;
		if(cursor.moveToFirst()){
			path = cursor.getString(cursor.getColumnIndex("unzipBookPath"));
		}
		cursor.close();
		return path;
	}

	@Override
	public boolean setLastReadTime(String bookId, String time) {
		ContentValues values = new ContentValues();
		values.put("lastReadDate", time);
		int i = db.update(Constants.DATATABLE_NAME, values, "bookId=?", new String[]{bookId});
		Log.i(TAG,"��������Ķ�ʱ��ķ���ֵΪ��" + i);
		if(i > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String getLastReadTime(String bookId) {
		Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.DATATABLE_NAME + " WHERE bookId=?", new String[]{"" + bookId});
		String time = cursor.getString(cursor.getColumnIndex("lastReadDate"));
		cursor.close();
		return time;
	}

	@Override
	public boolean setLastReadedPosition(String bookId, String position) {
		ContentValues values = new ContentValues();
		values.put("position", position);
		int i = db.update(Constants.DATATABLE_NAME, values, "bookId=?", new String[]{bookId});
		Log.i(TAG,"��������Ķ�λ�õķ���ֵΪ��" + i);
		if(i > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<BookInfo> getTypeBookInfo(String typeCode) {
		Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.DATATABLE_NAME + " WHERE classId=?", new String[]{typeCode});
		List<BookInfo> books = new ArrayList<BookInfo>();
		while(cursor.moveToNext()){
			books.add(cursor2BookInfo(cursor));
		}
		cursor.close();
		return books;
	}

	@Override
	public BookInfo getLastReadedBook() {
		Cursor cursor = db.query(Constants.DATATABLE_NAME , null, "isread=? AND lastReadDate=(SELECT MAX(lastReadDate) FROM " + Constants.DATATABLE_NAME + ")", new String[]{"" + 1}, null, null, null);
		BookInfo book = null;
		if(cursor.moveToFirst()){
			book = cursor2BookInfo(cursor);
		}
		cursor.close();
		return book;
	}
	
	@Override
	public boolean saveRatingValue(String bookId,String value){
		BookInfo book = getBookInfo(bookId);
		int gradeNums = 0;
		//��������
		String gradeNumsString = book.getGradeNums();
		if(gradeNumsString != null){
			gradeNums = Integer.parseInt(gradeNumsString);
		}
		Log.i(TAG,"" + gradeNums);
		//����
		String grade = book.getGrade();
		float gradeValue = -1;
		if(grade != null){
			gradeValue = Float.parseFloat(grade);
		}
		if(gradeNums != 0){
			value = "" + ((gradeValue * gradeNums + Float.parseFloat(value)) / (gradeNums + 1));
		}
		ContentValues values = new ContentValues();
		values.put("grade", value);
		values.put("gradeNums", "" + (gradeNums + 1));
		int i = db.update(Constants.DATATABLE_NAME, values, "bookId=?", new String[]{bookId});
		Log.i(TAG,"��������Ķ�ʱ��ķ���ֵΪ��" + i);
		if(i > 0){
			return true;
		}else{
			return false;
		}
	}
}
