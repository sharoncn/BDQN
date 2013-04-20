package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Header基类
 * @author sharoncn
 *
 */
public abstract class BaseHeader extends FrameLayout {
	protected static LayoutInflater inflater;
	protected Context mContext;
	
	public BaseHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		inflater = LayoutInflater.from(context);
		initInnerViews();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		Log.i("BaseHeader", "onSizeChanged:" + w + "  " + h);
		for(int i = 0;i < this.getChildCount(); i++){
			View v = this.getChildAt(i);
			Log.i("BaseHeader", "Child:" + v.toString());
			Log.i("BaseHeader", "Child:" + v.getWidth() + "  " + v.getHeight());
			android.view.ViewGroup.LayoutParams params = v.getLayoutParams();
			Log.i("BaseHeader", "Child:" + params.width + "  " + params.height);
		}
		super.onSizeChanged(w, h, oldw, oldh);
	}

	/**
	 * 初始化Header内部View
	 */
	protected abstract void initInnerViews();
	
}
