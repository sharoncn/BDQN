package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.BasicCanvas;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.ScrollableTextView;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Core.BaseActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 测试类
 * @author sharoncn
 *
 */
public class TestTabMain extends BaseActivity {
	//private ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.layout_tabmain_test);
		add(this);
		
		final ScrollableTextView atv_tips = (ScrollableTextView) findViewById(R.id.atv_news);
		atv_tips.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					atv_tips.initScrollTextView(getWindowManager(), "到哪了手机发的我风微微我特问题问题为台湾");
					atv_tips.startScroll();
				}else{
					atv_tips.stopScroll();
				}
			}
		});
		
		BasicCanvas flagshipCanvas = (BasicCanvas) findViewById(R.id.container);
		flagshipCanvas.setContentAdapter(new BaseAdapter() {
			
			@Override
			public int getCount() {
				return 1;
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public long getItemId(int position) {
				return 1;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if(convertView == null){
					convertView = LayoutInflater.from(TestTabMain.this).inflate(R.layout.layout_item_readed, null);
				}
				ImageView item_collect_image = (ImageView) convertView.findViewById(R.id.item_collect_image);
				TextView item_collect_readdate = (TextView) convertView.findViewById(R.id.item_collect_readdate);
				TextView item_collect_readpage = (TextView) convertView.findViewById(R.id.item_collect_readpage);
				TextView item_collect_bookname = (TextView) convertView.findViewById(R.id.item_collect_bookname);
				RatingBar item_ratingbar = (RatingBar) convertView.findViewById(R.id.item_collect_ratingbar);

				item_collect_image.setImageResource(R.drawable.nopic);
				item_collect_readdate.setText("2013-01-10 10:23:45");
				item_collect_readpage.setText("最后阅读到：第1页(共16页)");
				item_collect_bookname.setText("火影忍者402话");
				item_ratingbar.setClickable(false);
				item_ratingbar.setRating(4.0f);
				return convertView;
			}
			
		});
//		list = (ListView) findViewById(R.id.list_item);
//		
//		list.setAdapter(new BaseAdapter(){
//			@Override
//			public int getCount() {
//				return 1;
//			}
//
//			@Override
//			public Object getItem(int position) {
//				return null;
//			}
//
//			@Override
//			public long getItemId(int position) {
//				return 1;
//			}
//
//			@Override
//			public View getView(int position, View convertView, ViewGroup parent) {
//				if(convertView == null){
//					convertView = LayoutInflater.from(TabMainActivity.this).inflate(R.layout.layout_item_readed, null);
//				}
//				ImageView item_collect_image = (ImageView) convertView.findViewById(R.id.item_collect_image);
//				TextView item_collect_readdate = (TextView) convertView.findViewById(R.id.item_collect_readdate);
//				TextView item_collect_readpage = (TextView) convertView.findViewById(R.id.item_collect_readpage);
//				TextView item_collect_bookname = (TextView) convertView.findViewById(R.id.item_collect_bookname);
//				RatingBar item_ratingbar = (RatingBar) convertView.findViewById(R.id.item_collect_ratingbar);
//
//				item_collect_image.setImageResource(R.drawable.nopic);
//				item_collect_readdate.setText("2013-01-10 10:23:45");
//				item_collect_readpage.setText("最后阅读到：第1页(共16页)");
//				item_collect_bookname.setText("火影忍者402话");
//				item_ratingbar.setClickable(false);
//				item_ratingbar.setRating(4.0f);
//				return convertView;
//			}
//			
//		});
	}
	
}

/*
ImageView item_image = (ImageView) findViewById(R.id.item_image);
TextView item_date = (TextView) findViewById(R.id.item_date);
TextView item_descreption = (TextView) findViewById(R.id.item_descreption);
TextView item_bookname = (TextView) findViewById(R.id.item_bookname);
RatingBar item_ratingbar = (RatingBar) findViewById(R.id.item_ratingbar);


item_image.setImageResource(R.drawable.nopic);
item_date.setText("2013-01-10 10:23:45");
item_descreption.setText("火影忍者402话xxxx");
item_bookname.setText("火影忍者402话");
item_ratingbar.setClickable(false);
item_ratingbar.setNumStars(4);
*/