package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Components;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 为Header定义了设计属性(headerTitle)
 * @author sharoncn
 *
 */
public class DefaultHeader extends BaseHeader {
	private static final int DEFAULT_LAYOUT = R.layout.components_header_default;
	private View view;
	private TextView title;
	protected RelativeLayout container;

	public DefaultHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.rjf);
		int indexCount = a.getIndexCount();
		for (int i = 0; i < indexCount; i++) {
			final int index = a.getIndex(i);
			switch (index) {
			case R.styleable.rjf_headerTitle:
				title.setText(a.getResourceId(i, 0));
				break;
			}
		}
		a.recycle();
	}

	/**
	 * 设置Header标题内容
	 * @param text 内容 
	 */
	public void setHeaderTitle(String text){
		title.setText(text);
	}
	
	/**
	 * 设置Header标题内容
	 * @param resId 内容
	 */
	public void setHeaderTitle(int resId){
		this.setHeaderTitle(mContext.getString(resId));
	}
	
	@Override
	protected int prepareLayout() {
		return DEFAULT_LAYOUT;
	}

	@Override
	protected void initViews(int layout) {
		view = inflater.inflate(layout, null);
		final LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(params);
		this.addView(view);

		// 初始化Title
		title = (TextView) view.findViewById(R.id.header_title);
		container = (RelativeLayout) view.findViewById(R.id.header_container);
	}

}
