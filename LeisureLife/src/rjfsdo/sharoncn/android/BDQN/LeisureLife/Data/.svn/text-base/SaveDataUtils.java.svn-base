package rjfsdo.sharoncn.android.BDQN.LeisureLife.Data;

import java.util.ArrayList;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Constants;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SaveDataUtils {
	private static SaveDataHelper helper;
	private static SQLiteDatabase db;
	private static SaveDataUtils me;
	
	static{
		me = new SaveDataUtils();
	}
	
	private SaveDataUtils(){}
	
	public static SaveDataUtils getInstance(Context context){
		if(helper == null){
			helper = new SaveDataHelper(context);
		}
		if(db == null){
			db = helper.getWritableDatabase();
		}
		return me;
	}
	
	public boolean saveCollection(Collection c){
		ContentValues values = new ContentValues();
		values.put("Content", c.getContent());
		values.put("Type", c.getType());
		values.put("comment", c.getComment());
		long count = -1;
		try{
			count = db.insert(Constants.DATATABLE_NAME, "", values);
		}catch(Exception e){}
		if(count == -1){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean deleteCollection(Collection c){
		long count = -1;
		count = db.delete(Constants.DATATABLE_NAME, "Content=?", 
				new String[]{c.getContent()});
		if(count == -1){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean clearCollection(){
		long count = -1;
		count = db.delete(Constants.DATATABLE_NAME, null, null);
		if(count == -1){
			return false;
		}else{
			return true;
		}
	}
	
	public List<Collection> getCollection(){
		List<Collection> conllections = new ArrayList<Collection>();
		final Cursor cursor = db.query(Constants.DATATABLE_NAME, null, null, null, null, null, null);
		while(cursor.moveToNext()){
			Collection collection = new Collection(cursor.getString(cursor.getColumnIndex("Type")), 
					cursor.getString(cursor.getColumnIndex("Content")), 
					cursor.getString(cursor.getColumnIndex("comment")));
			conllections.add(collection);
		}
		cursor.close();
		return conllections;
	}
}
