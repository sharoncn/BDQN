package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.AboutActivity;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.FeedbackActivity;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.SearchActivity;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.ShelfActivity;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.TabMainActivity;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.BottomMenu;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.Header;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.MainActivity;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Data.BookInfoDao;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Data.BookInfoDaoImpl;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookInfo;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Utils;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

/**
 * 所有使用BottomMenu的Activity的基类
 * @author sharoncn
 *
 */
public class BaseActivity extends ActivityGroup{
	private static final int FIRST = 1;
	private static final String TAG = "BaseActivity";
	private static List<Activity> activities = new ArrayList<Activity>();
	private LinearLayout viewContainer;
	private Header viewHeader = null;
	private BottomMenu bottomMenu = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.layout_baseactivity);
		//获得各个View
		viewContainer = (LinearLayout) findViewById(R.id.base_container);
		viewHeader = (Header) findViewById(R.id.base_header);
		bottomMenu = (BottomMenu) findViewById(R.id.base_bottommenu);
		//为底部menu设置监听器
		bottomMenu.setUnifiedClickListener(menuLst);
	}
	
	//Header部分，设计为适应：背景颜色+标题文字，背景图片+标题文字，仅使用背景图片
	/**
	 * 设置头部背景
	 * @param resId
	 */
	public void setHeaderBackgroundResources(int resId){
		viewHeader.setBackgroundResource(resId);
	}
	/**
	 * 设置头部背景颜色
	 * @param color
	 */
	public void setHeaderBackgroundColor(int color){
		viewHeader.setBackgroundColor(color);
	}
	
	/**
	 * 设置头部标题
	 * @param title
	 */
	public void setHeaderTitle(String title){
		viewHeader.setTitle(title);
	}
	
	/**
	 * 设置头字体颜色
	 * @param color
	 */
	public void setHeaderTitleColor(int color){
		viewHeader.setTitleColor(color);
	}
	
	public void setHeaderTextSize(float size){
		viewHeader.setTextSize(size);
	}
	
	/**
	 * 设置头部高度
	 * @param height
	 */
	public void setHeaderHeight(int height){
		viewHeader.setHeight(height);
	}
	
	/**
	 * 设置标题可见性
	 * @param visibility
	 */
	public void setHeaderTitleVisibility(int visibility){
		viewHeader.setTitleVisibility(visibility);
	}
	
	/**
	 * 设置头部可见性
	 * @param visibility
	 */
	public void setHeaderVisibility(int visibility){
		viewHeader.setVisibility(visibility);
	}
	//Header部分结束
	
	//系统菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.v(TAG,"onCreateOptionsMenu");
		menu.add(FIRST, FIRST, FIRST, R.string.about).setIcon(android.R.drawable.ic_menu_info_details);
		menu.add(FIRST, FIRST + 1, FIRST, R.string.feedback).setIcon(android.R.drawable.ic_menu_send);
		menu.add(FIRST, FIRST + 2, FIRST, R.string.exit).setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int which = item.getItemId();
		switch(which){
		case FIRST:
			goAbout();
			break;
		case FIRST + 1:
			goFeedback();
			break;
		case FIRST + 2:
			prepareExit(this);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	//系统菜单结束

	//BottomMenu监听器
	private OnClickListener menuLst = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String tag = (String) v.getTag();
			if(tag.equals(BottomMenu.TAG_HOME)){//首页被点击
				goHome();
				
			}else if(tag.equals(BottomMenu.TAG_BROWSER)){//浏览被点击
				goBrowser();
				
			}else if(tag.equals(BottomMenu.TAG_MORE)){//更多被点击
				AlertDialog.Builder moreDlg = new AlertDialog.Builder(BaseActivity.this);
				moreDlg.setItems(R.array.more_dialog, itemClick)
				.setCancelable(false)
				.setPositiveButton(R.string.cancel, cancelClick)
				.show();
				
			}else if(tag.equals(BottomMenu.TAG_SEARCH)){//搜索被点击
				goSearch();
				
			}else if(tag.equals(BottomMenu.TAG_SHELF)){//书架被点击
				goShelf();
			}
		}
	};
	
	//Activity跳转
	private void goHome(){
		Log.i(TAG,"TAG:" + getTag());
		Intent intent = new Intent(this,TabMainActivity.class);
		startActivity(intent);
		if(!getTag().equalsIgnoreCase(TabMainActivity.TAG)){
			this.finish();
		}else{
			TabMainActivity tabmain = (TabMainActivity) this;
			tabmain.getCurrentTabHost().setCurrentTab(0);
		}
	}
	
	private void goBrowser(){
		BookInfoDao dao = BookInfoDaoImpl.getInstance(this);
		BookInfo book =dao.getLastReadedBook();
		if(book != null){
			String zipPath = BookInfoDaoImpl.getInstance(this).getUnZipPath(Integer.parseInt(book.getBookId()));
			String imagePath = zipPath.substring(0, zipPath.lastIndexOf(".") - 1);
			String lastReadPage = book.getLastReadedPage();
			Log.i(TAG, "最后阅读页数：" + lastReadPage);
			int position = 0;
			if(lastReadPage != null){
				position = Integer.parseInt(lastReadPage);
			}
			String picPath = Utils.getImagePath(imagePath,position);
			Log.i(TAG, "阅读图片路径：" + picPath);
			String bookId = book.getBookId();
			Intent intent = new Intent(this,MainActivity.class);
			intent.putExtra("bookId", bookId);
			intent.putExtra("picPath", picPath);
			startActivity(intent);
		}else{
			Utils.showMsg(this, getString(R.string.read_nomsg));
		}
	}
	
	private void goSearch(){
		Intent intent = new Intent(this,SearchActivity.class);
		startActivity(intent);
		if(!getTag().equalsIgnoreCase(SearchActivity.TAG)){
			this.finish();
		}
		//this.finish();
	}
	
	private void goShelf(){
		Intent intent = new Intent(this,ShelfActivity.class);
		startActivity(intent);
		if(!getTag().equalsIgnoreCase(ShelfActivity.TAG)){
			this.finish();
		}
		//this.finish();
	}
	
	private void goAbout(){
		Intent intent = new Intent(this,AboutActivity.class);
		startActivity(intent);
		if(!getTag().equalsIgnoreCase(AboutActivity.TAG)){
			this.finish();
		}
		//this.finish();
	}
	
	private void goFeedback(){
		Intent feedbackIntent = new Intent(this,FeedbackActivity.class);
		startActivity(feedbackIntent);
		if(!getTag().equalsIgnoreCase(FeedbackActivity.TAG)){
			this.finish();
		}
		//this.finish();
	}
	//Activity跳转
	
	//更多按钮的Dialog的Item监听器
	private DialogInterface.OnClickListener itemClick = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch(which){
			case 0://关于被点击
				goAbout();
				break;
			case 1://退出被点击
				prepareExit(BaseActivity.this);
				break;
			case 2://反馈被点击
				goFeedback();
				break;
			}
		}
	};
	
	//更多按钮的Dialog取消键监听器
	private DialogInterface.OnClickListener cancelClick = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};
	
	//退出确认dialog的确定按钮监听器
	private DialogInterface.OnClickListener okLst = new DialogInterface.OnClickListener(){
		@Override
		public void onClick(DialogInterface dialog, int which) {
			exit();
		}
	};
	
	//退出确认dialog的取消按钮监听器
	private DialogInterface.OnClickListener cancelLst = new DialogInterface.OnClickListener(){
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};
	
	//准备退出
	protected void prepareExit(Context context){
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setMessage(R.string.logout_msg_body)
		.setTitle(R.string.logout_title)
		.setCancelable(false)
		.setIcon(R.drawable.menu_more)
		.setPositiveButton(R.string.logout_submit, okLst)
		.setNegativeButton(R.string.logout_cancel, cancelLst)
		.show();
	}
	
	//退出
	protected void exit(){
		for(Activity activity:activities){
			if(activity != null){
				activity.finish();
			}
		}
		BookInfoDaoImpl.getInstance(this).close();
		this.finish();
	}

	/**
	 * 加入activity列表，退出时会循环关闭列表中所有Activity
	 * @param act
	 */
	protected void add(Activity act){
		String actTag = ((BaseActivity)act).getTag();
		Iterator<Activity> it = activities.iterator();
		while(it.hasNext()){
			BaseActivity activity = (BaseActivity) it.next();
			String listActTag = activity.getTag();
			Log.i(TAG, "BaseActivity中的列表:" + listActTag);
			if(actTag.equals(listActTag)){
				it.remove();
			}
		}
		activities.add(act);
	}
		
	/**
	 * 将传入的activity移出列表
	 * @param act
	 * @return
	 */
	protected boolean remove(Activity act){
		return activities.remove(act);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.v(TAG,"KEYCODE:" + keyCode);
		if(keyCode == KeyEvent.KEYCODE_BACK){ 
			prepareExit(this);
			return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public void setContentView(int layoutResID) {
		LinearLayout layout = (LinearLayout)LayoutInflater.from(this).inflate(layoutResID, null);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		viewContainer.addView(layout);
	}

	public String getTag(){
		return TAG;
	}
}
