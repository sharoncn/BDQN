package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Service;

import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Db.PrivacyModel;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.PrivacyManager.MyBinder;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.PrivacyManager.PrivacyObserver;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.PrivacyManager.PrivacyPWDActivity;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.PrivacyManager.PrivacyProvider;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Receivers.LockScreenReceiver;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class PrivacyService extends Service {
	private static final String TAG = "PrivacyService";
	private static MyBinder binder;
	private static Context mContext;
	private static ActivityManager mActManager;
	private static ArrayList<String> watchList = new ArrayList<String>();
	private static ArrayList<String> unlockList = new ArrayList<String>();
	private static LockScreenReceiver receiver;
	private static PrivacyObserver observer;
	private boolean flag = true;

	@Override
	public void onCreate() {
		Log.v(TAG, "onCreate");
		if (binder == null)
			binder = new MyBinder(this);
		if (mContext == null)
			mContext = this;
		if (mActManager == null)
			mActManager = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
		if (observer == null)
			observer = new PrivacyObserver(new Handler()).setService(binder);
		if (receiver == null)
			receiver = new LockScreenReceiver(binder);

		getContentResolver().registerContentObserver(Uri.parse("Content://" + PrivacyProvider.AUTHORITY), true,
				observer);
		registerScreenEvent();
		initData();// 从数据库得到所有被保护的程序
		new WatcherThread().start();
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.v(TAG, "PrivacyService onBind");
		return binder;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.v(TAG, "PrivacyService onStart");
		super.onStart(intent, startId);
	}

	/**
	 * 注册屏幕开启和屏幕关闭的广播
	 */
	private void registerScreenEvent() {
		final IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		mContext.registerReceiver(receiver, filter);
	}

	class WatcherThread extends Thread {
		@Override
		public void run() {
			RunningTaskInfo cureentTask;
			String currentPkgName;

			int count = 0;
			while (flag) {
				try {
					// 监视应用程序运行
					cureentTask = mActManager.getRunningTasks(1).get(0);
					currentPkgName = cureentTask.baseActivity.getPackageName();
					if (count >= 100) {
						Log.v(TAG, "任务栈顶:" + currentPkgName);
						count = 0;
					}
					if (watchList.contains(currentPkgName)) {
						if (!unlockList.contains(currentPkgName)) {
							// 转到输入密码界面
							final Intent intent = new Intent(mContext, PrivacyPWDActivity.class);
							intent.putExtra(PrivacyPWDActivity.FLAG_PKGNAME, currentPkgName);
							Log.i(TAG, "currentPkgName:" + currentPkgName);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);
						}
						Thread.sleep(500);
						continue;
					} else {
						Thread.sleep(500);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				count++;
			}
		}
	}

	private void initData() {
		// 初始化数据
		synchronized (watchList) {
			watchList = PrivacyModel.getInstance(mContext, getContentResolver()).findAll();
		}
	}

	@Override
	public void onDestroy() {
		if (receiver != null) {
			mContext.unregisterReceiver(receiver);
		}
		if (observer != null)
			getContentResolver().unregisterContentObserver(observer);
		flag = false;
		super.onDestroy();
	}

	/**
	 * 删除解锁记录
	 * 
	 * @param pkgName
	 */
	public void removeUnlock(String pkgName) {
		synchronized (unlockList) {
			unlockList.remove(pkgName);
		}
	}

	/**
	 * 添加一条解锁记录
	 * 
	 * @param pkgName
	 */
	public void addUnlock(String pkgName) {
		synchronized (unlockList) {
			unlockList.add(pkgName);
		}
	}

	/**
	 * 清空保护列表
	 */
	public void clearUnlock() {
		synchronized (unlockList) {
			unlockList.clear();
		}
	}

	/**
	 * 初始化保护列表
	 */
	public void initWatchData() {
		// 当保护数据发生变化，调用initWatchData刷新WatchList
		initData();
	}

}
