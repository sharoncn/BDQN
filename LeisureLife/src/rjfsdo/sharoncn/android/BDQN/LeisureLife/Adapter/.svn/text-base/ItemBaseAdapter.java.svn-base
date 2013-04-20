package rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter;

import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 列表类Activity中list的通用adapter
 * 
 * @author sharoncn
 * 
 */
public abstract class ItemBaseAdapter extends BaseAdapter {
	private static final String TAG = "ItemBaseAdapter";
	private int layoutResId = R.layout.list_item_default;
	protected Context context;
	private List<Object> data;
	private static View viewMore;
	private static String moreText;

	public ItemBaseAdapter(Context context, List<Object> data) {
		this.context = context;
		moreText = context.getString(R.string.seemore);
		this.data = data;
	}

	public List<Object> getData() {
		return data;
	}

	@Override
	public int getCount() {
		if (data != null) {
			return data.size() + 1;
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (data != null) {
			if (position == data.size()) {
				return moreText;
			} else {
				return data.get(position);
			}
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.v(TAG, "getView position:" + position + "  data.size():" + data.size());

		if (position == data.size()) {
			if (viewMore == null) {
				Log.v(TAG, "更多按钮");
				viewMore = LayoutInflater.from(context).inflate(R.layout.list_item_seemore, null);
			}
			TextView tv = (TextView) viewMore.findViewById(R.id.list_item_tv_seemore);
			if (tv != null)
				tv.setText((String) getItem(position));
			convertView = viewMore;
		} else {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(layoutResId, null);
			} else {
				//测试过程中发现List中更多按钮item显示过程中出现奇怪的现象。convertView实际是记录原来已inflate的
				//view。当点击更多按钮时，如果不手动清理掉那么会出现布局不匹配的显示问题(本应该显示在最下边,却出现在中间)。
				//解决方法:每次convertView不为null的时候，在convertView中查找更多按钮的id如果能找到说明这个布局
				//是不匹配的，重新inflate一次默认的layout
				View view = null;
				try {
					view = convertView.findViewById(R.id.list_item_tv_seemore);
				} catch (Exception e) {}
				if (view != null) {
					convertView = LayoutInflater.from(context).inflate(layoutResId, null);
				}
			}
			initView(convertView, position);
		}

		return convertView;
	}

	public int getLayoutResId() {
		return layoutResId;
	}

	/**
	 * 设置Item布局文件
	 * 
	 * @param layoutResId
	 *            布局文件id
	 */
	public void setLayoutResId(int layoutResId) {
		this.layoutResId = layoutResId;
	}

	/**
	 * 子类需实现此方法以初始化convertView
	 * 
	 * @param view
	 *            需要初始化的view
	 */
	public abstract void initView(View view, int position);
}
