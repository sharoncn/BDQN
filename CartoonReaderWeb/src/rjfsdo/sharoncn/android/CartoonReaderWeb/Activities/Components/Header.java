package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ��װͷ��
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
	 * ���ñ������ֵĿɼ���
	 * @param visibility
	 */
	public void setTitleVisibility(int visibility){
		title.setVisibility(visibility);
	}
	
	/**
	 * ���ñ�������
	 * @param title
	 */
	public void setTitle(String title){
		this.title.setText(title);
	}
	
	/**
	 * ���ñ���������ɫ
	 * @param color
	 */
	public void setTitleColor(int color){
		title.setTextColor(color);
	}
	
	/**
	 * ���ñ������ִ�С
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
	 * ���ñ���߶�
	 * @param height
	 */
	public void setHeight(int height){
		LayoutParams params = (LayoutParams) container.getLayoutParams();
		params.height = height;
		container.setLayoutParams(params);
	}
}
