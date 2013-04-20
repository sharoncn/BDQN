package rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter;

import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Recommend;
import android.content.Context;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * ∆¿¬€ƒ⁄»›  ≈‰∆˜
 * @author sharoncn
 *
 */
public class CommentAdapter extends BaseAdapter {
	//private static final String TAG = "CommentAdapter";
	private List<Recommend> data;
	private Context context;
	
	public CommentAdapter(Context context,List<Recommend> data){
		this.data = data;
		this.context = context;
	}

	
	@Override
	public int getCount() {
		if(data != null){
			return data.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
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
		//Log.i(TAG,"getView");
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item_recommend_item, null);
		}
		
		TextView tv_who = (TextView) convertView.findViewById(R.id.list_item_recommend_who);
		TextView tv_content = (TextView) convertView.findViewById(R.id.list_item_recommend_content);
		Recommend r = data.get(position);
		
		tv_who.setText(r.getName() + "   " + r.getTime());
		tv_content.setText(r.getContent());
		
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		convertView.measure(w, h);
		
		return convertView;
	}


	public List<Recommend> getData() {
		return data;
	}
	
}
