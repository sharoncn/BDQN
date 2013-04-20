package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Db.AppPrivacyDBHelper;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Service.PrivacyService;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Service.TrafficService;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.TrafficManager.TrafficModel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class LoadingActivity extends Activity {
	private ImageView left, right;
	private static final int FLAG_LEFT = 0;
	private static final int FLAG_RIGHT = 1;

	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_loading);
		initViews();// 初始化view
		initDataBases();// 初始化数据库
		startAllServices();// 启动需要启动的Service
	}

	// 在Loading时将所有Service启动起来
	private void startAllServices() {
		// 启动PrivacyService
		final Intent intent = new Intent(this, PrivacyService.class);
		startService(intent);
		
		// 启动TrafficService
		Intent trafficIntent = new Intent(this, TrafficService.class);
		startService(trafficIntent);
	}

	private void initDataBases() {
		//初始化Privacy数据库
		AppPrivacyDBHelper helper = new AppPrivacyDBHelper(this);
		helper.getReadableDatabase().close();
		TrafficModel.getInstance(this);
		//初始化Traffic数据库
		TrafficModel.getInstance(this);
	}

	private void initViews() {
		left = (ImageView) findViewById(R.id.loading_left);
		left.setTag(FLAG_LEFT);
		right = (ImageView) findViewById(R.id.loading_right);
		right.setTag(FLAG_RIGHT);

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				startAnimation(left);
				startAnimation(right);
			}
		}, 1000);
	}

	private void startAnimation(ImageView view) {
		int startX = 0, endX = 0;
		final int flag = (Integer) view.getTag();
		System.out.println("flag:" + flag);
		switch (flag) {
		case FLAG_LEFT:
			startX = 0;
			endX = -view.getWidth();
			break;
		case FLAG_RIGHT:
			startX = 0;
			endX = view.getWidth();
			break;
		}
		System.out.println("x-start:" + startX + "   x-end:" + endX);
		TranslateAnimation anim = new TranslateAnimation(startX, endX, 0, 0);
		anim.setDuration(2000);
		anim.setFillAfter(true);
		anim.setAnimationListener(listener);
		view.startAnimation(anim);
	}

	private AnimationListener listener = new AnimationListener() {
		private int flag = 0;

		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			flag++;
			if (flag >= 2) {
				gotoMain();
			}
		}
	};

	protected void gotoMain() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}
}
