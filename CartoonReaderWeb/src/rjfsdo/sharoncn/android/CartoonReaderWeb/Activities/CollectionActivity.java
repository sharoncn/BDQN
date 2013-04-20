package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter.DefaultBookAdapter;
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

public class CollectionActivity extends Activity {
	private BasicCanvas canvas;
	private int position = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.layout_collection);
		
		canvas = (BasicCanvas) findViewById(R.id.collection_canvas);
		canvas.setHeaderVisibility(View.GONE);
		
		BookInfoDao dao = BookInfoDaoImpl.getInstance(this);
		collectionAdapter.prepareData(dao.getCollection());
		canvas.setContentAdapter(collectionAdapter);
		canvas.setContentOnItemClickListener(item_click);
		canvas.setContentOnItemLongClickListener(item_long_click);
	}

	private DefaultBookAdapter collectionAdapter = new DefaultBookAdapter(this);
	
	private OnItemClickListener item_click = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adpView, View view, int position,
				long id) {
			BookInfo book = (BookInfo) adpView.getAdapter().getItem(position);
			Intent intent = new Intent(CollectionActivity.this,BookDetailActivity.class);
			intent.putExtra("book", book);
			startActivity(intent);
		}
	};
	
	private OnItemLongClickListener item_long_click = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> adpView, View view,
				int position, long id) {
			CollectionActivity.this.position = position;
			AlertDialog.Builder alert = new AlertDialog.Builder(CollectionActivity.this);
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
			BookInfo book = (BookInfo) collectionAdapter.getItem(position);
			BookInfoDao dao = BookInfoDaoImpl.getInstance(CollectionActivity.this);
			dao.setCollection(book.getBookId(), "" + 0);
			collectionAdapter.remove(position);
			collectionAdapter.notifyDataSetChanged();
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
