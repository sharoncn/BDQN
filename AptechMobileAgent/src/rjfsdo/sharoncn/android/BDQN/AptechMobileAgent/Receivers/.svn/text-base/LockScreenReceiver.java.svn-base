package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Receivers;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Interfaces.IService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LockScreenReceiver extends BroadcastReceiver {
	private static final String TAG = "LockScreenReceiver";
	private IService service;
	
	public LockScreenReceiver(IService service) {
		this.service = service;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		final String action = intent.getAction();
		if(Intent.ACTION_USER_PRESENT.equals(action)){
			//”√ªßΩ‚À¯Action
			Log.i(TAG, "ACTION_USER_PRESENT");
		}else if(Intent.ACTION_SCREEN_ON.equals(action)){
			Log.i(TAG, "ACTION_SCREEN_ON");
			service.changeScreenStatus(true);
		}else if(Intent.ACTION_SCREEN_OFF.equals(action)){
			Log.i(TAG, "ACTION_SCREEN_OFF");
			service.changeScreenStatus(false);
		}
	}

}
