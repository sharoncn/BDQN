package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Adapter;

import java.util.HashMap;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Setter;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * ��ʾ:�����ʹ��������֮ǰΪ��������������ӳ�䡣 Setterͨ�����õĻ�ȡֵ�ķ���������ֵ,ͨ����̬,��װ����ֵ�ı仯�㡣
 * ͨ��setMethodProjectionӳ���Setter,�����б仯���ӳٵ�����ʱ��
 * 
 * @author sharoncn
 * @deprecated
 */
@Deprecated
public class DefaultAdapter extends BasicAdapter {
	private static final String TAG = "DefaultAdapter";
	private static int layoutResId = R.layout.list_item_main;
	private static final int[] ids = new int[] { R.id.img, R.id.who, R.id.auth, R.id.time, R.id.content,
			R.id.reference, R.id.from };
	public static final int ID_IMG = R.id.img;
	public static final int ID_WHO = R.id.who;
	public static final int ID_AUTH = R.id.auth;
	public static final int ID_TIME = R.id.time;
	public static final int ID_CONTENT = R.id.content;
	public static final int ID_REFERENCE = R.id.reference;
	public static final int ID_FROM = R.id.from;
	private HashMap<Integer, Setter> projection = new HashMap<Integer, Setter>();

	// private ArrayList<HashMap<Integer, Object>> usableData = new
	// ArrayList<HashMap<Integer, Object>>();

	public DefaultAdapter(Context context) {
		super(context);
	}

	@Override
	protected View initView(int position, View convertView) {
		Log.v(TAG, "position:" + position);
		if (convertView == null) {
			convertView = inflater.inflate(layoutResId, null);
		} else {
			View view = null;
			try {
				view = convertView.findViewById(R.id.tv_more);
			} catch (Exception e) {
			}
			if (view != null) {
				convertView = inflater.inflate(layoutResId, null);
			}
		}

		final Object item = getItem(position);
		for (int id : ids) {
			final Setter setter = projection.get(id);
			final View view = convertView.findViewById(id);
			setter.set(view, item, position);
		}
		return convertView;
	}

	public void setLayoutRes(int resId){
		layoutResId = resId;
	}
	
	/**
	 * Ϊ�����еĸ������������������Setter
	 * 
	 * @param id
	 *            �����ID
	 * @param setter
	 *            ����������
	 */
	public void addSetterProjection(int id, Setter setter) {
		projection.put(id, setter);
	}

	/**
	 * ��ò��������������ID
	 * 
	 * @return ����ID
	 */
	public int[] getIds() {
		return ids;
	}

	public void setData(List<Object> data) {
		this.data = data;
		// parseUsableData(data);
	}

	// private void parseUsableData(List<Object> data) {
	//
	// final int count = data.size();
	// for(int i = 0;i < count;i++){
	// HashMap<Integer, Object> o = new HashMap<Integer, Object>();
	// Iterator<Integer> keys = projection.keySet().iterator();
	// while(keys.hasNext()){
	// Integer key = keys.next();
	// Setter setter = projection.get(key);
	// String methodName = setter.getMethodName();
	// String[] methodArry = methodName.split("\\.");
	// int _count = methodArry.length;
	// Object result = data.get(i);
	// try {
	// for (int k = 0; k < _count; k++) {
	// Log.i(TAG,"" + methodArry[k]);
	// Method method = result.getClass().getMethod(methodArry[i], new Class<?>[]
	// {});
	// result = method.invoke(result, new Object[] {});
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// o.put(key, result);
	// }
	// usableData.add(o);
	// }
	// }

	public void addData(List<Object> data) {
		this.data.addAll(data);
	}

	public void notifyDataSetChanged(int index) {
		// listView.getFirstVisiblePosition();
		Object item = data.get(index);
		final View view = listView.getChildAt(index);
		if(view == null) return;
		for (int id : getIds()) {
			View child = view.findViewById(id);
			if(child instanceof ImageView){
				final Setter setter = projection.get(id);
				setter.set(child, item, index);
			}
		}
	}

}
