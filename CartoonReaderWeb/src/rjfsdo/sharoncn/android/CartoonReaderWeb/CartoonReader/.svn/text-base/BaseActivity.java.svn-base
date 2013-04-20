package rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Utils.Constants;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Utils.Utils;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Data.BookInfoDao;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class BaseActivity extends Activity {
	private static final String TAG = "CartoonReaderBaseActivity";
	protected static Map<String,String> imagePosition = new HashMap<String,String>();
	protected static LinkedList<String> imageList = new LinkedList<String>();
	protected static ArrayList<Activity> allActivity = new ArrayList<Activity>();
	protected static String bookId = null;//根据bookId是不是空判断是SDcard传递过来的picPath还是网络版动漫阅读器传递过来的picPath
	protected static BookInfoDao dao;
	
	public static Activity getActivityByName(String name){
		return null;
	}
	
	/**
	 * 退出
	 * @param con
	 */
	public static void exitApp(Context con){
		//循环关闭Activity
		Iterator<Activity> it = allActivity.iterator();
		while(it.hasNext()){
			Activity act = it.next();
			if(act != null){
				act.finish();
			}
		}
		saveReaderState();
	}
	
	/**
	 * 保存当前阅读的漫画信息
	 */
	public static void saveReaderState(){
		saveReadedInfo();
		try{
			String picPath = Utils.getImagePath(imagePosition, imageList);
			if(picPath != null){
				//false 表示不再源文件中追加，覆盖文件中的内容
				Utils.saveFile(Constants.HISTORY, picPath, false);
			}
		}catch(Exception e){
			Log.i(TAG,e.getMessage());
		}
	}
	
	//保存阅读记录到数据库
	private static void saveReadedInfo(){
		if(bookId != null && dao != null){
			Log.i(TAG,"保存最后阅读位置:" + imagePosition.get("positionId"));
			dao.setLastReadedPosition(bookId, imagePosition.get("positionId"));
		}
	}
	
	/**
	 * 自定义退出对话框
	 * @param context
	 */
	public static void promptExit(final Context context){
		exitApp(context);
//		//自定义布局
//		View view = LayoutInflater.from(context).inflate(R.layout.layout_logout_cartoonreader, null);
//		//创建提示对话框
//		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//		dialog.setTitle(R.string.logout_title);
//		dialog.setView(view);
//		//确定按钮
//		dialog.setPositiveButton(R.string.logout_submit, new DialogInterface.OnClickListener(){
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				//弹出退出对话框并退出阅读器
//				exitApp(context);
//			}
//		});
//		
//		//取消按钮
//		dialog.setNegativeButton(R.string.logout_cancel, new DialogInterface.OnClickListener(){
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				//关闭提示对话框
//				dialog.dismiss();
//			}
//		});
//		dialog.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	//按back键提示是否退出
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(KeyEvent.KEYCODE_BACK == keyCode && event.getRepeatCount() == 0){
			promptExit(this);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}