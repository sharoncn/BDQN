package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public final class AddrPanel extends OneColumnPanel {
	private ArrayList<TextView> allowSetContentViews = new ArrayList<TextView>();
	public AddrPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews();
	}

	private void initViews() {
		View view = inflater.inflate(R.layout.panel_simple_info, null);
		TextView tv_title = (TextView) view.findViewById(R.id.panel_title);
		TextView tv_content = (TextView) view.findViewById(R.id.panel_content);
		allowSetContentViews.add(tv_content);
		tv_title.setText(mContext.getString(R.string.addris));
		this.addItem(view);

		view = inflater.inflate(R.layout.panel_simple_info, null);
		tv_title = (TextView) view.findViewById(R.id.panel_title);
		tv_content = (TextView) view.findViewById(R.id.panel_content);
		allowSetContentViews.add(tv_content);
		tv_title.setText(mContext.getString(R.string.descriptionis));

		this.addItem(view);
	}

	/**
	 * 为Panel的item设置内容
	 * @param index 取值范围[0-1]
	 * @param resId  要设置的内容
	 */
	public void setContentText(int index,int resId){
		setContentText(index,mContext.getString(resId));
	}
	
	/**
	 * 为Panel的item设置内容
	 * @param index 取值范围[0-1]
	 * @param text  要设置的内容
	 */
	public void setContentText(int index, String text){
		if(index > 1 || index < 0){
			throw new ArrayIndexOutOfBoundsException("index");
		}
		TextView tv = allowSetContentViews.get(index);
		if(tv != null){
			tv.setText(text);
		}
	}
}
