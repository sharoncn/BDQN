package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;
import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter.BookAdapter;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter.DefaultBookAdapter;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.BasicCanvas;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Core.BaseActivity;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookInfo;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Net.Client;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Constants;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Utils;

public class SearchActivity extends BaseActivity {
	public static final String TAG = "SearchActivity";
	private ImageButton btn_search;
	private EditText searchText;
	private BasicCanvas searchCanvas;
	private BookAdapter adapter;
	private List<BookInfo> data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_search);
		add(this);
		
		btn_search = (ImageButton) findViewById(R.id.search_btn);
		btn_search.setOnClickListener(btn_search_listener);
		btn_search.requestFocus();
		
		searchText = (EditText) findViewById(R.id.search_input);
		
		searchCanvas = (BasicCanvas) findViewById(R.id.search_canvas);
		searchCanvas.setHeaderButtonVisibility(View.GONE);
		searchCanvas.setTitleText(R.string.all);
		searchCanvas.setTitleVisibility(View.VISIBLE);
		
		searchCanvas.setContentOnItemClickListener(itemClick);
		
		adapter = new DefaultBookAdapter(this);
		data = prepareAllData();
		adapter.prepareData(data);
		searchCanvas.setContentAdapter(adapter);
	}
	
	//item单击监听器
	private OnItemClickListener itemClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adpView, View view, int position,
				long id) {
			BookInfo book = (BookInfo) adpView.getAdapter().getItem(position);
			Intent intent = new Intent(SearchActivity.this,BookDetailActivity.class);
			intent.putExtra("book", book);
			startActivity(intent);
		}
	};
	
	//搜索按钮监听器
	private View.OnClickListener btn_search_listener = new View.OnClickListener(){
		@Override
		public void onClick(View v) {
			String bookName = searchText.getText().toString();
			if(bookName == null || "".equals(bookName)){
				Utils.showMsg(SearchActivity.this, getString(R.string.search_keyword));
				return;
			}
			List<BookInfo> books = new ArrayList<BookInfo>();
			for(BookInfo book:data){
				if(book.getBookName().equalsIgnoreCase(bookName)){
					books.add(book);
				}else if(book.getClassId().equalsIgnoreCase(bookName)){
					books.add(book);
				}else if(book.getBookName().equalsIgnoreCase(bookName)){
					books.add(book);
				}
			}
			if(books.size() == 0){
				Utils.showMsg(SearchActivity.this, getString(R.string.search_nomatch));
				return;
			}
			adapter.prepareData(books);
			adapter.notifyDataSetChanged();
		}
	};
	
	//准备所有漫画书数据
	private List<BookInfo> prepareAllData() {
		File path = new File(Constants.XMLTEMP);
		File allFile = new File(path,Constants.ALL_FILENAME);
		List<BookInfo> all_data = null;
		if(Utils.sdcardExsits()){
			if(allFile.exists()){
				try {
					return Utils.formatBookInfo(allFile);
				} catch(Exception e){
					e.printStackTrace();
					return new ArrayList<BookInfo>();
				}
			}else{
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("action", "book"));
				params.add(new BasicNameValuePair("type", "all"));//推荐列表
				try {
					String result_all = Client.requestStringContent(Constants.URL_BOOK, params);
					if(result_all != null && result_all != ""){
						all_data = Utils.formatBookInfo(result_all);
						if(!Utils.write2Temp(result_all,Constants.ALL_FILENAME))
							Log.v(TAG, getString(R.string.write_xml_fail) + Constants.ALL_FILENAME);
					}
					return all_data;
				} catch (Exception e) {
					Utils.showMsg(this, getString(R.string.getdata_fail));
					e.printStackTrace();
					if(all_data != null){
						return all_data;
					}
				}
			}
		}
		return new ArrayList<BookInfo>();
	}
	
	@Override
	public String getTag() {
		return TAG;
	}
}
