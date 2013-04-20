package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 封装头部
 * @author sharoncn
 *
 */
public class Header extends LinearLayout {
	private TextView title;
	private Context context;
	private LinearLayout container;
	
	public Header(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initHeader();
	}

	private void initHeader(){
		container = new LinearLayout(context);
		LayoutParams container_params = new LayoutParams(LayoutParams.MATCH_PARENT, 
				LayoutParams.WRAP_CONTENT);
		container_params.gravity = Gravity.CENTER;
		container.setLayoutParams(container_params);
		container.setGravity(Gravity.CENTER);
		container.setBackgroundResource(R.drawable.top_bg);
		
		title = new TextView(context);
		LayoutParams title_params = new LayoutParams(LayoutParams.WRAP_CONTENT, 
				LayoutParams.WRAP_CONTENT);
		title.setLayoutParams(title_params);
		container.addView(title);
		this.addView(container);
	}
	
	/**
	 * 设置标题文字的可见性
	 * @param visibility
	 */
	public void setTitleVisibility(int visibility){
		title.setVisibility(visibility);
	}
	
	/**
	 * 设置标题内容
	 * @param title
	 */
	public void setTitle(String title){
		this.title.setText(title);
	}
	
	/**
	 * 设置标题字体颜色
	 * @param color
	 */
	public void setTitleColor(int color){
		title.setTextColor(color);
	}
	
	/**
	 * 设置标题文字大小
	 * @param size
	 */
	public void setTextSize(float size){
		title.setTextSize(size);
	}
	
	@Override
	public void setBackgroundColor(int color) {
		container.setBackgroundColor(color);
		super.setBackgroundColor(color);
	}

	@Override
	public void setBackgroundResource(int resid) {
		container.setBackgroundResource(resid);
		super.setBackgroundResource(resid);
	}

	/**
	 * 设置标题高度
	 * @param height
	 */
	public void setHeight(int height){
		LayoutParams params = (LayoutParams) container.getLayoutParams();
		params.height = height;
		container.setLayoutParams(params);
	}
}
