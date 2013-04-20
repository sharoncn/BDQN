package rjfsdo.sharoncn.android.BDQN.LeisureLife.Components;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Header父类
 * @author sharoncn
 *
 */
public abstract class BaseHeader extends LinearLayout {
	private List<View> views = new ArrayList<View>();
	
	public BaseHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setBackgroundResource(int resid) {
		super.setBackgroundResource(resid);
	}

	/**
	 * 设置Header标题
	 * @param title
	 */
	public abstract void setTitle(String title);
	/**
	 * 设置Header标题的颜色
	 * @param resid
	 */
	public abstract void setTitleColor(int resid);
	/**
	 * 设置Header标题字体大小
	 * @param size
	 */
	public abstract void setTitleTextSize(float size);
	/**
	 * 设置Header标题可见性
	 * @param visibility
	 */
	public abstract void setTitleVisibility(int visibility);
	
	/**
	 * 为Header注册Button
	 * @param view
	 */
	protected void registerButtons(View view){
		views.add(view);
	}
	
	/**
	 * 为Header注册Button
	 * @param position
	 * @param view
	 */
	protected void registerButtons(int position,View view){
		views.add(position,view);
	}
	
	/**
	 * 为Header的button设置监听器
	 * @param l
	 */
	public void setOnButtonClickListener(OnClickListener l){
		for(View view:views){
			view.setOnClickListener(l);
		}
	}
	
	/**
	 * 设置Button的可见性
	 * @param position
	 * @param visibility
	 */
	public void setButtonVisibility(int position,int visibility){
		if(position == -1){
			for(View view:views){
				view.setVisibility(visibility);
			}
		}else{
			views.get(position).setVisibility(visibility);
		}
	}
}
