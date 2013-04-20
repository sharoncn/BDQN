package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter.BookAdapter;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter.DefaultBookAdapter;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.BaseCanvas;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.BasicCanvas;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.ScrollableTextView;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.Listener.OnMeasuredHeightListener;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookInfo;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Net.Client;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Constants;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Utils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

public class HomeActivity extends Activity{
	private static final String TAG = "HomeActivity";
	private BasicCanvas flagship_canvas,recommend_canvas,order_canvas,flagship_history_canvas;
	private List<BookInfo> recommend_data_all;//所有推荐数据
	private List<BookInfo> order_data_all;//所有排行数据
	private ScrollableTextView atv_news;
	private TabMainActivity parent;
	
	@Override
	protected void onResume() {
		Log.v(TAG,"HomeActivity onResume");
		flagship_canvas.setHeaderButtonVisibility(View.VISIBLE);
		recommend_canvas.setVisibility(View.VISIBLE);
		order_canvas.setVisibility(View.VISIBLE);
		//当flagship_history_canvas在计算高度之前被设置为GONE，那么将它设置为可见的时候，高度会出现问题，
		//所以onResume第一次调用时不能将flagship_history_canvas设置为GONE。这里使用flagship_history_canvas
		//的高度大于0表示第一次程序启动时的onResume方法已经执行过了。再次执行onResume方法时，canvas的高度已经
		//被计算出来了，并正确设置了LayoutParams，这时可以将它设置为GONE了。
		//结论：当View的可见性是GONE的时候设置它的LayoutParams是无效的
		if(flagship_history_canvas.getHeight() > 0){
			flagship_history_canvas.setVisibility(View.GONE);
		}
		super.onResume();
	}

