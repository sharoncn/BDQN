package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Adapter;

import java.util.LinkedList;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author sharoncn
 *
 */
public class CustomGridAdapter extends BaseAdapter implements HasLayoutRes {
	private LinkedList<GridDataInfo> data = new LinkedList<GridDataInfo>();
	private static final String KEY_DATASEQUENCE = "dataSequence";
	private static LayoutInflater inflater;
	private static Resources res;
	private static final int layout_res = R.layout.grid_item_layout;
	private static final String TAG = "CustomGridAdapter";
	private static SharedPreferences sp;
	private static Editor editor;

	public CustomGridAdapter(Context context) {
		if (inflater == null)
			inflater = LayoutInflater.from(context);
		if (res == null)
			res = context.getResources();
		if (sp == null)
			sp = PreferenceManager.getDefaultSharedPreferences(context);
		if (editor == null)
			editor = sp.edit();

	}

	public void prepareData(LinkedList<GridDataInfo> data){
		if(data == null){
			throw new IllegalArgumentException("Argument is null!");
		}
		this.data = data;
		toLastSequence();
	}
	
	private void toLastSequence() {
		String sequence = null;
		if (sp.contains(KEY_DATASEQUENCE)) {
			sequence = sp.getString(KEY_DATASEQUENCE, null);
			if (sequence != null) {
				String[] sSq = sequence.split("#");
				final int count = sSq.length;
				LinkedList<GridDataInfo> tmp = new LinkedList<CustomGridAdapter.GridDataInfo>();
				for (int i = 0; i < count; i++) {
					int sIndex = Integer.parseInt(sSq[i]);
					tmp.add(data.get(sIndex));
				}
				data = tmp;
			}
		} else {
			StringBuffer sb = new StringBuffer();
			final int count = data.size();
			for (int i = 0; i < count; i++) {
				sb.append("" + i);
				if (i != count - 1) {
					sb.append("#");
				}
			}
			editor.putString(KEY_DATASEQUENCE, sb.toString());
			editor.commit();
		}

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
			convertView = inflater.inflate(layout_res, null);
		}
		GridDataInfo dataInfo = data.get(position);
		ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
		icon.setBackgroundDrawable(dataInfo.getIcon());
		TextView title = (TextView) convertView.findViewById(R.id.title);
		title.setText(dataInfo.getTitle());
		return convertView;
	}

	// 切换位置
	// Adapter中实际做的只是交换数据的位置
	public void exchange(int pPosition, int cPosition) {
		Log.v(TAG, "pPosition:" + pPosition + "  cPosition:" + cPosition);
		if (cPosition == pPosition) {
			return;
		}
		GridDataInfo tmp = data.get(pPosition);
		data.set(pPosition, data.get(cPosition));
		data.set(cPosition, tmp);

		exchangePreference(pPosition, cPosition);

		this.notifyDataSetChanged();
	}

	private void exchangePreference(int pPosition, int cPosition) {
		String sequence = sp.getString(KEY_DATASEQUENCE, null);
		if (sequence == null) {
			return;
		}
		final String[] sSq = sequence.split("#");
		final StringBuffer sb = new StringBuffer();
		final String tmp = sSq[pPosition];
		sSq[pPosition] = sSq[cPosition];
		sSq[cPosition] = tmp;
		final int count = sSq.length;
		for (int i = 0; i < count; i++) {
			sb.append(sSq[i]);
			if (i != count - 1) {
				sb.append("#");
			}
		}
		editor.putString(KEY_DATASEQUENCE, sb.toString());
		editor.commit();
	}

	@Override
	public int getLayoutRes() {
		return layout_res;
	}

	public static class GridDataInfo {
		private String title;
		private Drawable icon;
		private Drawable dragIcon;
		private Class<?> clazz;

		public GridDataInfo(String title, Drawable icon, Drawable dragIcon, Class<?> clazz) {
			this.title = title;
			this.icon = icon;
			this.dragIcon = dragIcon;
			this.clazz = clazz;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public String getTitle() {
			return title;
		}

		public Drawable getIcon() {
			return icon;
		}

		public Drawable getDragIcon() {
			return dragIcon;
		}

	}
}
