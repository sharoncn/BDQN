package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter.BookAdapter;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter.DefaultBookAdapter;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.BasicCanvas;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.ScrollableTextView;
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
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class RecommendActivity extends Activity {
	private static final String TAG = "RecommendActivity";
	private ScrollableTextView tv_news;
	private BasicCanvas canvas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_recommend);
		
		tv_news = (ScrollableTextView) findViewById(R.id.recommend_atv_news);
		tv_news.initScrollTextView(getWindowManager(), getString(R.string.about_body));
		tv_news.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					tv_news.startScroll();
				}else{
					tv_news.stopScroll();
				}
			}
		});
		
		canvas = (BasicCanvas) findViewById(R.id.recommend_canvas);
		canvas.setBackgroundResource(R.drawable.book_bg_tj);
		canvas.setHeaderButtonVisibility(View.GONE);
		BookAdapter adapter = new DefaultBookAdapter(this);
		
		adapter.prepareData(prepareRecommendData());
		canvas.setContentAdapter(adapter);
		canvas.setContentOnItemClickListener(itemClick);
	}
	
	private OnItemClickListener itemClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adpView, View view, int position,
				long id) {
			BookInfo book = (BookInfo) adpView.getAdapter().getItem(position);
			Intent intent = new Intent(RecommendActivity.this,BookDetailActivity.class);
			intent.putExtra("book", book);
			startActivity(intent);
		}
	};
	
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
					return new ArrayList<BookInfo>();
				}
			}else{
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("action", "book"));
				params.add(new BasicNameValuePair("type", "recommend"));//�Ƽ��б�
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
		return new ArrayList<BookInfo>();
	}

	//�μ�HomeActivity#onKeyDown˵��
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
}