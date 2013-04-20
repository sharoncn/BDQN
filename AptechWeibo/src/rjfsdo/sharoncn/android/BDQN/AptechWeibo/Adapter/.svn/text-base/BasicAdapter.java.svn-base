package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Adapter;

import java.util.ArrayList;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Adapter的抽象类，封装了更多按钮的产生和一些基本的信息，子类可以关注于初始化View和其他逻辑
 * @author sharoncn
 *
 */
public abstract class BasicAdapter extends BaseAdapter {
	protected List<Object> data = new ArrayList<Object>();
	protected LayoutInflater inflater;
	protected static final int LAYOUT_MORE = R.layout.list_item_more;
	protected static final int ID_TVMORE = R.id.tv_more;
	private static final String TAG = "BasicAdapter";
	protected Context context;
	private static String more;
	protected static ListView listView;

	public BasicAdapter(Context context) {
		this.context = context;
		more = context.getString(R.string.more);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		int size = data.size();
		Log.v(TAG,"getCount,数据量:" + size);
		return size + 1;
	}

	@Override
	public Object getItem(int position) {
		Log.v(TAG,"getItem");
		if (position == data.size()) {
			return more;
		}
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		Log.v(TAG,"getItemId");
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(listView == null){
			listView = (ListView) parent;
		}
		
		if(position == getCount() - 1){
			final View bottom = inflater.inflate(LAYOUT_MORE, null);
			final TextView more = (TextView) bottom.findViewById(R.id.tv_more);
			more.setText(R.string.more);
			return bottom;
		}else{
			convertView = initView(position, convertView);
		}
		return convertView;
	}

	/**
	 * 初始化convertView中各个View
	 * @param position      item的位置
	 * @param convertView 
	 */
	protected abstract View initView(int position, View convertView);

}
