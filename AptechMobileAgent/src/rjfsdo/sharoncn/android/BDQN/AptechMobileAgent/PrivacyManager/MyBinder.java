package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.PrivacyManager;

import android.os.Binder;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Interfaces.IService;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Service.PrivacyService;

/**
 * @author sharoncn
 */
public class MyBinder extends Binder implements IService{
	private PrivacyService service;
	
	public MyBinder(PrivacyService service) {
		this.service = service;
	}

	public PrivacyService getService(){
		return service;
	}
	
	@Override
	public void unlock(String pkgName) {
		service.addUnlock(pkgName);
	}

	@Override
	public void notifyDataChanged() {
		service.initWatchData();
	}

	@Override
	public void changeScreenStatus(boolean isOn) {
		if(!isOn){
			service.clearUnlock();
		}
	}

}
