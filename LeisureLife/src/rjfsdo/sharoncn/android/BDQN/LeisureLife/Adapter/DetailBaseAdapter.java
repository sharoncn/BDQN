package rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter;

import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * 显示详细信息使用的各个DetailAdapter的父类R.layout.list_item_detail_base包含3个LinearLayout。
 * Adapter内部的数据本身有N个，但是getCount只返回1，这样各详细信息Activity只会一条详细信息。
 * 通过设置Adapter的position切换显示其他内容(主要为底部Menu的向前和向后操作提供支持)。
 * @author sharoncn
 *
 */
public abstract class DetailBaseAdapter extends BaseAdapter {
	private static final int LAYOUT_RES = R.layout.list_item_detail_base;
	protected List<Object> data;
	protected int position = 0;
	private Context context;
	
	public DetailBaseAdapter(Context context){
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public Object getItem(int _position) {
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
			convertView = LayoutInflater.from(context).inflate(LAYOUT_RES, null);
		}
		LinearLayout layoutOne = (LinearLayout) convertView.findViewById(R.id.list_item_detail_base_1);
		LinearLayout layoutTwo = (LinearLayout) convertView.findViewById(R.id.list_item_detail_base_2);
		LinearLayout layoutThree = (LinearLayout) convertView.findViewById(R.id.list_item_detail_base_3);
		layoutOne.removeAllViews();
		layoutTwo.removeAllViews();
		layoutThree.removeAllViews();
		View one,two,three;
		one = initLayoutOne(layoutOne.getChildCount() > 0 ? layoutOne.getChildAt(0) : null);
		two = initLayoutTwo(layoutTwo.getChildCount() > 0 ? layoutTwo.getChildAt(0) : null);
		three = initLayoutThree(layoutThree.getChildCount() > 0 ? layoutThree.getChildAt(0) : null);
		if(one != null)layoutOne.addView(one);
		if(two != null)layoutTwo.addView(two);
		if(three != null)layoutThree.addView(three);
		return convertView;
	}

	
	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		if(position < data.size()){
			this.position = position;
		}
	}

	/**
	 * 初始化第一个View容器
	 * @param container
	 * @return
	 */
	public abstract View initLayoutOne(View container);
	/**
	 * 初始化第二个View容器
	 * @param container
	 * @return
	 */
	public abstract View initLayoutTwo(View container);
	/**
	 * 初始化第三个View容器
	 * @param container
	 * @return
	 */
	public abstract View initLayoutThree(View container);
}
