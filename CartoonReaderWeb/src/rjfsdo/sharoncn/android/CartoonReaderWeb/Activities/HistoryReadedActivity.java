package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter.BookAdapter;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter.HistoryAdapter;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.BasicCanvas;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Data.BookInfoDao;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Data.BookInfoDaoImpl;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class HistoryReadedActivity extends Activity {
	private BasicCanvas canvas;
	private int position = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.layout_historyreaded);
		
		canvas = (BasicCanvas) findViewById(R.id.historyreaded_canvas);
		canvas.setHeaderVisibility(View.GONE);
		
		BookInfoDao dao = BookInfoDaoImpl.getInstance(this);
		readedAdapter.prepareData(dao.getReadedBookInfo());
		canvas.setContentAdapter(readedAdapter);
		canvas.setContentOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adpView, View view, int position,
					long id) {
				BookInfo book = (BookInfo) adpView.getAdapter().getItem(position);
				Intent intent = new Intent(HistoryReadedActivity.this,BookDetailActivity.class);
				intent.putExtra("book", book);
				startActivity(intent);
			}
		});
		canvas.setContentOnItemLongClickListener(long_click);
	}

	private BookAdapter readedAdapter = new HistoryAdapter(this);
	
	private OnItemLongClickListener long_click = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> adpView, View view,
				int position, long id) {
			HistoryReadedActivity.this.position = position;
			AlertDialog.Builder alert = new AlertDialog.Builder(HistoryReadedActivity.this);
			alert.setIcon(R.drawable.menu_more)
			.setCancelable(false)
			.setTitle(R.string.collection_delete_title)
			.setMessage(R.string.collection_delete_content)
			.setPositiveButton(R.string.collection_delete_ok, ok_listener)
			.setNegativeButton(R.string.collection_delete_cancel, cancel_listener)
			.show();
			
			return true;
		}
	};
	
	private DialogInterface.OnClickListener ok_listener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if(position == -1){
				return;
			}
			BookInfo book = (BookInfo) readedAdapter.getItem(position);
			BookInfoDao dao = BookInfoDaoImpl.getInstance(HistoryReadedActivity.this);
			dao.setReaded(book.getBookId(), "" + 0);
			readedAdapter.remove(position);
			readedAdapter.notifyDataSetChanged();
		}
	};
	
	private DialogInterface.OnClickListener cancel_listener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
}
