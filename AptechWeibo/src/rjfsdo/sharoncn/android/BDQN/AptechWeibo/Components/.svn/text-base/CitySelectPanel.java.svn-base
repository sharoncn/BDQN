package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.ProvincesProvider;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.City;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Province;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 其实并不需要在初始化时确定Panel内的item数量。 即使需要在初始化时确定，也可以自定义style属性解决。
 * 但是最开始的想法是这样，需求也只有这么多。 不考虑那么多的变化了。
 * 
 * @author sharoncn
 * 
 */
public final class CitySelectPanel extends OneColumnPanel implements OnItemSelectedListener {
	private ArrayList<Spinner> spinners = new ArrayList<Spinner>();
	private static final int PROVINCE = 0;
	private static final int CITY = 1;
	private static final String TAG = "CitySelectPanel";
	private ProvincesAdapter pAdapter;
	private CityAdapter cAdapter;
	private ProvincesProvider pProvider;
	private LayoutInflater inflater;

	public CitySelectPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		pProvider = ProvincesProvider.getInstance(context);
		pAdapter = new ProvincesAdapter();
		cAdapter = new CityAdapter();
		inflater = LayoutInflater.from(context);
		initViews();
	}

	private void initViews() {
		View view = inflater.inflate(R.layout.panel_simple_info_spinner, null);
		TextView tv_title = (TextView) view.findViewById(R.id.panel_title);
		Spinner spinner = (Spinner) view.findViewById(R.id.panel_content);
		tv_title.setText(mContext.getString(R.string.provinceis));
		
		pAdapter.setData(pProvider.getProvinces());
		spinner.setAdapter(pAdapter);
		spinner.setOnItemSelectedListener(this);
		spinners.add(spinner);
		
		this.addItem(view);

		view = inflater.inflate(R.layout.panel_simple_info_spinner, null);
		tv_title = (TextView) view.findViewById(R.id.panel_title);
		tv_title.setText(mContext.getString(R.string.cityis));
		
		spinner = (Spinner) view.findViewById(R.id.panel_content);
		spinner.setAdapter(cAdapter);
		spinners.add(spinner);
		
		this.addItem(view);
	}

	public void setCurrentProvince(int provinceId) {
		Log.i(TAG,"provinceId:" + provinceId);
		int index = 0;
		int count = pAdapter.getCount();
		Province p;
		for(int i = 0; i < count;i++){
			p = (Province) pAdapter.getItem(i);
			Log.i(TAG,"相比较的ID:" + p.getId());
			if(provinceId == p.getId()){
				index = i;
				break;
			}
		}
		spinners.get(PROVINCE).setSelection(index);
	}
	
	public void setCurrentCity(String cityId) {
		Log.i(TAG,"provinceId:" + cityId);
		int index = 0;
		int count = cAdapter.getCount();
		City c;
		for(int i = 0; i < count;i++){
			c = (City) cAdapter.getItem(i);
			Log.i(TAG,"相比较的ID:" + c.getId());
			if(cityId.equals(c.getId())){
				index = i;
				break;
			}
		}
		spinners.get(CITY).setSelection(index);
	}

	public String getSelectedProvince() {
		Spinner spinner = spinners.get(PROVINCE);
		Province p = (Province) spinner.getAdapter().getItem(spinner.getSelectedItemPosition());
		return p.getName();
	}

	public String getSelectedCity() {
		Spinner spinner = spinners.get(CITY);
		City p = (City) spinner.getAdapter().getItem(spinner.getSelectedItemPosition());
		return p.getName();
	}

	class ProvincesAdapter extends BaseAdapter {
		private ArrayList<Province> data = new ArrayList<Province>();

		@Override
		public int getCount() {
			return data.size();
		}

		public void setData(ArrayList<Province> provinces) {
			this.data = provinces;
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
			ViewHolder holder;
			if(convertView == null){
				convertView = inflater.inflate(R.layout.spinner_item_simple, null);
			}
			holder = (ViewHolder) convertView.getTag();
			if(holder == null){
				holder = new ViewHolder();
				holder.tv = (TextView) convertView.findViewById(R.id.spinner_content);
			}
			holder.tv.setText(data.get(position).getName());
			convertView.setTag(holder);
			return convertView;
		}

	}

	class ViewHolder{
		public TextView tv;
	}
	
	class CityAdapter extends BaseAdapter {
		private ArrayList<City> data = new ArrayList<City>();

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
			ViewHolder holder;
			if(convertView == null){
				convertView = inflater.inflate(R.layout.spinner_item_simple, null);
			}
			holder = (ViewHolder) convertView.getTag();
			if(holder == null){
				holder = new ViewHolder();
				holder.tv = (TextView) convertView.findViewById(R.id.spinner_content);
			}
			holder.tv.setText(data.get(position).getName());
			convertView.setTag(holder);
			return convertView;
		}

		public void setData(ArrayList<City> data) {
			this.data = data;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> adpView, View view, int position, long id) {
		CityAdapter cityAdapter = (CityAdapter) spinners.get(CITY).getAdapter();
		Province p = (Province) adpView.getAdapter().getItem(position);
		cityAdapter.setData(p.getCitys());
		cityAdapter.notifyDataSetChanged();
	}

	@Override
	public void onNothingSelected(AdapterView<?> adpView) {
		// doNothing
	}

}
