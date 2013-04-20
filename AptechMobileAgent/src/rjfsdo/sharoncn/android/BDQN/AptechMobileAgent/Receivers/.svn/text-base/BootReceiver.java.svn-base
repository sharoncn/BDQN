package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Receivers;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Service.PrivacyService;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Service.TrafficService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
	private static final String TAG = "BootReceiver";
	private static Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (mContext == null)
			mContext = context;
		final String action = intent.getAction();
		// 接收启动完成的Action
		if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
			// 启动完成
			Log.i(TAG, "系统启动完成");
			startPrivacyService();
			startTrafficService();
		}
	}

	//启动流量统计服务
	private void startTrafficService() {
		final Intent intent = new Intent(mContext, TrafficService.class);
		mContext.startService(intent);
	}

	//启动隐私保护服务
	private void startPrivacyService() {
		final Intent intent = new Intent(mContext, PrivacyService.class);
		mContext.startService(intent);
	}

}
