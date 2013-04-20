package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Models.FileInfo;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class FileAdapter extends BaseAdapter implements OnCheckedChangeListener {
	private static final String TAG = "FileAdapter";
	private List<FileInfo> mData;
	private int layoutRes;
	protected static LayoutInflater mInflater;
	private int checkBoxVisibility = View.GONE;
	private ArrayList<HashMap<String, String>> checked = new ArrayList<HashMap<String, String>>();
	protected HashMap<Integer, Boolean> checkedState = new HashMap<Integer, Boolean>();
	private OnSetSingleCheckBoxVisibilityListener cbVisiableListener;
	private OnCheckedChangeListener checkedChange;
	private static final String FLAG_CHECKED_ID = "id";
	private static final String FLAG_CHECKED_PATH = "path";

	public FileAdapter(Context context, int layoutRes, List<FileInfo> data) {
		this.mData = data;
		this.layoutRes = layoutRes;
		if (mInflater == null)
			mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(layoutRes, null);
		}
		holder = (ViewHolder) convertView.getTag();

		if (holder == null) {
			holder = new ViewHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.img);
			holder.title = (TextView) convertView.findViewById(R.id.filename);
			holder.cBox = (CheckBox) convertView.findViewById(R.id.select);
			holder.size = (TextView) convertView.findViewById(R.id.filesize);
			holder.lastdate = (TextView) convertView.findViewById(R.id.lastmodifydate);
			convertView.setTag(holder);
		}

		holder.img.setBackgroundResource(mData.get(position).getIcon());
		holder.title.setText(mData.get(position).getName());
		holder.size.setText(mData.get(position).getSize());
		holder.lastdate.setText(mData.get(position).getDate());
		holder.cBox.setId(position);

		holder.cBox.setVisibility(checkBoxVisibility);
		if (cbVisiableListener != null) {
			int v = cbVisiableListener.OnSetSingleCheckBoxVisibility(position);
			if (v != checkBoxVisibility) {
				holder.cBox.setVisibility(v);
			}
		}

		if (checkedState.containsKey(position)) {
			holder.cBox.setChecked(true);
		} else {
			holder.cBox.setChecked(false);
		}
		holder.cBox.setOnCheckedChangeListener(this);
		return convertView;

	}

	public void setOnSetSingleCheckBoxVisibilityListener(OnSetSingleCheckBoxVisibilityListener l) {
		this.cbVisiableListener = l;
	}

	public void setOnCheckedChangedListener(OnCheckedChangeListener l) {
		checkedChange = l;
	}

	/**
	 * 设置CheckBox的可见性
	 * 
	 * @param visibility
	 */
	public void setCheckBoxVisibility(int visibility) {
		checkBoxVisibility = visibility;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		int id = buttonView.getId();
		String path = mData.get(id).getPath();
		if (isChecked) {
			checkedState.put(buttonView.getId(), true);
			final HashMap<String, String> check = new HashMap<String, String>();
			check.put(FLAG_CHECKED_ID, "" + id);
			check.put(FLAG_CHECKED_PATH, path);
			checked.add(check);
		} else {
			checkedState.remove(buttonView.getId());
			for (int i = 0; i < checked.size(); i++) {
				if (checked.get(i).get(FLAG_CHECKED_PATH) == path) {
					Log.v(TAG, "UnChecked  " + path);
					checked.remove(i);
				}
			}
		}
		if (checkedChange != null) {
			checkedChange.onCheckedChanged(buttonView, isChecked);
		}
	}

	public class ViewHolder {
		public ImageView img;
		public TextView title;
		public CheckBox cBox;
		public TextView size;
		public TextView lastdate;
	}

	public ArrayList<HashMap<String, String>> getCheckedItems() {
		return checked;
	}

	public void checkedAll() {

	}
}
