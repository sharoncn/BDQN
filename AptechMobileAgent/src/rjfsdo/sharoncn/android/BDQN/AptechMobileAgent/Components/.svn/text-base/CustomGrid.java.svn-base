package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Adapter.CustomGridAdapter;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Adapter.CustomGridAdapter.GridDataInfo;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.RectComparetor;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * 拖放逻辑为直接改变要拖放目标View的背景和位置。这种方式更直接一些吧，不需要额外的创建View。
 * @author sharoncn
 *
 */
public class CustomGrid extends GridView implements OnItemLongClickListener {
	//private static final String TAG = "CustomGrid";
	// 主页面中gridview中的对象可拖拽
	private View dragView = null;
	private int x = 0;
	private int y = 0;
	private boolean isStartDragging = false;
	private Drawable bgCache;
	private static LayoutInflater inflater;
	private GridDataInfo data;
	private int xOffset, yOffset;
	private int position;
	private CustomGridAdapter adapter;
	private OnItemLongClickListener listener;

	public CustomGrid(Context context, AttributeSet attrs) {
		super(context, attrs);
		super.setOnItemLongClickListener(this);
		if (inflater == null)
			inflater = LayoutInflater.from(context);
	}

	@Override
	public void setOnItemLongClickListener(OnItemLongClickListener listener) {
		this.listener = listener;
		super.setOnItemLongClickListener(this);
	}



	// 所谓拦截事件是指是否下发事件给内部的View。无论如何ViewGroup的onTouch都被调用
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			//直接将事件交给OnTouch
			System.out.println("MotionEvent.ACTION_DOWN");
			return true;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		x = (int) ev.getX();
		y = (int) ev.getY();
		final int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			System.out.println("onTouchEvent MotionEvent.ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			//只拦截拖动时move事件
			System.out.println("MotionEvent.ACTION_MOVE");
			if (isStartDragging) {
				onDrag(dragView);
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			System.out.println("MotionEvent.ACTION_UP");
			if (isStartDragging)
				stopDrag();
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 开始拖动View
	 */
	private void startDrag() {
		if (dragView == null) {
			return;
		}
		initRects();
		isStartDragging = true;
		changeStatus(isStartDragging, dragView, data);
		onDrag(dragView);
	}

	//初始化CustomGrid容器中各个子对象的矩形
	private void initRects(){
		final int count = this.getChildCount();
		System.out.println("" + this.getChildCount());
		positions.clear();
		rects.clear();
		
		for (int i = 0; i < count; i++) {
			final View view = this.getChildAt(i);
			final Rect rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
			positions.put(rect, i);
			rects.add(rect);
			System.out.println("child Rect:" + rect.toShortString());
		}
	}
	
	//当move触发时会掉哦那个onDrag。目标View会不断的改变位置
	private void onDrag(View v) {
		final int l = x - xOffset;
		final int t = y - yOffset;
		final int r = x + xOffset;
		final int b = y + yOffset;
		System.out.println("x:" + x + "  y:" + y + "  l:" + l + "  t:" + t + "  r:" + r + "  b:" + b);
		v.layout(l, t, r, b);
		v.postInvalidate();
	}

	//停止拖动
	private void stopDrag() {
		isStartDragging = false;
		changeStatus(isStartDragging, dragView, data);
		onDrop();
	}

	//放下目标View
	private void onDrop() {
		final int rightPositon = getRightPlace();
		adapter.exchange(position, rightPositon);
	}

	private HashMap<Rect, Integer> positions = new HashMap<Rect, Integer>();
	private ArrayList<Rect> rects = new ArrayList<Rect>();

	/**
	 * 获取正确的位置放置View
	 * @return 正确位置的position
	 */
	private int getRightPlace() {
		final Rect dragViewRect = new Rect(dragView.getLeft(), dragView.getTop(), dragView.getRight(), dragView.getBottom());
		final Rect[] viewRects = rects.toArray(new Rect[] {});
		Arrays.sort(viewRects, RectComparetor.getInstance(dragViewRect));
		int p = positions.get(viewRects[0]);
		System.out.println("最合适的位置:" + p);
		return p;
	}

	/**
	 * 根据isDrag改变目标View的状态
	 * @param isDrag 是否正在拖动
	 * @param view   目标view
	 * @param data   必需的数据
	 */
	private void changeStatus(boolean isDrag, View view, GridDataInfo data) {
		final ImageView icon = (ImageView) view.findViewById(R.id.icon);
		if (isDrag) {
			view.setBackgroundResource(R.color.alphablack);
			icon.setBackgroundDrawable(data.getDragIcon());
		} else {
			view.setBackgroundDrawable(bgCache);
			icon.setBackgroundDrawable(data.getIcon());
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adpView, View view, int position, long id) {
		this.position = position;
		System.out.println("onItemLongClick");
		dragView = view;
		xOffset = view.getWidth() / 2;
		yOffset = view.getHeight() / 2;
		bgCache = view.getBackground();
		adapter = (CustomGridAdapter) adpView.getAdapter();
		data = (GridDataInfo) adapter.getItem(position);
		
		startDrag();
		
		if(listener != null) listener.onItemLongClick(adpView, view, position, id);
		return true;
	}
}