	@Override
	protected void onRestart() {
		Log.v(TAG,"HomeActivity onRestart");
		super.onRestart();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_home);
		Log.v(TAG,"HomeActivity onCreate");
		//获得各个Canvas
		atv_news = (ScrollableTextView) findViewById(R.id.atv_news);
		atv_news.initScrollTextView(getWindowManager(),getString(R.string.about_body));
		atv_news.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					atv_news.startScroll();
				}else{
					atv_news.stopScroll();
				}
			}
		});
		atv_news.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v(TAG,"文本框被点击,有焦点吗：" + v.hasFocus());
			}
		});
		//主打
		flagship_canvas = (BasicCanvas) findViewById(R.id.flagship_canvas);
		flagship_canvas.setBackgroundResource(R.drawable.book_bg_zd);
		flagship_canvas.setHeaderButtonOnClickListener(flagship_button_lst);
		flagship_canvas.setContentOnItemClickListener(itemClickListener);
		//热门推荐
		recommend_canvas = (BasicCanvas) findViewById(R.id.recommend_canvas);
		recommend_canvas.setBackgroundResource(R.drawable.book_bg_tj);
		recommend_canvas.setHeaderButtonOnClickListener(recommend_button_lst);
		recommend_canvas.setContentOnItemClickListener(itemClickListener);
		//热门排行
		order_canvas = (BasicCanvas) findViewById(R.id.order_canvas);
		order_canvas.setBackgroundResource(R.drawable.book_bg_ph);
		order_canvas.setHeaderButtonOnClickListener(order_button_lst);
		order_canvas.setContentOnItemClickListener(itemClickListener);
		//历史主打
		flagship_history_canvas = (BasicCanvas) findViewById(R.id.flagship_history_canvas);
		flagship_history_canvas.setBackgroundResource(R.drawable.book_bg);
		flagship_history_canvas.setHeaderButtonVisibility(View.GONE);
		flagship_history_canvas.setTitleText(R.string.book_title_lszd);
		flagship_history_canvas.setTitleVisibility(View.VISIBLE);
		flagship_history_canvas.setContentOnItemClickListener(itemClickListener);
		
		//本期主打就是热门推荐列表的第一个
		recommend_data_all = prepareRecommendData();//推荐的所有数据
		order_data_all = prepareOrderData();//热门点击所有数据
		
		//Log.v(TAG,"重设高度");
		recommend_canvas.setOnMeasuredHeightListener(new MeasuredHeightAdapter(recommend_canvas));
		order_canvas.setOnMeasuredHeightListener(new MeasuredHeightAdapter(order_canvas));
		flagship_history_canvas.setOnMeasuredHeightListener(new MeasuredHeightAdapter(flagship_history_canvas));
		
		//为各个Canvas设置ContentAdapter
		flagship_canvas.setContentAdapter(getFlagshipAdapter(recommend_data_all));
		recommend_canvas.setContentAdapter(getRecommendAdapter(recommend_data_all));
		order_canvas.setContentAdapter(getOrderAdapter(order_data_all));
		flagship_history_canvas.setContentAdapter(getFlagshipHistoryAdapter(recommend_data_all));

		//拿到此Activity的父容器(TabMainActivity)
		parent = (TabMainActivity) this.getParent();
		
	}
	
	class MeasuredHeightAdapter implements OnMeasuredHeightListener{
		private BaseCanvas canvas;
		
		public MeasuredHeightAdapter(BaseCanvas canvas){
			this.canvas = canvas;
		}
		
		@Override
		public void onMeasuredHeight(int height) {
			Log.v(TAG,"canvas onMeasuredHeight:" + height);
			canvas.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, height));
			if(canvas.equals(flagship_history_canvas)){
				//当计算完Canvas的高度之后将flagship_history_canvas设置为隐藏
				flagship_history_canvas.setVisibility(View.GONE);
			}
			//canvas.invalidate();
		}
	}

	//监听器
	private OnClickListener flagship_button_lst = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.v(TAG,"more is clicked");
			//parent.getCurrentTabHost();
			flagship_canvas.setHeaderButtonVisibility(View.GONE);
			recommend_canvas.setVisibility(View.GONE);
			order_canvas.setVisibility(View.GONE);
			flagship_history_canvas.setVisibility(View.VISIBLE);
		}
	}; 
	
	private OnClickListener recommend_button_lst = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.v(TAG,"more is clicked");
			parent.getCurrentTabHost().setCurrentTab(1);
		}
	}; 
	
	private OnClickListener order_button_lst = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.v(TAG,"more is clicked");
			parent.getCurrentTabHost().setCurrentTab(2);
		}
	}; 
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {
			BookAdapter adp = (BookAdapter) adapter.getAdapter();
			BookInfo book = (BookInfo) adp.getItem(position);
			Intent intent = new Intent(HomeActivity.this,BookDetailActivity.class);
			intent.putExtra("book", book);
			startActivity(intent);
		}
	};
	//监听器结束
	
	//准备推荐数据
	private List<BookInfo> prepareRecommendData() {
		File path = new File(Constants.XMLTEMP);
		File recommendFile = new File(path,Constants.RECOMMEND_FILENAME);
		List<BookInfo> recommend_data = null;
		if(Utils.sdcardExsits()){
			if(recommendFile.exists()){
				try {
					return Utils.formatBookInfo(recommendFile);
				} catch(Exception e){
					e.printStackTrace();
					return null;
				}
			}else{
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("action", "book"));
				params.add(new BasicNameValuePair("type", "recommend"));//推荐列表
				try {
					String result_recommend = Client.requestStringContent(Constants.URL_BOOK, params);
					if(result_recommend != null && result_recommend != ""){
						recommend_data = Utils.formatBookInfo(result_recommend);
						if(!Utils.write2Temp(result_recommend,Constants.RECOMMEND_FILENAME))
							Log.v(TAG, getString(R.string.write_xml_fail) + Constants.RECOMMEND_FILENAME);
						return recommend_data;
					}
				} catch (Exception e) {
					Utils.showMsg(this, getString(R.string.getdata_fail));
					e.printStackTrace();
					if(recommend_data != null){
						return recommend_data;
					}
				}
			}
		}
		return null;
	}

	//准备排行数据
	private List<BookInfo> prepareOrderData() {
		File path = new File(Constants.XMLTEMP);
		File recommendFile = new File(path,Constants.ORDER_FILENAME);
		List<BookInfo> order_data = null;
		if(Utils.sdcardExsits()){
			if(recommendFile.exists()){
				try {
					return Utils.formatBookInfo(recommendFile);
				} catch(Exception e){
					e.printStackTrace();
					return null;
				}
			}else{
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("action", "book"));
				params.add(new BasicNameValuePair("type", "hitnums"));//热门排行
				try {
					String result_order = Client.requestStringContent(Constants.URL_BOOK, params);
					if(result_order != null && result_order != ""){
						order_data = Utils.formatBookInfo(result_order);
						if(!Utils.write2Temp(result_order,Constants.ORDER_FILENAME))
							Log.v(TAG, getString(R.string.write_xml_fail) + Constants.ORDER_FILENAME);
						return order_data;
					}
				} catch (Exception e) {
					Utils.showMsg(this, getString(R.string.getdata_fail));
					e.printStackTrace();
					if(order_data != null){
						return order_data;
					}
				}
			}
		}
		return null;
	}
	
	//本期主打数据
	private BookAdapter getFlagshipAdapter(List<BookInfo> data){
		//Log.v(TAG,"本期主打数据:" + data.size());
		List<BookInfo> flagship_data = new ArrayList<BookInfo>();
		BookAdapter flagship_adapter = new DefaultBookAdapter(this);
		if(data != null && data.size() > 0){
			flagship_data.add(data.get(0));
		}else{
			Utils.showMsg(this, getString(R.string.getdata_fail));
		}
		flagship_adapter.prepareData(flagship_data);
		return flagship_adapter;
	}
	
	//本期主打数据
	private BookAdapter getFlagshipHistoryAdapter(List<BookInfo> data){
		//Log.v(TAG,"本期主打数据:" + data.size());
		List<BookInfo> flagship_history_data = new ArrayList<BookInfo>();
		BookAdapter flagship_history_adapter = new DefaultBookAdapter(this);
		if(data != null && data.size() > 1){
			for(int i = 1;i < data.size();i++){
				flagship_history_data.add(data.get(i));
			}
			flagship_history_data.add(data.get(0));
		}else{
			Utils.showMsg(this, getString(R.string.getdata_fail));
		}
		flagship_history_adapter.prepareData(flagship_history_data);
		return flagship_history_adapter;
	}
	
	//热门推荐需要的数据
	private BookAdapter getRecommendAdapter(List<BookInfo> data){
		//Log.v(TAG,"热门推荐需要的数据:" + data.size());
		List<BookInfo> recommend_data = new ArrayList<BookInfo>();
		BookAdapter recommend_adapter = new DefaultBookAdapter(this);
		if(data != null && data.size() >= 4){
			recommend_data.add(data.get(1));
			recommend_data.add(data.get(2));
			recommend_data.add(data.get(3));
		}else{
			Utils.showMsg(this, getString(R.string.getdata_fail));
		}
		recommend_adapter.prepareData(recommend_data);
		return recommend_adapter;
	}
	
	//热门排行需要的数据
	private BookAdapter getOrderAdapter(List<BookInfo> data){
		List<BookInfo> order_data = new ArrayList<BookInfo>();
		//Log.v(TAG,"热门排行需要的数据:" + data.size());
		BookAdapter order_adapter = new DefaultBookAdapter(this);
		if(data != null && data.size() >= 3){
			order_data.add(data.get(0));
			order_data.add(data.get(1));
			order_data.add(data.get(2));
		}else{
			Utils.showMsg(this, getString(R.string.getdata_fail));
		}
		order_adapter.prepareData(order_data);
		return order_adapter;
	}
	
	//暂时发现，TabMainActivity中按返回键，onkeydown事件会被HomeActivity直接处理并无法传递到BaseActivity中
	//因为BaseActivity现在继承自ActivityGroup，所以按键事件直接被BaseActivity内部的子activity处理了，无法传递到
	//BaseActivity(ActivityGroup)中，现在还为测试继承自BaseActivity得子类Activity会不会出现这个问题（应该是不会出现），明天继续再验证吧
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
	
}
