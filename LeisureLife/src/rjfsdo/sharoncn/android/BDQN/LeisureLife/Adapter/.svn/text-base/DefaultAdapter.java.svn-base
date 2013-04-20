package rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 非详细信息通用Adapter
 * 为适应所有需求,布局中包含1个ImageView和3个TextView
 * @author sharoncn
 * 
 */
public class DefaultAdapter extends ItemBaseAdapter {
	protected Map<String, String> projection = new HashMap<String, String>();
	protected Map<Integer, String> textHeaders = new HashMap<Integer, String>();
	public static final String VIEW_IMG = "img";
	public static final String VIEW_TXTONE = "txtOne";
	public static final String VIEW_TXTTWO = "txtTwo";
	public static final String VIEW_TXTTHREE = "txtThree";
	public static final String VIEW_TXTFOUR = "txtFour";
	private static final String TAG = "DefaultAdapter";

	public DefaultAdapter(Context context, List<Object> data) {
		super(context, data);
		this.setLayoutResId(R.layout.list_item_default);
	}

	@Override
	public void initView(View view, int position) {
		Log.v(TAG, "initView position:" + position);
		ImageView img = (ImageView) view.findViewById(R.id.list_item_img);
		TextView txtOne = (TextView) view.findViewById(R.id.list_item_text_one);
		TextView txtTwo = (TextView) view.findViewById(R.id.list_item_text_two);
		TextView txtThree = (TextView) view.findViewById(R.id.list_item_text_three);
		TextView txtFour = (TextView) view.findViewById(R.id.list_item_text_four);

		try {
			Object data = this.getItem(position);
			Method method;
			Class<?> clazz = data.getClass();
			Class<?>[] method_params = new Class<?>[] {};
			Object[] params = new Object[] {};
			String methodName = null;
			// 这速度会怎么样？..为什么老爱用反射？晕死了
			// 还好一次性构造的数据并不多
			methodName = projection.get(VIEW_IMG);
			if (methodName != null) {
				method = clazz.getMethod(methodName, method_params);
				String imageId = (String) method.invoke(data, params);
				Log.v(TAG, "设置图片：" + imageId);
				img.setImageDrawable(DataManager.getInstance(context).getImage(imageId));
			}

			methodName = projection.get(VIEW_TXTONE);
			if (methodName != null) {
				method = clazz.getMethod(methodName, method_params);
				String header = textHeaders.get(0);
				header = header == null ? "" : header;
				txtOne.setText(header + (String) method.invoke(data, params));
				setupText(0, txtOne);
			} else {
				txtOne.setVisibility(View.GONE);
			}

			methodName = projection.get(VIEW_TXTTWO);
			if (methodName != null) {
				method = clazz.getMethod(methodName, method_params);
				String header = textHeaders.get(1);
				header = header == null ? "" : header;
				txtTwo.setText(header + (String) method.invoke(data, params));
				setupText(1, txtTwo);
			} else {
				txtTwo.setVisibility(View.GONE);
			}

			methodName = projection.get(VIEW_TXTTHREE);
			if (methodName != null) {
				method = clazz.getMethod(methodName, method_params);
				String header = textHeaders.get(2);
				header = header == null ? "" : header;
				txtThree.setText(header + (String) method.invoke(data, params));
				setupText(2, txtThree);
			} else {
				txtThree.setVisibility(View.GONE);
			}

			methodName = projection.get(VIEW_TXTFOUR);
			if (methodName != null) {
				method = clazz.getMethod(methodName, method_params);
				String header = textHeaders.get(3);
				header = header == null ? "" : header;
				txtFour.setText(header + (String) method.invoke(data, params));
				setupText(3, txtFour);
			} else {
				txtFour.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 如果需要给TextView加一些设置可以重写此方法
	 * @param index    TextView的索引，从0开始，最大为3。对应至上而下的顺序
	 * @param textView 需要设置的TextView
	 */
	public void setupText(int index, TextView textView) {
		// empty
	}

	/**
	 * 设置view初始化的方法映射
	 * 
	 * @param viewName
	 *            view名称 设置为DefaultAdapter.VIEW_IMG等
	 * @param method
	 *            方法名 请注意方法名大小写,如getName
	 */
	public void addProjection(String viewName, String method) {
		projection.put(viewName, method);
	}

	/**
	 * 设置TextView显示内容的头部分，如："地址:","日期:",需要按照布局顺序添加
	 * TextHeader的初始容量为4,index值大于3将被忽略掉
	 * 
	 * @param index
	 *            需要插入的位置
	 * @param header
	 *            TextView显示内容的头部分
	 */
	public void addTextHeader(int index, String header) {
		if (index > 3) {
			return;
		}
		//之前使用ArrayList不太合理。因为在index位置插入，如果为空还好，
		//如果不为空，这将会造成get结果不可预测的问题，所以改为Map。
		textHeaders.put(index, header);
	}
}
