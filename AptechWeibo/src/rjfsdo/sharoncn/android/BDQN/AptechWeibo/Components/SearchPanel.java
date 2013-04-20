package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import android.content.Context;
import android.util.AttributeSet;

public class SearchPanel extends ClickableOneColumnPanel {
	
	public SearchPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void prapare() {
		rowNum = 4;
		contents = new String[]{
				mContext.getString(R.string.hot_status),
				mContext.getString(R.string.hot_repost),
				mContext.getString(R.string.hot_comment),
				mContext.getString(R.string.lookaround)
		};
	}

}
