package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components;

import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter.BookAdapter;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.Listener.OnMeasuredHeightListener;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @see BaseCanvas
 * @author sharoncn
 *
 */
public class BasicCanvas extends BaseCanvas {
	private ListView list;
	private int MeasuredCanvasHeight = -1;
	private OnMeasuredHeightListener listener;
	
	public BasicCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void initContent() {
		list = getContentListView();
	}

	/**
	 * 为Content设置适配器
	 * @param adapter 适配器
	 */
	public void setContentAdapter(ListAdapter adapter){
		final BookAdapter bookAdapter = (BookAdapter) adapter;
		bookAdapter.setOnMeasuredHeightListener(new OnMeasuredHeightListener() {
			@Override
			public void onMeasuredHeight(int height) {
				boolean flag = (MeasuredCanvasHeight == -1);
				if(flag){
					LayoutParams params = (LayoutParams) list.getLayoutParams();
					MeasuredCanvasHeight = container.getMeasuredHeight() + height * bookAdapter.getCount() 
							+ list.getDividerHeight() * (bookAdapter.getCount() + 1) + params.topMargin + params.bottomMargin
							;
				}
				flag = flag && (listener != null);
				if(flag){
					//Log.v("BasicCanvas","调用监听器");
					listener.onMeasuredHeight(MeasuredCanvasHeight);
				}
			}
		});
		list.setAdapter(adapter);
	}
	
	/**
	 * 为Content的Item设置OnItemClickListener
	 * @param l OnItemClickListener
	 */
	public void setContentOnItemClickListener(OnItemClickListener l){
		list.setOnItemClickListener(l);
	}
	
	/**
	 * 为Content的Item设置OnItemLongClickListener
	 * @param l OnItemLongClickListener
	 */
	public void setContentOnItemLongClickListener(OnItemLongClickListener l){
		list.setOnItemLongClickListener(l);
	}
	
	/**
	 * 计算整个canvas的高度
	 * @return 返回高度
	 */
	public int measuredCanvasHeight(){
		return MeasuredCanvasHeight;
	}
	
	
	/**
	 * 为Canvas设置一个监听器，当整个canvas的高度被计算出来时被调用
	 */
	public void setOnMeasuredHeightListener(OnMeasuredHeightListener l){
		this.listener = l;
	}
}
