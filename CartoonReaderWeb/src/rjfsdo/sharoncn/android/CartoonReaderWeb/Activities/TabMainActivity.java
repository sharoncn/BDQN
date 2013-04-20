package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Core.BaseActivity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;

public class TabMainActivity extends BaseActivity {
	public static final String TAG = "TabMainActivity";
	private TabHost tabHost;
	private TabWidget tabWidget;
	private GestureDetector gestureScanner;
	private static final int DISTANCE = 150;
	public enum Direction{
		Left,Right
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG,"TabMainActivity OnCreate");
		this.setContentView(R.layout.layout_tabmain);
		add(this);
		
		tabHost = (TabHost) findViewById(R.id.tabmain_host);
		LocalActivityManager mgr = getLocalActivityManager();
		tabHost.setup(mgr);
		tabHost.setOnTabChangedListener(tabChangelst);
		tabWidget = tabHost.getTabWidget();
		
		TabSpec homeSpace =  tabHost.newTabSpec("home");
		homeSpace.setIndicator("");
		homeSpace.setContent(new Intent(this,HomeActivity.class));
		
		TabSpec recommendSpace =  tabHost.newTabSpec("recommend");
		recommendSpace.setIndicator("");
		recommendSpace.setContent(new Intent(this,RecommendActivity.class));
		
		TabSpec orderSpace =  tabHost.newTabSpec("order");
		orderSpace.setIndicator("");
		orderSpace.setContent(new Intent(this,OrderActivity.class));
		
		TabSpec typeSpace = tabHost.newTabSpec("type");
		typeSpace.setIndicator("");
		typeSpace.setContent(new Intent(this,BookTypeActivity.class));
		
		tabHost.addTab(homeSpace);
		tabHost.addTab(recommendSpace);
		tabHost.addTab(orderSpace);
		tabHost.addTab(typeSpace);
		
		setTabHeight(60);
		tabHost.setCurrentTab(0);
		
