package rjfsdo.sharoncn.android.BDQN.LeisureLife.Components;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Delicacies.Food;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Listener.OnViewChangedListener;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Food的陈列柜，聚合FoodContainer和indicator
 * @author shraoncn
 *
 */
public class FoodShowcase extends FrameLayout implements OnViewChangedListener {
	//private static final String TAG = "FoodShowcase";
	private LinearLayout indicator;
	private FoodContainer fContainer;
	private Context context;
	private int indicatorDrawable = R.drawable.indicator;
	private int currentItem = 0;
	
	public FoodShowcase(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return fContainer.onTouchEvent(event);
	}

	private void init() {
		//初始化indicator
		indicator = new LinearLayout(context);
		FrameLayout.LayoutParams iParams = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		iParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
		indicator.setLayoutParams(iParams);
		
		//初始化FoodContainer
		fContainer = new FoodContainer(context);
		LayoutParams fParams = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		fContainer.setLayoutParams(fParams);
		fContainer.setOnViewChangedListener(this);
		this.addView(fContainer);
		this.addView(indicator);
	}

	public void setIndicatorDrawable(int resId){
		indicatorDrawable = resId;
	}
	
	public void putFood(Food food,Drawable d){
		fContainer.putFood(food,d);
		
		ImageView iChild = new ImageView(context);
		LinearLayout.LayoutParams icParams = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		icParams.setMargins(5, 5, 5, 5);
		iChild.setLayoutParams(icParams);
		iChild.setBackgroundResource(indicatorDrawable);
		indicator.addView(iChild);
		fContainer.requestLayout();
		if(indicator.getChildCount() > 0){
			initIndicator();
		}
	}

	private void initIndicator() {
		final int count = indicator.getChildCount();
		for(int i = 0;i < count;i ++){
			if(i == 0){
				indicator.getChildAt(i).setEnabled(true);
			}else{
				indicator.getChildAt(i).setEnabled(false);
			}
		}
	}

	@Override
	public void onViewChanged(int position) {
//		int count = indicator.getChildCount();
//		
//		for(int i = 0;i < count;i ++){
//			final View view = indicator.getChildAt(i);
//			if(i == position){
//				view.setEnabled(true);
//			}else{
//				view.setEnabled(false);
//			}
//		}
		if(position < 0 || position >= indicator.getChildCount())return;
		final View view = indicator.getChildAt(currentItem);
		if(view.isEnabled()){
			view.setEnabled(false);
		}
		indicator.getChildAt(position).setEnabled(true);
		currentItem = position;
	}
}
