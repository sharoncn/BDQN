package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Core.BaseActivity;

public class ShelfActivity extends BaseActivity {
	public static final String TAG = "ShelfActivity";
	private TabHost tabHost;
	//private TabWidget tabWidget;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.layout_shelf);
		add(this);
		
		this.setHeaderVisibility(View.GONE);
		
		tabHost = (TabHost) findViewById(R.id.shelf_host);
		LocalActivityManager mgr = getLocalActivityManager();
		tabHost.setup(mgr);
		tabHost.setOnTabChangedListener(tabChangeLst);
		//tabWidget = tabHost.getTabWidget();
		
		TabSpec collectionSpace =  tabHost.newTabSpec(getString(R.string.menu_mycollection));
		collectionSpace.setIndicator(getString(R.string.menu_mycollection),this.getResources().getDrawable(R.drawable.collection));
		collectionSpace.setContent(new Intent(this,CollectionActivity.class));
		
		TabSpec readedSpace =  tabHost.newTabSpec(getString(R.string.menu_recentlyread));
		readedSpace.setIndicator(getString(R.string.menu_recentlyread),this.getResources().getDrawable(R.drawable.read));
		readedSpace.setContent(new Intent(this,HistoryReadedActivity.class));
		
		tabHost.addTab(collectionSpace);
		tabHost.addTab(readedSpace);
	}
	
	private OnTabChangeListener tabChangeLst = new OnTabChangeListener(){
		@Override
		public void onTabChanged(String tabId) {
			
		}
	};
	
	@Override
	public String getTag() {
		return TAG;
	}
}
