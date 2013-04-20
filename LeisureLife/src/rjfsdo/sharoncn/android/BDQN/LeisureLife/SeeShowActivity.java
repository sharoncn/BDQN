package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.LinearLayout.LayoutParams;

/**
 * 看演出界面
 * @author sharoncn
 *
 */
public class SeeShowActivity extends TabActivity {
	private static final String TAG = "SeeShowActivity";
	private TabHost host;
	private TabWidget widget;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.layout_act_seeshow);
		host = getTabHost();
        widget = host.getTabWidget();
        
        LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.weight = 1;
        
        LayoutInflater inflater = LayoutInflater.from(this);
        //演唱会
        View view = inflater.inflate(R.layout.tab_item_concert, null);
        view.setLayoutParams(params);
        host.addTab(host.newTabSpec("concert").setIndicator(view).setContent(new Intent(this,ConcertActivity.class)));
        //音乐会
        view = inflater.inflate(R.layout.tab_item_music, null);
        view.setLayoutParams(params);
        host.addTab(host.newTabSpec("music").setIndicator(view).setContent(new Intent(this,MusicActivity.class)));
        //话剧
        view = inflater.inflate(R.layout.tab_item_play, null);
        view.setLayoutParams(params);
        host.addTab(host.newTabSpec("play").setIndicator(view).setContent(new Intent(this,PlayActivity.class)));
        //戏曲
        view = inflater.inflate(R.layout.tab_item_pko, null);
        view.setLayoutParams(params);
        host.addTab(host.newTabSpec("pko").setIndicator(view).setContent(new Intent(this,PkoActivity.class)));
        
        //初始化Tab标签
        initTabs();
	}

	private void initTabs() {
		int count = widget.getTabCount();
        for(int i = 0; i < count; i++){
        	View v = widget.getChildTabViewAt(i);
        	Log.i(TAG,"widget.getChildTabViewAt():" + v.toString());
        	Log.i(TAG,"parent:" + v.getParent().toString());
        	LayoutParams p = (LayoutParams) v.getLayoutParams();
        	p.gravity = Gravity.CENTER;
        	p.weight = 1;
        	v.setLayoutParams(p);
        }
	}
}
