package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Adapter;

import java.util.ArrayList;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Db.PrivacyModel;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Models.AppPackageInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PrivacyAppsAdapter extends BaseAdapter implements OnClickListener {
	private List<AppPackageInfo> data;
	private static int layoutRes = R.layout.list_item_privacy;
	private static LayoutInflater mInflater;
	private static Context mContext;
	private ArrayList<Boolean> privacy = new ArrayList<Boolean>();

	public PrivacyAppsAdapter(Context context) {
		if (mContext == null)
			mContext = context;
		if (mInflater == null)
			mInflater = LayoutInflater.from(context);
	}

	public void prepareData(List<AppPackageInfo> data) {
		if (data == null) {
			throw new IllegalArgumentException("Data is null");
		}
		this.data = data;
		final int count = data.size();
		privacy.clear();
		ArrayList<String> watched = PrivacyModel.getInstance(mContext, mContext.getContentResolver()).findAll();
		for (int i = 0; i < count; i++) {
			privacy.add(watched.contains(data.get(i).getPackageName()));
		}
	}

	/**
	 * 设置保护状态
	 * 
	 * @param index
	 * @param isPrivacy
	 */
	public void setPrivacy(int index, boolean isPrivacy) {
		if (index >= privacy.size()) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		privacy.set(index, isPrivacy);
	}

	/**
	 * 获得保护状态
	 * 
	 * @param index
	 * @return
	 */
	public boolean getPrivacy(int index) {
		if (index >= privacy.size()) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		return privacy.get(index);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
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
			holder.icon = (ImageView) convertView.findViewById(R.id.privacy_icon);
			holder.text = (TextView) convertView.findViewById(R.id.privacy_text);
			holder.lock = (ImageView) convertView.findViewById(R.id.privacy_lock);
			holder.lock.setOnClickListener(this);
			convertView.setTag(holder);
		}
		final AppPackageInfo app = data.get(position);
		holder.icon.setBackgroundDrawable(app.getIcon());
		holder.text.setText(app.getName());
		holder.lock.setTag(position);
		setLockState(holder.lock, position);
		return convertView;
	}

	private void setLockState(View v, int position) {
		v.setBackgroundResource(privacy.get(position) ? R.drawable.app_lock : R.drawable.app_unlock);
	}

	private int currentPostion = 0;
	private OnClickListener lockListener;

	/**
	 * 设置Lock监听器
	 * 
	 * @param l
	 */
	public void setLockOnClickListener(OnClickListener l) {
		this.lockListener = l;
	}

	/**
	 * 获得当前点击的Lock所在的position
	 * 
	 * @return
	 */
	public int getCurrentPosition() {
		return currentPostion;
	}

	@Override
	public void onClick(View v) {
		currentPostion = (Integer) v.getTag();
		privacy.set(currentPostion, !privacy.get(currentPostion));
		setLockState(v, currentPostion);
		if (lockListener != null) {
			lockListener.onClick(v);
		}
	}

	/**
	 * 查询是否处于保护状态
	 * 
	 * @param position
	 * @return
	 */
	public boolean isProtected(int position) {
		if (position >= privacy.size() || position < 0) {
			return false;
		}
		return privacy.get(position);
	}

	class ViewHolder {
		public ImageView icon;
		public TextView text;
		public ImageView lock;
	}
}
