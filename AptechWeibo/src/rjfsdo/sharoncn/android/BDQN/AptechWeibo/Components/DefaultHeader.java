package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * 只有一个标题的Header
 * @author sharoncn
 *
 */
public class DefaultHeader extends BaseHeader {
	private static final int LAYOUT_ID = R.layout.header_default;
	private TextView tv_title;
	private Context context;
	
	public DefaultHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	/**
	 *  子类重写时应该首先调用父类方法，然后初始化自己的内部组件
	 */
	@Override
	protected void initInnerViews() {
		final View view = inflater.inflate(LAYOUT_ID, null);
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.CENTER;
		view.setLayoutParams(params);
		tv_title = (TextView) view.findViewById(R.id.txt);
		//默认为黑色字体，字体大小为20sp
		tv_title.setTextColor(Color.WHITE);
		tv_title.setTextSize(20);
		
		this.addView(view);
	}

	/**
	 * 设置标题
	 * @param title 标题内容
	 */
	public void setHeaderTitle(String title){
		if(title != null){
			tv_title.setText(title);
		}
	}
	
	public void setHeaderTitle(int resId){
		this.setHeaderTitle(context.getString(resId));
	}
	
	/**
	 * 设置标题颜色,默认为黑色
	 * @param color 颜色
	 */
	public void setHeaderTitleColor(int color){
		tv_title.setTextColor(color);
	}
	
	/**
	 * 设置标题字体大小,默认16sp
	 * @param size 字体大小
	 */
	public void setHeaderTextSize(int size){
		tv_title.setTextSize(size);
	}
	
	/**
	 * 设置标题的可见性
	 * @param visibility 
	 * 	可见性({@link android.view.View#GONE},{@link android.view.View#VISIBLE},{@link android.view.View#INVISIBLE})
	 */
	public void setTitleVisibility(int visibility){
		tv_title.setVisibility(visibility);
	}
}
