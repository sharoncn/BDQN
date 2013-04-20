package rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter;

import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.FoodShowcase;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Delicacies;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Delicacies.Food;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 美食详情的适配器
 * @author sharoncn
 *
 */
public class DelicaciesDetailAdapter extends DetailBaseAdapter implements OnClickListener {
	public static final int CONTENT_BTN_RESERVE = R.id.list_item_detail_d_reserve;
	public static final int CONTENT_BTN_FIND = R.id.list_item_detail_d_find;
	private static final String TAG = "DelicaciesDetailAdapter";
	private Context context;
	private OnClickListener content_btn_click;
	private DataManager dataManager;
	private FoodShowcase fShowcase;
	private View layoutOne;

	public DelicaciesDetailAdapter(Context context) {
		super(context);
		this.context = context;
		dataManager = DataManager.getInstance(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.w(TAG, parent.getHeight() + "");
		convertView = super.getView(position, convertView, parent);
		Log.w(TAG, convertView.toString());
		convertView.setMinimumHeight(parent.getHeight());
		//convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, parent.getHeight()));
		return convertView;
	}

	@Override
	public View initLayoutOne(View container) {
		if(container == null){
			container = LayoutInflater.from(context).inflate(R.layout.list_item_detail_delicacies_one, null);
		}
		
		final ImageView image = (ImageView) container.findViewById(R.id.list_item_detail_d_img);
		final TextView name = (TextView) container.findViewById(R.id.list_item_detail_d_txtone);
		final Button btnFind = (Button) container.findViewById(R.id.list_item_detail_d_find);
		final Button btnReserve = (Button) container.findViewById(R.id.list_item_detail_d_reserve);
		final Delicacies d = (Delicacies) this.getItem(0);
		image.setImageDrawable(dataManager.getImage(d.getImage()));
		name.setText(d.getName());
		btnFind.setOnClickListener(this);
		btnReserve.setOnClickListener(this);
		layoutOne = container;
		return container;
	}

	@Override
	public View initLayoutTwo(View container) {
		if(container == null){
			container = LayoutInflater.from(context).inflate(R.layout.list_item_detail_delicacies_two, null);
		}
		
		final TextView addr = (TextView) container.findViewById(R.id.list_item_detail_delicacies_two_addr);
		fShowcase = (FoodShowcase) container.findViewById(R.id.foodShowcase);
		final Delicacies d = (Delicacies) this.getItem(0);
		addr.setText(context.getString(R.string.addris) + d.getAddr());
		
		final List<Food> foods = d.getFood();
		final int count = foods.size();
		for(int i = 0;i < count;i ++){
			final Food food = foods.get(i);
			fShowcase.putFood(food, dataManager.getImage(food.getImage() + ""));
		}
		//container.setBackgroundColor(Color.GREEN);
		container.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		
		return container;
	}

	@Override
	public View initLayoutThree(View container) {
		return null;
	}

	/**
	 * 设置预定和查找按钮的监听器
	 * @param l
	 */
	public void setContentButtonOnClickListener(OnClickListener l){
		content_btn_click = l;
	}
	
	@Override
	public void onClick(View v) {
		Log.w(TAG,"DelicaciesDetailAdapter,button onClick");
		switch(v.getId()){
		case R.id.list_item_detail_d_find:
			v.setId(R.id.list_item_detail_d_find);
			break;
		case R.id.list_item_detail_d_reserve:
			v.setId(R.id.list_item_detail_d_reserve);
			break;
		}
		if(content_btn_click != null){
			content_btn_click.onClick(v);
		}
	}

	public void dispatchTouchEvent(MotionEvent ev) {
		//Log.w(TAG,layoutOne.getBottom() + ":" + ev.getY());
		if(ev.getY() > layoutOne.getBottom() + 180){//180为header的高度
			fShowcase.onTouchEvent(ev);
		}
	}
}
