package rjfsdo.sharoncn.android.BDQN.LeisureLife.Components;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Delicacies.Food;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Listener.OnViewChangedListener;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Food容器，用于显示Food的展示
 * @author sharoncn
 *
 */
public class FoodContainer extends ViewGroup {
	private static final String TAG = "FoodContainer";
	private Context context;
	private Scroller scr;
	private VelocityTracker vtr;
	private float motionStartPst;
	private static final int SNAP_VELOCITY = 0;
	private int currentScn = 0;
	private OnViewChangedListener viewChangedListener;

	public FoodContainer(Context context) {
		this(context, null);
		scr = new Scroller(context);
	}

	public FoodContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		scr = new Scroller(context);
		this.context = context;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childLeft = 0;
		final int childCount = getChildCount();
		Log.i(TAG,"onLayout:" + childCount);
		for (int i = 0; i < childCount; i++) {
			final View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE) {
				final int childWidth = childView.getMeasuredWidth();
				childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
				childLeft += childWidth;
			}
		}
	}

	@Override
	public void computeScroll() {
		if (scr.computeScrollOffset()) {
			scrollTo(scr.getCurrX(), scr.getCurrY());
			postInvalidate();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int width = MeasureSpec.getSize(widthMeasureSpec);

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		scrollTo(currentScn * width, 0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float x = event.getX();

		Log.i(TAG,"onTouchEvent:" + action);
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.v(TAG, "ACTION_DOWN");
			if (vtr == null) {
				vtr = VelocityTracker.obtain();
			}
			if (!scr.isFinished()) {
				scr.abortAnimation();
			}
			this.motionStartPst = x;
			break;
		case MotionEvent.ACTION_MOVE:
			// Log.v(TAG,"ACTION_MOVE");
			int deltaX = (int) (motionStartPst - x);
			if (canMove(deltaX)) {
				if (vtr != null) {
					vtr.addMovement(event);
				}
				this.motionStartPst = x;
				this.scrollBy(deltaX, 0);
			}
			break;
		case MotionEvent.ACTION_UP:
			Log.v(TAG, "ACTION_UP");
			int velocityX = 0;
			if (vtr != null) {
				vtr.addMovement(event);
				vtr.computeCurrentVelocity(1000);
				velocityX = (int) vtr.getXVelocity();
			}
			Log.v(TAG, "velocityX:" + velocityX);
			if (velocityX < SNAP_VELOCITY && currentScn < this.getChildCount() - 1) {
				Log.v(TAG, "Move Left");
				this.snapToScreen(currentScn + 1);
			} else if (velocityX > SNAP_VELOCITY && currentScn > 0) {
				Log.v(TAG, "Move Right");
				this.snapToScreen(currentScn - 1);
			} else {
				this.snapToDestination();
			}
			if (vtr != null) {
				vtr.recycle();
				vtr = null;
			}
			break;
		}

		return true;
	}

	private void snapToScreen(int position) {
		position = Math.max(0, Math.min(position, getChildCount() - 1));
		if (getScrollX() != (position * getWidth())) {
			final int delta = position * getWidth() - getScrollX();
			scr.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 2);
			currentScn = position;
			invalidate();
			if (this.viewChangedListener != null) {
				this.viewChangedListener.onViewChanged(position);
			}
		}
	}

	private void snapToDestination() {
		final int screenWidth = getWidth();
		final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
		snapToScreen(destScreen);
	}

	public void setOnViewChangedListener(OnViewChangedListener l) {
		this.viewChangedListener = l;
	}

	private boolean canMove(int deltaX) {
		if (this.getScrollX() <= 0 && deltaX < 0) {
			return false;
		}
		if (this.getScrollX() >= (this.getChildCount() - 1) * this.getWidth() && deltaX > 0) {
			return false;
		}
		return true;
	}

	public static class FoodView extends LinearLayout {
		private Context context;
		private ImageView img;
		private TextView tv_name, tv_oldprice, tv_newprice;

		public FoodView(Context context) {
			this(context, null);
		}
		
		public FoodView(Context context, Food food,Drawable d) {
			this(context);
			putFood(food, d);
		}

		public FoodView(Context context, AttributeSet attrs) {
			super(context, attrs);
			this.context = context;
			init();
		}

		private void init() {
			View v = LayoutInflater.from(context).inflate(R.layout.item_food_view, null);
			img = (ImageView) v.findViewById(R.id.item_food_img);
			tv_name = (TextView) v.findViewById(R.id.item_food_name);
			tv_oldprice = (TextView) v.findViewById(R.id.item_food_oldprice);
			tv_newprice = (TextView) v.findViewById(R.id.item_food_newprice);
			v.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			this.addView(v);
		}

		public void putFood(Food food, Drawable drawable) {
			img.setBackgroundDrawable(drawable);
			tv_name.setText(context.getString(R.string.food_name) + food.getName());
			tv_oldprice.setText(context.getString(R.string.food_oldprice) + food.getOprice());
			tv_newprice.setText(context.getString(R.string.food_newprice) + food.getNprice());
		}
	}

	public void putFood(Food food, Drawable d) {
		FoodView fChild = new FoodView(context);
		fChild.putFood(food, d);
		this.addView(fChild);
		Log.i(TAG,"putFood:" + getChildCount());
	}
}
