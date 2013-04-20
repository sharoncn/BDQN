package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter.BookAdapter;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter.BookTypeAdapter;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter.DefaultBookAdapter;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.BasicCanvas;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.ScrollableTextView;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Data.BookInfoDaoImpl;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookInfo;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookType;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow.LayoutParams;

public class BookTypeActivity extends Activity {
	private static final String TAG = "BookTypeActivity";
	private ScrollableTextView tv_news;
	private BasicCanvas typeCanvas,detailCanvas;
	
	@Override
	protected void onResume() {
		typeCanvas.setVisibility(View.VISIBLE);
		detailCanvas.setVisibility(View.GONE);
		super.onResume();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_booktype);
		
		tv_news = (ScrollableTextView) findViewById(R.id.booktype_atv_news);
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
		
		typeCanvas = (BasicCanvas) findViewById(R.id.booktype_canvas);
		typeCanvas.setBackgroundResource(R.drawable.book_bg);
		typeCanvas.setTitleText(R.string.type_toptitle);
		typeCanvas.setTitleVisibility(View.VISIBLE);
		typeCanvas.setHeaderButtonVisibility(View.GONE);
		BookTypeAdapter adapter = new BookTypeAdapter(this);
		
		adapter.prepareData(prepareBookTypeData());
		adapter.setButtonOnClickListener(type_click);
		typeCanvas.setContentAdapter(adapter);
		
		//detail
		detailCanvas = (BasicCanvas) findViewById(R.id.booktype_detail_canvas);
		detailCanvas.setHeaderButtonBackgoundResources(R.drawable.book_bg);
		detailCanvas.setTitleVisibility(View.VISIBLE);
		detailCanvas.setHeaderButtonVisibility(View.VISIBLE);
		detailCanvas.setHeaderButtonBackgoundResources(R.drawable.returnback);
		detailCanvas.setHeaderButtonOnClickListener(returnBack);
		detailCanvas.setVisibility(View.GONE);
	}
	
	private OnClickListener returnBack = new OnClickListener() {
		@Override
		public void onClick(View v) {
			typeCanvas.setVisibility(View.VISIBLE);
			detailCanvas.setVisibility(View.GONE);
		}
	};
	
	private OnClickListener type_click = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String typeCode = (String) v.getTag();
			Log.v(TAG,"typeCode:" + typeCode);
			typeCanvas.setVisibility(View.GONE);
			detailCanvas.setVisibility(View.VISIBLE);
			detailCanvas.setTitleText(((Button)v).getText().toString());
			detailCanvas.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 
					LayoutParams.MATCH_PARENT));
			List<BookInfo> books = BookInfoDaoImpl.getInstance(BookTypeActivity.this).getTypeBookInfo(typeCode);
			BookAdapter adapter = new DefaultBookAdapter(BookTypeActivity.this);
			adapter.prepareData(books);
			detailCanvas.setContentAdapter(adapter);
			detailCanvas.setContentOnItemClickListener(item_click);
		}
	};
	
	private OnItemClickListener item_click = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adpView, View view, int position,
				long id) {
			BookAdapter adp = (BookAdapter) adpView.getAdapter();
			BookInfo book = (BookInfo) adp.getItem(position);
			Intent intent = new Intent(BookTypeActivity.this,BookDetailActivity.class);
			intent.putExtra("book", book);
			startActivity(intent);
		}
	};
	
	private List<BookType> prepareBookTypeData() {
		File path = new File(Constants.XMLTEMP);
		File bookTypeFile = new File(path,Constants.BOOKTYPE_FILENAME);
		List<BookType> bookType_data = null;
		if(Utils.sdcardExsits()){
			if(bookTypeFile.exists()){
				try {
					return Utils.formatBookType(bookTypeFile);
				} catch(Exception e){
					e.printStackTrace();
					return new ArrayList<BookType>();
				}
			}else{
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("action", "type"));
				try {
					String result_BookType = Client.requestStringContent(Constants.URL_TYPE, params);
					if(result_BookType != null && result_BookType != ""){
						bookType_data = Utils.formatBookType(result_BookType);
						if(!Utils.write2Temp(result_BookType,Constants.BOOKTYPE_FILENAME))
							Log.v(TAG, getString(R.string.write_xml_fail) + Constants.BOOKTYPE_FILENAME);
						return bookType_data;
					}
				} catch (Exception e) {
					Utils.showMsg(this, getString(R.string.getdata_fail));
					e.printStackTrace();
					if(bookType_data != null){
						return bookType_data;
					}
				}
			}
		}
		return new ArrayList<BookType>();
	}

	//参见HomeActivity#onKeyDown说明
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

}
