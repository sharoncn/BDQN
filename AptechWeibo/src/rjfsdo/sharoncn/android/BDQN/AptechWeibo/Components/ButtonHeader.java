package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class ButtonHeader extends HasButtonHeader {

	public ButtonHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected View inflateLayoutRes() {
		layoutRes = R.layout.header_button;
		return super.inflateLayoutRes();
	}
	
	/**
	 * 设置左边按钮内容
	 * @param resId int 要设置的内容ID
	 */
	public void setLeftButtonText(int resId){
		this.setLeftButtonText(mContext.getString(resId));
	}

	/**
	 * 设置左边按钮内容
	 * @param text String 内容
	 */
	public void setLeftButtonText(String text) {
		((Button)btnLeft).setText(text);
	}
	
	/**
	 * 设置右边按钮内容
	 * @param resId int 要设置的内容
	 */
	public void setRightButtonText(int resId){
		this.setRightButtonText(mContext.getString(resId));
	}

	/**
	 * 设置右边按钮内容
	 * @param text String 要设置的内容
	 */
	public void setRightButtonText(String text) {
		((Button)btnRight).setText(text);
	}
}
