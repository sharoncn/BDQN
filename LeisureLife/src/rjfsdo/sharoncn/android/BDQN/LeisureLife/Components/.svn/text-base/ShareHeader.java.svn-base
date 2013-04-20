package rjfsdo.sharoncn.android.BDQN.LeisureLife.Components;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 分享Activity中使用的头部
 * @author sharoncn
 *
 */
public class ShareHeader extends BaseHeader {
	public static final int BUTTON_BACK = R.id.shareheader_btn_back;
	public static final int BUTTON_SEND = R.id.shareheader_btn_send;
	private TextView title;
	private Context context;
	private View container;
	
	public ShareHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		container = LayoutInflater.from(context).inflate(R.layout.layout_header_share, null);
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, Util.dp2Px(context, 60));
		container.setLayoutParams(params);
		container.setBackgroundResource(R.drawable.head);
		this.addView(container);
		title = (TextView) container.findViewById(R.id.shareheader_btn_title);
		Button view = (Button) container.findViewById(BUTTON_BACK);
		view.setText(R.string.back);
		registerButtons(view);
		view = (Button) container.findViewById(BUTTON_SEND);
		registerButtons(view);
	}

	@Override
	public void setTitle(String title) {
		this.title.setText(title);
	}

	@Override
	public void setTitleColor(int resid) {
		title.setTextColor(context.getResources().getColor(resid));
	}

	@Override
	public void setTitleTextSize(float size) {
		title.setTextSize(size);
	}

	@Override
	public void setTitleVisibility(int visibility) {
		title.setVisibility(visibility);
	}

}
