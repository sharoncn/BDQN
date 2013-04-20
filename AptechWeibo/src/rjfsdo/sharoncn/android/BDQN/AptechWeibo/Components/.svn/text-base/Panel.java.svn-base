package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 请不要使用addView方法添加。
 * 感觉Panel这样设计不太合理。无法限制AddView
 * @author sharoncn
 *
 */
public abstract class Panel extends LinearLayout {
	private static final String TAG = "Panel";
	protected static LayoutInflater inflater;
	private ArrayList<LinearLayout> rows = new ArrayList<LinearLayout>();
	private int numRows = 0;
	protected Context mContext;

	public Panel(Context context) {
		this(context, null);
	}

	public Panel(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		init();
	}

	private void init() {
		this.setBackgroundResource(R.drawable.panel_bg);
		this.setOrientation(LinearLayout.VERTICAL);
		this.setPadding(0, 0, 0, 0);
		Log.v(TAG,"Padding:" + this.getPaddingLeft() + ":" + this.getPaddingRight());
		this.setFocusable(false);
		this.setFocusableInTouchMode(false);
		this.setClickable(false);
	}
	
	protected void addItem(View child,int row, LinearLayout.LayoutParams params) {
		Log.v(TAG,"row:" + row);
		LinearLayout rowContainer;
		if(row >= numRows){
			Log.v(TAG,"new Row");
			if(row > 0){
				Log.v(TAG,"add HorizontalDivider");
				this.addView(getHorizontalDivider(mContext));
			}
			rowContainer = new LinearLayout(mContext);
			LayoutParams rowParams = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			rowParams.gravity = Gravity.CENTER;
			rowContainer.setLayoutParams(rowParams);
			rowContainer.setOrientation(LinearLayout.HORIZONTAL);
			this.addView(rowContainer);
			rows.add(rowContainer);
			numRows ++;
		}else{
			Log.v(TAG,"get Row");
			rowContainer = rows.get(row);
			if(rowContainer.getChildCount() > 0){
				Log.v(TAG,"add VerticalDivider");
				rowContainer.addView(getVerticalDivider(mContext));
			}
		}
		rowContainer.addView(child,params);
		Log.v(TAG,"children count:" + this.getChildCount());
	}

	private View getVerticalDivider(Context context){
		ImageView divider = new ImageView(context);
		LayoutParams params = new LayoutParams(2, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		params.weight = 0;
		divider.setLayoutParams(params);
		divider.setBackgroundResource(R.color.divider);
		return divider;
	}
	
	private View getHorizontalDivider(Context context){
		ImageView divider = new ImageView(context);
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 2);
		divider.setLayoutParams(params);
		divider.setBackgroundResource(R.color.divider);
		return divider;
	}
	
}
