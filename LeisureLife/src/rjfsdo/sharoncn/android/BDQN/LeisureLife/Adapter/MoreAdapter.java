package rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter;

import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 主页面，"更多"中使用
 * @author sharoncn
 *
 */
public class MoreAdapter extends BaseAdapter {
	private Context context;
	private List<Integer> data;
	
	public MoreAdapter(Context context,List<Integer> data){
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		if(data != null){
			return data.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if(data != null){
			return data.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item_more, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.list_item_tv);
		tv.setText(context.getString(data.get(position)));
		return convertView;
	}

}
