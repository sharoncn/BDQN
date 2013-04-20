package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter.BookDetailAdapter;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.BasicCanvas;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Core.BaseActivity;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Data.BookInfoDao;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Data.BookInfoDaoImpl;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookInfo;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Constants;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Utils;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class BookDetailActivity extends BaseActivity {
	public static final String TAG = "BookDetailActivity";
	private BasicCanvas canvas;
	private BookDetailAdapter adapter;
	private BookInfoDao dao;
	private ProgressDialog pd;
	private BookInfo downloadBook;//��Ҫ���ص���
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.getData().containsKey("msg")){
				String message = msg.getData().getString("msg");
				Utils.showMsg(BookDetailActivity.this, message);
			}
			super.handleMessage(msg);
		}
	};//����ΪDownloadThread��ʾToast��ʾ
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bookdetail);
		add(this);
		
		this.setHeaderVisibility(View.GONE);
		canvas = (BasicCanvas) findViewById(R.id.bookdetail_canvas);
		canvas.setHeaderButtonBackgoundResources(R.drawable.returnback);
		canvas.setHeaderButtonOnClickListener(returnBackListener);
		canvas.setTitleVisibility(View.VISIBLE);
		canvas.hideDivider();
		
		//�õ����ݹ�����BookInfo����
		Intent in = getIntent();
		Bundle bundle = in.getExtras();
		BookInfo book = (BookInfo) bundle.getSerializable("book");
		
		adapter = new BookDetailAdapter(this);
		List<BookInfo> data = new ArrayList<BookInfo>();
		data.add(book);
		adapter.prepareData(data);
		adapter.setCollectionOnClickListener(collectionListener);
		adapter.setReadOnClickListener(readListener);
		canvas.setContentAdapter(adapter);
		canvas.setTitleText(book.getClassName());
		dao = BookInfoDaoImpl.getInstance(this);
	}
	
	//���ذ�ť������
	private View.OnClickListener returnBackListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			remove(BookDetailActivity.this);
			finish();
		}
	};
	
	//�ղذ�ť������
	private View.OnClickListener collectionListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			BookInfo book = (BookInfo) adapter.getItem((Integer)v.getTag());
			if(!dao.isRecordExists(book.getBookId())){
				if(dao.saveBookInfo(book) == -1){
					Utils.showMsg(BookDetailActivity.this, getString(R.string.bookInfo_collection_fail));
				}
			}else{
				Log.i(TAG,"bookId:" + book.getBookId());
				if(dao.setCollection(book.getBookId(),"" + 1)){
					Utils.showMsg(BookDetailActivity.this, getString(R.string.bookInfo_collection_success));
				}else{
					Utils.showMsg(BookDetailActivity.this, getString(R.string.bookInfo_collection_fail));
				}
			}
		}
	};
	
	//�Ķ���ť������
	private View.OnClickListener readListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			BookInfo book = (BookInfo) adapter.getItem((Integer)v.getTag());
			downloadBook = book;
			dao.saveBookInfo(book);
			book = dao.getBookInfo(book.getBookId());
			if(needDownLoad(book)){
				AlertDialog.Builder builder = new AlertDialog.Builder(BookDetailActivity.this);
				builder.setCancelable(false)
				.setIcon(R.drawable.menu_more)
				.setTitle(R.string.download_title)
				.setPositiveButton(R.string.collection_delete_ok, ok_click)
				.setNegativeButton(R.string.cancel, cancel_click)
				.show();
			}else{
				viewBook(book);
			}
		}
	};
	
	//����
	private void viewBook(BookInfo book){
		dao.setReaded(book.getBookId(),"" + 1);
		String zipPath = BookInfoDaoImpl.getInstance(this).getUnZipPath(Integer.parseInt(book.getBookId()));
		String imagePath = zipPath.substring(0, zipPath.lastIndexOf(".") - 1);
		Log.i(TAG, "�Ķ�·����" + imagePath);
		//�鿴����
		String lastReadPage = book.getLastReadedPage();
		Log.i(TAG, "����Ķ�ҳ����" + lastReadPage);
		int position = 0;
		if(lastReadPage != null){
			position = Integer.parseInt(lastReadPage);
		}
		String picPath = Utils.getImagePath(imagePath,position);
		Log.i(TAG, "�Ķ�ͼƬ·����" + picPath);
		if(picPath != null){
			Intent intent = new Intent(this,rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.MainActivity.class);
			intent.putExtra("picPath", picPath);
			intent.putExtra("bookId", book.getBookId());
			startActivity(intent);
			finish();//����
		}else{
			//��������²�Ӧ�ô���ΪNull�����
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putString("msg", getString(R.string.get_image_err));
			msg.setData(data);
			handler.sendMessage(msg);
		}
	}
	
	/**
	 * ȷ���Ƿ�����
	 * @param book
	 * @return
	 */
	private boolean needDownLoad(BookInfo book){
		String zipFilePath = BookInfoDaoImpl.getInstance(this).getUnZipPath(Integer.parseInt(book.getBookId()));
		Log.v(TAG,"needDownLoad_zipFilePath:" + zipFilePath);
		File zipFile = new File(zipFilePath);
		if(zipFile.exists()){
			return false;
		}else{
			return true;
		}
	}
	
	//��ʾ�Ƿ����ص�Dialog��ȷ��������
	private DialogInterface.OnClickListener ok_click = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			pd = new ProgressDialog(BookDetailActivity.this);
			pd.setCancelable(false);
			pd.setMessage(getString(R.string.downlond_wait));
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.show();
			new DownloadThread(downloadBook).start();
		}
	};
	
	//��ʾ�Ƿ����ص�Dialog��ȡ��������
	private DialogInterface.OnClickListener cancel_click = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};
	
	@Override
	public String getTag() {
		return TAG;
	}
	
	//����zip�ļ����߳�
	class DownloadThread extends Thread{
		private BookInfo book;
		
		public DownloadThread(BookInfo book){
			this.book = book;
		}
		
		@Override
		public void run() {
			try {
				Utils.downloadFile(Constants.URL + book.getZipPath(),Constants.ZIPTEMP);
			} catch (Exception e) {
				e.printStackTrace();
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putString("msg", getString(R.string.download_fail));
				msg.setData(data);
				handler.sendMessage(msg);
				pd.dismiss();
				return;
			}
			String unzipPath = BookInfoDaoImpl.getInstance(BookDetailActivity.this).getUnZipPath(Integer.parseInt(book.getBookId()));
			if(!Utils.unZipFile(unzipPath)){
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putString("msg", getString(R.string.upzip_fail));
				msg.setData(data);
				handler.sendMessage(msg);
				pd.dismiss();
				return;
			}
			pd.dismiss();
			viewBook(book);
		}
	}
}
