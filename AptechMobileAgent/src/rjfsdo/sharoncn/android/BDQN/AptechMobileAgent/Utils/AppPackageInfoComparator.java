package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils;

import java.text.Collator;
import java.util.Comparator;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Models.AppPackageInfo;

/**
 * 根据AppPackageInfo的name成员为AppPackageInfo排序的Comparator
 * @author shroncn
 *
 */
public class AppPackageInfoComparator implements Comparator<AppPackageInfo> {
	private static Collator cp = Collator.getInstance(java.util.Locale.CHINA);
	private static AppPackageInfoComparator me;
	static {
		me = new AppPackageInfoComparator();
	}

	private AppPackageInfoComparator() {
	}

	@Override
	public int compare(AppPackageInfo app1, AppPackageInfo app2) {
		return cp.compare(app1.getName(), app2.getName());
	}

	public static Comparator<AppPackageInfo> getInstance() {
		return me;
	}

}
