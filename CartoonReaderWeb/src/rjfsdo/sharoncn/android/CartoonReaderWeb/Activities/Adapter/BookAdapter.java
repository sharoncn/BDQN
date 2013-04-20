package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.Animation.ItemAnimationFactory;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.Listener.OnMeasuredHeightListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BookAdapter extends BaseAdapter {
	private static final String TAG = null;
	protected List<?> data = new ArrayList<Object>();
	private Context context;
	private int layout_res;
	private OnMeasuredHeightListener lst = null;
	private ItemAnimationFactory factory;
	protected static final Map<String,Bitmap> imageCache = new HashMap<String,Bitmap>();
	private boolean isPlayAnimation = true;//�Ƿ񲥷Ŷ���
	
	public BookAdapter(Context context,int layout_res){
		this.context = context;
		this.layout_res = layout_res;
		factory = ItemAnimationFactory.getInstance(context, R.anim.item_anim);
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
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(layout_res, null);
		}
		initViews(convertView,position);
		//convertView.measure(0, 0);
		//Log.v(TAG,"getView,MeasuredHeight:" + convertView.getMeasuredHeight());
		//Log.v(TAG,"getView,Height:" + convertView.getHeight());
		int height = convertView.getMeasuredHeight();
		if(height > 0){
			if(lst != null){
				lst.onMeasuredHeight(height);
			}
		}
		if(isPlayAnimation)
		convertView.startAnimation(factory.getAnimation());
		return convertView;
	}
	
	public void remove(int position){
		data.remove(position);
	}

	public void setOnMeasuredHeightListener(OnMeasuredHeightListener l){
		lst = l;
	}
	
	/**
	 * �������ͨ����д�˷����Ի���Զ���Item���ֵ�����
	 */
	protected abstract void initViews(View view,int position);

	/**
	 * Ϊ������׼������
	 * @param data
	 */
	public void prepareData(List<?> data){
		this.data = data;
		Log.v(TAG, "��������" + this.data.size());
	}
	
	/**
	 * �����Ƿ�ΪItem���Ŷ���Ч��,Ĭ���ǲ��Ŷ�����,�������Ҫ����Ч��,������Ϊfalse
	 * @param isPlayAnimation
	 */
	public void setPlayAnimaion(boolean isPlayAnimation){
		this.isPlayAnimation = isPlayAnimation;
	}
}
