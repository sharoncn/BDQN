package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
//import android.util.Log;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

/**
 * 显示程序主界面
 * 
 * @author sharoncn
 * 
 */
public class TabMainActivity extends TabActivity implements OnTabChangeListener, OnClickListener {
	public static final String FLAG_ISLOGIN = "isLogin";
	private static final int INDEX_HOME = 0;
	private static final int INDEX_RECOMMEND = 1;
	private static final int INDEX_COLLECTION = 2;
	private static final int INDEX_MORE = 3;
	private static final String TAG = "TabMainActivity";
	private TabHost tabHost;
	private TabWidget tabWidget;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_tabmain);

		//debug
		//Debug.startMethodTracing();
		
		tabHost = this.getTabHost();
		tabHost.setOnTabChangedListener(this);
		tabWidget = tabHost.getTabWidget();

		initTabs();

		Bundle data = getIntent().getExtras();
		//不论是谁调用必然会传递是否登陆，所以判断就没有必要了
		boolean isLogin = data.getBoolean(FLAG_ISLOGIN);
		if (!isLogin) {
			tabWidget.setVisibility(View.GONE);
		}
	}

	private void initTabs() {
		TabSpec home = tabHost.newTabSpec("home")
				.setIndicator(getString(R.string.home), getResources().getDrawable(R.drawable.tabmain_tab_home))
				.setContent(new Intent(this, HomeActivity.class));
		TabSpec recommend = tabHost
				.newTabSpec("recommend")
				.setIndicator(getString(R.string.recommend),
						getResources().getDrawable(R.drawable.tabmain_tab_recommend))
				.setContent(new Intent(this, RecommendActivity.class));
		TabSpec collection = tabHost
				.newTabSpec("collection")
				.setIndicator(getString(R.string.collection),
						getResources().getDrawable(R.drawable.tabmain_tab_collection))
				.setContent(new Intent(this, CollectionActivity.class));
		TabSpec more = tabHost.newTabSpec("more")
				.setIndicator(getString(R.string.more), getResources().getDrawable(R.drawable.tabmain_tab_more))
				.setContent(new Intent(this, MoreActivity.class));

		tabHost.addTab(home);
		tabHost.addTab(recommend);
		tabHost.addTab(collection);
		tabHost.addTab(more);

		tabHost.setCurrentTab(INDEX_HOME);

		for (int i = 0; i < tabWidget.getTabCount(); i++) {
			//Log.v(TAG, "index:" + tabWidget.getChildTabViewAt(i).toString());
			ViewGroup tabParent = (ViewGroup) tabWidget.getChildTabViewAt(i);
			tabParent.setBackgroundResource(android.R.color.transparent);
			tabParent.addStatesFromChildren();
			tabParent.setTag(i);
			tabParent.setOnClickListener(this);
			View icon = tabParent.getChildAt(0);
			icon.setFocusable(true);
			icon.setFocusableInTouchMode(true);
			if (i == 0) {
				icon.requestFocus();
			}
			icon.setTag(i);
			TextView tv = (TextView) tabParent.getChildAt(1);
			tv.setTextColor(Color.WHITE);
		}
	}

	@Override
	public void onClick(View v) {
		//Log.v(TAG, "onClick" + v.getTag());
		switch (Integer.parseInt(v.getTag() + "")) {
		case INDEX_HOME:
			tabHost.setCurrentTab(INDEX_HOME);
			break;
		case INDEX_RECOMMEND:
			tabHost.setCurrentTab(INDEX_RECOMMEND);
			break;
		case INDEX_COLLECTION:
			tabHost.setCurrentTab(INDEX_COLLECTION);
			break;
		case INDEX_MORE:
			tabHost.setCurrentTab(INDEX_MORE);
			break;
		}
	}

	@Override
	public void onTabChanged(String tabId) {
		int index = tabHost.getCurrentTab();
		//Log.v(TAG, "index:" + index);
		//Log.v(TAG, "index:" + tabWidget.getChildTabViewAt(index).toString());
		ViewGroup tabParent = (ViewGroup) tabWidget.getChildTabViewAt(index);
		View icon = tabParent.getChildAt(0);
		icon.requestFocus();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setMessage(R.string.exit_ask)
			.setTitle(R.string.app_name)
			.setPositiveButton(R.string.no, alertBtnClick)
			.setNegativeButton(R.string.yes, alertBtnClick)
			.show();
			return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}
	}
	
	DialogInterface.OnClickListener alertBtnClick = new DialogInterface.OnClickListener(){
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch(which){
			case DialogInterface.BUTTON_POSITIVE:
				dialog.dismiss();
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				Util.exit(TabMainActivity.this);
				break;
			}
		}
	};

	@Override
	protected void onDestroy() {
		Log.i(TAG, "onDestroy");
		//stop debug
		//Debug.stopMethodTracing();
		//如果主界面退出，将百度地图服务停止
		Util.destoryMapManger(this);
		super.onDestroy();
	}

}
