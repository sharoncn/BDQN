package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.PrivacyManager;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Interfaces.IService;
import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;

public class PrivacyObserver extends ContentObserver {
	private static final String TAG = "PrivacyObserver";
	private IService service;

	public PrivacyObserver(Handler handler) {
		super(handler);
	}

	/**
	 * 让观察者持有一个Service中binder的引用，用于调用service中的方法
	 * 
	 * @param service
	 * @return
	 */
	public PrivacyObserver setService(IService service) {
		this.service = service;
		return this;
	}

	@Override
	public void onChange(boolean selfChange) {
		Log.i(TAG, "onChange:" + selfChange);
		service.notifyDataChanged();
		super.onChange(selfChange);
	}

}
