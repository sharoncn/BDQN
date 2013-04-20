package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Test;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyTestListView extends ListView {

	public MyTestListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		long start = System.nanoTime();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		long end = System.nanoTime();
		System.out.println("onMeasure 调用时间:" + (end - start) + "纳秒");
	}
}
