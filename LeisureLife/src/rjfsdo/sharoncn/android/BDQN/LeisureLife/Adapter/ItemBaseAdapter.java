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
 * �б���Activity��list��ͨ��adapter
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
				Log.v(TAG, "���ఴť");
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
				//���Թ����з���List�и��ఴťitem��ʾ�����г�����ֵ�����convertViewʵ���Ǽ�¼ԭ����inflate��
				//view����������ఴťʱ��������ֶ��������ô����ֲ��ֲ�ƥ�����ʾ����(��Ӧ����ʾ�����±�,ȴ�������м�)��
				//�������:ÿ��convertView��Ϊnull��ʱ����convertView�в��Ҹ��ఴť��id������ҵ�˵���������
				//�ǲ�ƥ��ģ�����inflateһ��Ĭ�ϵ�layout
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
	 * ����Item�����ļ�
	 * 
	 * @param layoutResId
	 *            �����ļ�id
	 */
	public void setLayoutResId(int layoutResId) {
		this.layoutResId = layoutResId;
	}

	/**
	 * ������ʵ�ִ˷����Գ�ʼ��convertView
	 * 
	 * @param view
	 *            ��Ҫ��ʼ����view
	 */
	public abstract void initView(View view, int position);
}
