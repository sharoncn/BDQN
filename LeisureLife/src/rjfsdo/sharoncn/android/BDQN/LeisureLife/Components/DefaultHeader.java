package rjfsdo.sharoncn.android.BDQN.LeisureLife.Components;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 默认头部组件
 * @author sharoncn
 *
 */
public class DefaultHeader extends BaseHeader {
	private TextView title;
	private Context context;
	private View container;
	
	public DefaultHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		container = LayoutInflater.from(context).inflate(R.layout.layout_header_default, null);
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, Util.dp2Px(context, 60));
		params.gravity = Gravity.CENTER;
		container.setLayoutParams(params);
		container.setBackgroundResource(R.drawable.head);
		this.addView(container);
		title = (TextView) container.findViewById(R.id.default_header_title);
	}

	@Override
	public void setBackgroundResource(int resid) {
		this.container.setBackgroundResource(resid);
		//super.setBackgroundResource(resid);
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
