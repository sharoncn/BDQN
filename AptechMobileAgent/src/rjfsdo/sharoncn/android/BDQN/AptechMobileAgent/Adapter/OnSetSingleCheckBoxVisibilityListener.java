package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Adapter;

public interface OnSetSingleCheckBoxVisibilityListener {
	/**
	 * 原来项目中的接口，原来有FileManager中文件多选功能。因为FileManager中仍有checkbox的判断逻辑，
	 * 只是并没有让checkbox显示，所以先保留吧。
	 * @param position
	 * @return 
	 */
	int OnSetSingleCheckBoxVisibility(int position);
}
