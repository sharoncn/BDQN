package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import android.content.Context;
import android.util.AttributeSet;

public class DetailPanel extends CountPanel {

	public DetailPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void prepare() {
		titles = new String[1][2];
		titles[0][0] = mContext.getString(R.string.comment_count);
		titles[0][1] = mContext.getString(R.string.repost_count);
	}

}
