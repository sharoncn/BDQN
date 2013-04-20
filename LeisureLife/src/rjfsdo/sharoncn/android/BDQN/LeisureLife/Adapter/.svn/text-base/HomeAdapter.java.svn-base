package rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter;

import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.HomeDataUtil.HomeData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 主页中使用
 * @author sharoncn
 *
 */
public class HomeAdapter extends BaseAdapter {
	private Context context;
	private List<HomeData> data;

	public HomeAdapter(Context context, List<HomeData> data) {
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		if (data != null) {
			return data.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		if (data != null) {
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
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.tab_item_home, null);
		}
		ImageView image = (ImageView) convertView
				.findViewById(R.id.home_item_iv);
		TextView text = (TextView) convertView.findViewById(R.id.home_item_tv);

		HomeData homeData = data.get(position);

		image.setBackgroundResource(homeData.getResid());
		text.setText(homeData.getName());

		return convertView;
	}

}
