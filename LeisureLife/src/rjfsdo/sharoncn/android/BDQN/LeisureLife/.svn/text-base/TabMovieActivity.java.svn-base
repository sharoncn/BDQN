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
 * 电影列表界面
 * @author sharoncn
 *
 */
public class TabMovieActivity extends TabActivity {
	private static final String TAG = "TabMovieActivity";
	private TabHost host;
	private TabWidget widget;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.layout_act_tabmovie);
		host = getTabHost();
        widget = host.getTabWidget();
        
        LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.weight = 1;
        
        LayoutInflater inflater = LayoutInflater.from(this);
        //正在上映
        View view = inflater.inflate(R.layout.tab_item_movie, null);
        view.setLayoutParams(params);
        host.addTab(host.newTabSpec("movie").setIndicator(view).setContent(new Intent(this,MovieActivity.class)));
        //即将上映
        view = inflater.inflate(R.layout.tab_item_moviewill, null);
        view.setLayoutParams(params);
        host.addTab(host.newTabSpec("moviewill").setIndicator(view).setContent(new Intent(this,WillMovieActivity.class)));
        
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
