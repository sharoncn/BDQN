package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Receivers;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Interfaces.ITrafficService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ShutdownReceiver extends BroadcastReceiver {
	private static final String TAG = "ShutdownReceiver";
	private static ITrafficService mService;

	public ShutdownReceiver(ITrafficService service){
		mService = service;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		final String action = intent.getAction();
		Log.i(TAG, "ShutdownReceiver onReceive");
		if (Intent.ACTION_SHUTDOWN.equals(action)) {
			// 保存流量记录
			// bind到traffic服务
			// 保存流量数据
			if(mService != null){
				mService.onShutdown();
			}else{
				Log.i(TAG, "mService is null");
			}
		}
	}
	
	@Override
	public IBinder peekService(Context myContext, Intent service) {
		Log.i(TAG, "ShutdownReceiver peekService");
		return super.peekService(myContext, service);
	}

}
