package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Interfaces;

public interface IService {
	void unlock(String pkgName);
	void notifyDataChanged();
	void changeScreenStatus(boolean isOn);
}
