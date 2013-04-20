package rjfsdo.sharoncn.android.BDQN.ImageViewer.Components;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 底部menu基类，封装一些简单的功能
 * @author sharoncn
 *
 */
public class BaseMenu extends LinearLayout {
	private List<View> views = new ArrayList<View>();
	
	public BaseMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 为注册过的View添加点击监听器
	 * @param l OnClickListener
	 */
	public void setOnMenuButtonClickListenter(OnClickListener l){
		for(View view:views){
			view.setOnClickListener(l);
		}
	}

	/**
	 * 注册view,view注册的顺序影响它的索引，子类注册时需要注意,如果非按顺序注册请使用
	 * registerClickableView(int index,View view)
	 * @param view 要注册的view
	 */
	protected void registerClickableView(View view){
		views.add(view);
	}
	
	/**
	 * 注册view
	 * @param index 索引
	 * @param view 需要注册的view
	 * @see BaseMenu#registerClickableView(View view)
	 */
	protected void registerClickableView(int index,View view){
		views.add(index,view);
	}
	
	/**
	 * 设置按钮的可见性
	 * @param index 添加的view在view列表中的索引
	 * @param visibility 可见性 One of View.GONE,View.VISIBLE,View.INVISIBLE
	 */
	public void setViewVisibility(int index,int visibility){
		views.get(index).setVisibility(visibility);
	}
}
