package rjfsdo.sharoncn.android.BDQN.ImageViewer.Components;

import rjfsdo.sharoncn.android.BDQN.ImageViewer.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 封装图片查看Activity需要的Header。只有ViewActivity一个需求
 * @author sharoncn
 *
 */
public class ViewHeader extends LinearLayout {
	private static final String TAG = "ViewHeader";
	private RelativeLayout container;
	private ImageButton previous,next;
	private TextView info;

	public ViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = LayoutInflater.from(context).inflate(R.layout.layout_components_header_view, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(view);
		
		container = (RelativeLayout) view.findViewById(R.id.view_header_container);
		previous = (ImageButton) view.findViewById(R.id.view_header_back);
		next = (ImageButton) view.findViewById(R.id.view_header_next);
		info = (TextView) view.findViewById(R.id.view_header_pageinfo);
	}

	/**
	 * 设置向前按钮的监听器
	 * @param l
	 */
	public void setOnPreviousClickListener(OnClickListener l){
		previous.setOnClickListener(l);
	}
	
	/**
	 * 设置向后按钮的监听器
	 * @param l
	 */
	public void setOnNextClickListener(OnClickListener l){
		next.setOnClickListener(l);
	}

	@Override
	public void setBackgroundResource(int resid) {
		if(container == null){
			Log.v(TAG,"container is null");
		}else{
			container.setBackgroundResource(resid);
		}
		super.setBackgroundResource(resid);
	}
	
	/**
	 * 设置标题内容
	 * @param title
	 */
	public void setTitle(String title){
		info.setText(title);
	}
}
