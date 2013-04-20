package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * header»ùÀà
 * @author sharoncn
 *
 */
public abstract class BaseHeader extends LinearLayout {
	protected static LayoutInflater inflater;
	protected Context mContext;
	
	public BaseHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		if (inflater == null)
			inflater = LayoutInflater.from(context);
		
		initViews(prepareLayout());
	}

	protected abstract void initViews(int layout);
	protected abstract int prepareLayout();
}
