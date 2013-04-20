package rjfsdo.sharoncn.android.BDQN.AptechWeibo;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Constants;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.ImageCache;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.SoundManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

/**
 * Ö÷tab½çÃæ
 * @author sharoncn
 *
 */
public class MainTabAct extends TabActivity implements OnTabChangeListener {
	private static final String TAB_HOME = "home";
	private static final String TAB_ALTME = "altme";
	private static final String TAB_ABOUTME = "aboutme";
	private static final String TAB_SEARCH = "search";
	private static final String TAB_MORE = "more";
	public static final String TO = "to";
	private TabHost tabHost;
	private TabWidget tabWidget;
	private LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_maintab);

		initTabHost();
	}
	
	
	private void initTabHost() {
		tabHost = this.getTabHost();
		tabWidget = tabHost.getTabWidget();
		inflater = LayoutInflater.from(this);

		tabHost.addTab(newTabSpec(TAB_HOME, R.layout.tab_menu_item, MHomeAct.class, R.drawable.icon_1_n, R.string.home));
		tabHost.addTab(newTabSpec(TAB_ALTME, R.layout.tab_menu_item, MAltMeAct.class, R.drawable.icon_2_n, R.string.altme));
		tabHost.addTab(newTabSpec(TAB_ABOUTME, R.layout.tab_menu_item, MAboutMeAct.class, R.drawable.icon_3_n, R.string.aboutme));
		tabHost.addTab(newTabSpec(TAB_SEARCH, R.layout.tab_menu_item, MSearchAct.class, R.drawable.icon_4_n, R.string.search));
		tabHost.addTab(newTabSpec(TAB_MORE, R.layout.tab_menu_item, MMoreAct.class, R.drawable.icon_5_n, R.string.more));
		
		tabHost.setOnTabChangedListener(this);
		tabWidget.getChildTabViewAt(tabHost.getCurrentTab()).requestFocus();
	}

	private TabSpec newTabSpec(String tag, int layoutResId, Class<?> to, int icon, int txt) {
		Intent intent = new Intent(this, to);
		View view = inflater.inflate(layoutResId, null);
		view.setPadding(0, 5, 0, 5);
		view.setTag(tag);
		
		ImageView img = (ImageView) view.findViewById(R.id.img);
		img.setBackgroundResource(icon);
		TextView tv = (TextView) view.findViewById(R.id.txt);
		tv.setText(txt);
		TabSpec spec = tabHost.newTabSpec(tag).setIndicator(view).setContent(intent);
		return spec;
	}

	@Override
	public void onTabChanged(String tabId) {
		if(tabId.equals(TAB_HOME)){
			tabWidget.getChildTabViewAt(0).requestFocus();
		}else if(tabId.equals(TAB_ALTME)){
			tabWidget.getChildTabViewAt(1).requestFocus();
		}else if(tabId.equals(TAB_ABOUTME)){
			tabWidget.getChildTabViewAt(2).requestFocus();
		}else if(tabId.equals(TAB_SEARCH)){
			tabWidget.getChildTabViewAt(3).requestFocus();
		}else if(tabId.equals(TAB_MORE)){
			tabWidget.getChildTabViewAt(4).requestFocus();
//			Log.v(TAG,"TAB CHANGED:" + TAB_MORE);
//			Log.v(TAG,tabWidget.getChildTabViewAt(0).toString());
//			Log.v(TAG,tabWidget.getChildTabViewAt(0).getTag().toString());
//			Log.v(TAG,tabWidget.getChildTabViewAt(0).hasFocus() + "");
		}
		if(Constants.ISPLAYSOUND){
			SoundManager.getInstance().applyPlaySound(R.raw.sound_button, false);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			prepareExit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void prepareExit() {
		AlertDialog.Builder alert = new Builder(this);
		alert.setTitle(String.format(getString(R.string.exit), getString(R.string.app_name)))
		.setPositiveButton(R.string.cancel, listener)
		.setNegativeButton(R.string.ok, listener)
		.show();
	}
	
	private DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener(){

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch(which){
			case DialogInterface.BUTTON_POSITIVE:
				dialog.dismiss();
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				ImageCache.getInstance(MainTabAct.this).saveProjection();
				finish();
				break;
			}
		}
		
	};
}
