package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * 多列Panel
 * 将椭圆矩形信息展示窗口封装成Panel
 * @author sharoncn
 *
 */
public class MultiColumnPanel extends Panel {
	private int rowIndex = -1;

	public MultiColumnPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public int newRow() {
		rowIndex++;
		return rowIndex;
	}

	public void addItem(View child) {
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		params.weight = 1;
		this.addItem(child, rowIndex, params);
	}
	
	public int getCurrentRowIndex(){
		return rowIndex;
	}
	
}