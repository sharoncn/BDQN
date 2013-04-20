package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Adapter;

import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Models.AppPackageInfo;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Util;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AppInstalledAdapter extends BaseAdapter implements OnClickListener {
	private static int layoutRes = R.layout.list_item_appinfo;
	private Context mContext;
	private ArrayList<AppPackageInfo> allData;
	private OnClickListener listener;
	private static LayoutInflater mInflater;
	private static final int POSITION_CARDINAL = 1000000;
	public static final int ID_BTN_OPEN = R.id.app_btn_open;
	public static final int ID_BTN_UNINSTALL = R.id.app_btn_uninstall;

	public AppInstalledAdapter(Context context) {
		this.mContext = context;
		if (mInflater == null) {
			mInflater = LayoutInflater.from(context);
		}
	}

	/**
	 * 准备数据
	 * 
	 * @param data
	 */
	public void prepareData(ArrayList<AppPackageInfo> data) {
		if (data != null)
			this.allData = data;
	}

	@Override
	public int getCount() {
		if (allData != null) {
			return allData.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (allData != null) {
			return allData.get(position);
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
			convertView = mInflater.inflate(layoutRes, null);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.app_icon);
			holder.name = (TextView) convertView.findViewById(R.id.app_name);
			holder.size = (TextView) convertView.findViewById(R.id.app_size);
			holder.ver = (TextView) convertView.findViewById(R.id.app_ver);
			holder.open = (Button) convertView.findViewById(R.id.app_btn_open);
			holder.uninstall = (Button) convertView.findViewById(R.id.app_btn_uninstall);
		}

		AppPackageInfo app = allData.get(position);
		holder.icon.setImageDrawable(app.getIcon());
		holder.name.setText(app.getName());
		holder.size.setText(String.format(mContext.getString(R.string.sizeis), Util.getFormatedSize(app.getSize())));
		holder.ver.setText(String.format(mContext.getString(R.string.veris), app.getVersion()));
		holder.open.setOnClickListener(this);
		holder.open.setTag(POSITION_CARDINAL + position);
		holder.uninstall.setOnClickListener(this);
		holder.uninstall.setTag(POSITION_CARDINAL + position);
		if (app.isSysFlag()) {
			holder.uninstall.setEnabled(true);
		} else {
			holder.uninstall.setEnabled(false);
		}

		convertView.setTag(holder);
		return convertView;
	}

	class ViewHolder {
		public ImageView icon;
		public TextView name, size, ver;
		public Button open, uninstall;
	}

	@Override
	public void onClick(View v) {
		// TODO 如果需要做什么，在这里做
		if (listener != null) {
			listener.onClick(v);
		}
	}

	/**
	 * 为布局中的button设置监听器
	 * 
	 * @param l
	 *            监听器
	 */
	public void setOnClickListener(OnClickListener l) {
		this.listener = l;
	}

	/**
	 * 获取View在List中的位置
	 * 
	 * @param v
	 *            要获取position的view
	 * @return 正确获取返回position，否则返回-1
	 */
	public int getCurrentPosition(View v) {
		final int pTmp = (Integer) v.getTag();
		int position = -1;
		if (v instanceof Button && pTmp >= POSITION_CARDINAL) {
			//list中超过100W行数据的可能性几乎没有吧
			//不超过100W行逻辑就没问题
			position = pTmp % POSITION_CARDINAL;
		}
		return position;
	}

	public void removePositio(int position) {
		allData.remove(position);
		notifyDataSetChanged();
	}
}
