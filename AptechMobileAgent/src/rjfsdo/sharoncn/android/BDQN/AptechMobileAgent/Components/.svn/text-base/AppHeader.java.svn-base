package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Components;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Util;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * AppManager中使用
 * @author sharoncn
 *
 */
public class AppHeader extends DefaultHeader {
	private TextView memoryRemaining;
	private String text;

	public AppHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		text = mContext.getString(R.string.remaining_mem_size);
		addText();
	}

	private void addText() {
		memoryRemaining = new TextView(mContext);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, 5, 0);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		memoryRemaining.setLayoutParams(params);
		memoryRemaining.setTextColor(Color.WHITE);
		memoryRemaining.setTextSize(10);

		container.addView(memoryRemaining);
		memoryRemaining.setText(String.format(text, Util.getFormatedSize(0d)));
	}

	/**
	 * 设置SDCard的剩余容量
	 * 
	 * @param size
	 */
	public void setMemoryRemainingSize(double size) {
		memoryRemaining.setText(String.format(text, Util.getFormatedSize(size)));
	}
}
