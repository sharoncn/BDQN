package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils;

import java.util.Comparator;

import android.graphics.Rect;
import android.util.Log;

/**
 * 比较目标Rect和其他Rect之间的距离，以确定最合适的放置位置
 * @author sharoncn
 *
 */
public class RectComparetor implements Comparator<Rect> {
	private static final String TAG = "RectComparetor";
	private static Rect _rect;
	private static RectComparetor me;
	
	static{
		me = new RectComparetor();
	}
	
	private RectComparetor(){}
	
	public static RectComparetor getInstance(Rect rect){
		_rect = rect;
		return me;
	}
	
	@Override
	public int compare(Rect r1, Rect r2) {
		double distance1 = Math.hypot(Math.abs(r1.centerX() - _rect.centerX()),Math.abs(r1.centerY() - _rect.centerY()));
		double distance2 = Math.hypot(Math.abs(r2.centerX() - _rect.centerX()),Math.abs(r2.centerY() - _rect.centerY()));
		double result = distance1 - distance2;
		Log.i(TAG, "目标RECT：" + r1.toShortString() + "|" + r2.toShortString());
		Log.i(TAG, "结果：" + result);
		return (int)result;
	}
}