		gestureScanner = new GestureDetector(gesListener){
			@Override
			public boolean onTouchEvent(MotionEvent ev) {
				return super.onTouchEvent(ev);
			}
		};
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		//��OnGestureListenerȷ�������ƶ�������true,�������ƶ������ᴥ������View��up�¼�������������ƶ���
		boolean flag = gestureScanner.onTouchEvent(ev);
		//Log.v(TAG, "gestureScanner.onTouchEvent(ev):" + flag);
		if(flag){
			return flag;
		}else{
			return super.dispatchTouchEvent(ev);
		}
	}


	//��������
	private OnGestureListener gesListener = new OnGestureListener() {
		@Override
		public boolean onSingleTapUp(MotionEvent e) {return false;}
		
		@Override
		public void onShowPress(MotionEvent e) {}
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {return false;}
		
		@Override
		public void onLongPress(MotionEvent e) {}
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			//Log.v(TAG,"onFlingʱ��������{" + e1.getX() + ":" + e2.getX() + ":" + velocityX + "}");
			if(e1 != null && e2 != null){
				if(e1.getX() - e2.getX() > DISTANCE  && Math.abs(velocityX) > Math.abs(velocityY)){
					doOnFling(Direction.Left);//left
					return true;
				}
				if(e1.getX() - e2.getX() < -DISTANCE  && Math.abs(velocityX) > Math.abs(velocityY)){
					doOnFling(Direction.Right);//right
					return true;
				}
			}
			return false;
		}
		
		private void doOnFling(Direction direction) {
			//Log.i(TAG,"��ǰTab����:" + tabHost.getCurrentTab());
			int index = tabHost.getCurrentTab();
			switch(direction){
			case Left:
				//Log.i(TAG,"�����ƶ�����:left");//��ζ��TabHost��tab����Ӧ�õ���
				if(index  < tabWidget.getTabCount() -1){
					tabHost.setCurrentTab(index + 1);
				}
				break;
			case Right:
				//Log.i(TAG,"�����ƶ�����:right");//��ζ��TabHost��tab����Ӧ�õݼ�
				if(index > 0){
					tabHost.setCurrentTab(index - 1);
				}
				break;
			}
		}

		@Override
		public boolean onDown(MotionEvent e) {return false;}
	};
	
	public TabHost getCurrentTabHost(){
		return tabHost;
	}

	//selector��Ч��ԭ������������Գɹ�����ͼƬ�ڽ���״̬�ı�ʱ��û�б䣬����ѽֻ��ʹ����������ֶ�����ͼƬ
	//���������з���TabHost��selectorʹ��Checked
	//20130120,selector��Ч��ԭ���Ѿ��ҵ�����Ϊ��������״̬�µ�drawable����ȥ�����߼���״̬Ϊfalse�Ϳ�����
	private void setTabHeight(int height) {
		for(int i = 0; i < tabWidget.getTabCount(); i++){
			Log.v(TAG,"childCount:" + tabWidget.getTabCount());
			View view = tabWidget.getChildTabViewAt(i);
			Log.v(TAG,"id:" + view.getId() + "  tag:" + view.getTag());
			view.getLayoutParams().height = height;
			((RelativeLayout)view).setAddStatesFromChildren(true);
			view.setFocusable(true);
			view.setFocusableInTouchMode(true);
			view.setClickable(true);
			view.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
			
			switch(i){
			case 0:
				view.setBackgroundResource(R.drawable.tab_home_button_type);
				view.setTag("home");
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						//Log.i(TAG,"tabWidget.getChildAt(0).getClass().toString():" + tabWidget.getChildAt(0).getRootView().getClass().toString());
						LocalActivityManager mgr = getLocalActivityManager();
						//Log.i(TAG,"mgr.getCurrentActivity().getClass().toString():" + mgr.getCurrentActivity().getClass().toString());
						HomeActivity home = (HomeActivity) mgr.getCurrentActivity();
						home.onResume();
					}
				});
				break;
			case 1:
				view.setBackgroundResource(R.drawable.tab_recommend_button_type);
				view.setTag("recommend");
				break;
			case 2:
				view.setBackgroundResource(R.drawable.tab_order_button_type);
				view.setTag("order");
				break;
			case 3:
				view.setBackgroundResource(R.drawable.tab_type_button_type);
				view.setTag("classification");
				break;
			}
		}
	}

	private OnTabChangeListener tabChangelst = new OnTabChangeListener() {
		@Override
		public void onTabChanged(String tabId) {
//			Log.v(TAG,"tabId:" + tabId);
//			for(int i = 0; i < tabWidget.getTabCount(); i++){
//				View view = tabWidget.getChildTabViewAt(i);
//				Log.v(TAG,"view.isFocused():" + view.isFocused());
//				String tag = (String) view.getTag();
//				if(tag == null){
//					tag = "home";
//				}
//				
//				if(tag.equals("home")){
//					//Log.i(TAG,"tabWidget.getChildAt(0).getClass().toString():" + tabWidget.getChildAt(0).getRootView().getClass().toString());
//					LocalActivityManager mgr = getLocalActivityManager();
//					Log.i(TAG,"mgr.getCurrentActivity().getClass().toString():" + mgr.getCurrentActivity().getClass().toString());
//					HomeActivity home = (HomeActivity) mgr.getCurrentActivity();
//					home.onResume();
//				}
//			}
		}
	};
	
	@Override
	public String getTag() {
		return TAG;
	}

	@Override
	protected void onDestroy() {
		Log.v(TAG,"TabMainActivity onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onRestart() {
		Log.v(TAG,"TabMainActivity onRestart");
		super.onRestart();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.v(TAG,"TabMainActivity onRestoreInstanceState");
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onStart() {
		Log.v(TAG,"TabMainActivity onStart");
		super.onStart();
	}
	
}