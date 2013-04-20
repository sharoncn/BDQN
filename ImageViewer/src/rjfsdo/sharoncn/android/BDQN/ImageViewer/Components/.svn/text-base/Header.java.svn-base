package rjfsdo.sharoncn.android.BDQN.ImageViewer.Components;

import rjfsdo.sharoncn.android.BDQN.ImageViewer.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 封装顶部header。
 * @author sharoncn
 *
 */
/**
 * @author sharoncn
 *
 */
public class Header extends LinearLayout {
	private LayoutInflater inflater;
	private LinearLayout main_bg;
	private ImageView main_back,main_dropdown;
	private TextView main_title;
	
	public Header(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflater = LayoutInflater.from(context);
		View view =inflater.inflate(R.layout.layout_components_header, null);
		this.addView(view);
		
		main_bg = (LinearLayout) view.findViewById(R.id.header_main_bg);
		main_back = (ImageView) view.findViewById(R.id.header_main_back);
		main_dropdown = (ImageView) view.findViewById(R.id.header_main_dropdown);
		main_title = (TextView) view.findViewById(R.id.header_main_title);
	}

	/**
	 * 设置返回按钮的可见性
	 * @param visibility
	 */
	public void setBackButtonVisibility(int visibility){
		main_back.setVisibility(visibility);
	}
	
	/**
	 * 设置Dropdown按钮的可见性
	 * @param visibility
	 */
	public void setDropdownButtonVisibility(int visibility){
		main_dropdown.setVisibility(visibility);
	}
	
	@Override
	public void setBackgroundResource(int resid) {
		super.setBackgroundResource(resid);
		main_bg.setBackgroundResource(resid);
	}
	
	/**
	 * 设置标题
	 * @param resid
	 */
	public void setTitle(int resid){
		main_title.setText(resid);
	}
	
	/**
	 * 设置标题文字
	 * @param title
	 */
	public void setTitle(String title){
		main_title.setText(title);
	}
	
	/**
	 * 为Back按钮设置监听器
	 * @param l
	 */
	public void setOnBackClickListener(OnClickListener l){
		main_back.setOnClickListener(l);
	}
	
	/**
	 * 为Dropdown按钮设置监听器
	 * @param l
	 */
	public void setOnDropdownClickListener(OnClickListener l){
		main_dropdown.setOnClickListener(l);
	}
}
