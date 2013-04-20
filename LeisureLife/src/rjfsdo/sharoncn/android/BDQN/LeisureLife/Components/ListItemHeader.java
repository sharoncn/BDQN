package rjfsdo.sharoncn.android.BDQN.LeisureLife.Components;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * 详情信息中不同显示信息的头部
 * @author sharoncn
 *
 */
public class ListItemHeader extends LinearLayout implements OnClickListener{
	private Button btn;
	private TextView  title;
	private OnClickListener listener;
	private boolean flag_btn_state = false;

	public ListItemHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		View child = LayoutInflater.from(context).inflate(R.layout.list_item_detail_base_header, null);
		child.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, Util.dp2Px(context, 40)));
		btn = (Button) child.findViewById(R.id.list_item_detail_base_header_btn);
		title = (TextView) child.findViewById(R.id.list_item_detail_base_header_title);
		this.addView(child);
	}

	/**
	 * 设置标题
	 * @param resid
	 */
	public void setTitle(int resid){
		title.setText(resid);
	}
	
	/**
	 * 设置标题
	 * @param title
	 */
	public void setTitle(String title){
		this.title.setText(title);
	}
	
	public String getTitle(){
		return title.getText().toString();
	}
	
	/**
	 * 设置按钮的监听器
	 * @param l
	 */
	public void setButtonOnClickListener(OnClickListener l){
		btn.setOnClickListener(this);
		this.listener = l;
	}

	@Override
	public void onClick(View v) {
		if(listener != null){
			flag_btn_state = !flag_btn_state;
			if(flag_btn_state){
				v.setBackgroundResource(R.drawable.sanjiao_s);
			}else{
				v.setBackgroundResource(R.drawable.sanjiao_n);
			}
			v.setTag(flag_btn_state);
			listener.onClick(v);
		}
	}
}
