package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * ����Panel
 * ����Բ������Ϣչʾ���ڷ�װ��Panel
 * @author sharoncn
 *
 */
public class OneColumnPanel extends Panel {
	private int rowCount = 0;

	public OneColumnPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void addItem(View child) {
		LinearLayout.LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		this.addItem(child, rowCount, params);
		rowCount++;
	}

}
