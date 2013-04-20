package rjfsdo.sharoncn.android.BDQN.AptechWeibo;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Constants;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.SoundManager;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TabHost.TabSpec;

/**
 * 于我有关的信息
 * @author sharoncn
 *
 */
public class MAltMeAct extends TabActivity implements OnTabChangeListener {
	public static final String ALTME = "altme";
	public static final String COMMENT = "comment";
	public static final String MESSAGE = "message";
	private TabHost tabHost;
	private TabWidget tabWidget;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_maltme);

		initTabs();
	}

	private void initTabs() {
		tabHost = this.getTabHost();
		tabWidget = tabHost.getTabWidget();
		//tabWidget.setDividerDrawable(R.drawable.news_bg);

		tabHost.addTab(newTabSpec(ALTME, AltMeAct.class, R.drawable.tab_news_altme, R.string.altme));
		tabHost.addTab(newTabSpec(COMMENT, CommentListAct.class, R.drawable.tab_news_comment, R.string.comment));
		tabHost.addTab(newTabSpec(MESSAGE, MessageAct.class, R.drawable.tab_news_message, R.string.message));
		
		tabHost.setOnTabChangedListener(this);
		tabHost.setCurrentTab(0);
	}

	private TabSpec newTabSpec(String tag, Class<?> to, int icon, int txt) {
		Intent intent = new Intent(this, to);
		View view = new ImageView(this);
		view.setBackgroundResource(icon);
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(params);
		TabSpec spec = tabHost.newTabSpec(tag).setIndicator(view).setContent(intent);
		return spec;
	}

	@Override
	public void onTabChanged(String tabId) {
		if(tabId.equals(ALTME)){
			tabWidget.getChildAt(0).requestFocus();
		}else if(tabId.equals(COMMENT)){
			tabWidget.getChildAt(1).requestFocus();
		}else if(tabId.equals(MESSAGE)){
			tabWidget.getChildAt(2).requestFocus();
		}
		if(Constants.ISPLAYSOUND){
			SoundManager.getInstance().applyPlaySound(R.raw.sound_button, false);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
