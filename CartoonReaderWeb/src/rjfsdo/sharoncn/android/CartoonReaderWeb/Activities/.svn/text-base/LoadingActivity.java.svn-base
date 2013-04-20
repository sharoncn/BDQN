package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Data.BookInfoDaoImpl;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Data.SqliteHelper;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookInfo;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Net.Client;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Constants;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Utils;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;

/**
 * 设计将在Loading中将所有漫画数据下载下来，并写入到数据库中 
 * 可能在实际的项目中由于全部数据量太大，下载时间过长。先这么着吧，原理一样。
 * @author sharoncn
 *
 */
public class LoadingActivity extends Activity {
	private static final String TAG = "LoadingActivity";
	private ProgressBar loading_pb;
	private View image_bg;
	private Timer timer;
	private Animation anim;
	//等待3秒跳转，如果3秒未执行完准备数据的操作，继续等待10秒退出。如果3秒之前执行完操作，依然等待3秒退出
	private boolean flag_3s = false;
	private boolean flag_complated = false;
	private int count = 0;
	private Handler handler;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading);
        init();
    }
    
    private void init(){
    	handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				Bundle data = msg.getData();
				if(data.containsKey("msg")){
					Utils.showMsg(LoadingActivity.this, data.getString("msg"));
				}
				super.handleMessage(msg);
			}
    		
    	};
    	
    	image_bg = findViewById(R.id.image_bg);
    	loading_pb = (ProgressBar) findViewById(R.id.loading_pb);
    	loading_pb.setProgress(0);
    	
    	anim = new AlphaAnimation(0.0f, 1.0f);
    	anim.setDuration(2000);
    	image_bg.startAnimation(anim);
    	timer = new Timer();
    	timer.schedule(task,0);
    	handler.postDelayed(new Runnable(){
			@Override
			public void run() {
				flag_3s = true;
				handler.postDelayed(new Runnable(){
					@Override
					public void run() {
						count += 1;
						if(flag_complated){
							Intent intent = new Intent(LoadingActivity.this,TabMainActivity.class);
							startActivity(intent);
							finish();
							return;
						}
						if(count >= 10){
							timer.cancel();
							Intent intent = new Intent(LoadingActivity.this,TabMainActivity.class);
							startActivity(intent);
							finish();
							return;
						}
						handler.postDelayed(this, 1000);
					}
				}, 1000);
			}
    	}, 3000);
    }
    
    //初始化数据库
    private void initDatabase(){
    	SqliteHelper helper = new SqliteHelper(this);
    	SQLiteDatabase db = helper.getReadableDatabase();
    	db.close();
    }
    
    //准备数据
    private void prepareData(String url,List<NameValuePair> params,String fileName){
		try {
			String result_hitnum = Client.requestStringContent(url, params);
			if(result_hitnum != null && result_hitnum != ""){
				if(!Utils.write2Temp(result_hitnum,fileName))
					Log.v(TAG, getString(R.string.write_xml_fail) + " " + fileName);
			}
		} catch (Exception e) {
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putString("msg", getString(R.string.getdata_fail));
			msg.setData(data);
			handler.sendMessage(new Message());
			e.printStackTrace();
		}
    }
    
    private void saveAllBook2DB(){
    	File allBooks = new File(Constants.XMLTEMP + File.separator + Constants.ALL_FILENAME);
    	if(allBooks.exists()){
    		try {
				List<BookInfo> books = Utils.formatBookInfo(allBooks);
				BookInfoDaoImpl.getInstance(LoadingActivity.this).saveBookInfo(books);
			} catch (Exception e) {
				e.printStackTrace();
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putString("msg", getString(R.string.save2db_fail));
				msg.setData(data);
				handler.sendMessage(new Message());
			}
    	}
    }
    
    private TimerTask task = new TimerTask() {
		@Override
		public void run() {
			initDatabase();//初始化数据库
			//热门排行
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("action", "book"));
			params.add(new BasicNameValuePair("type", "hitnum"));
	    	prepareData(Constants.URL_BOOK,params,Constants.ORDER_FILENAME);//准备数据
	    	params.clear();
	    	//推荐列表
			params.add(new BasicNameValuePair("action", "book"));
			params.add(new BasicNameValuePair("type", "recommend"));
	    	prepareData(Constants.URL_BOOK,params,Constants.RECOMMEND_FILENAME);//准备数据
	    	//所有列表
	    	params.clear();
			params.add(new BasicNameValuePair("action", "book"));
			params.add(new BasicNameValuePair("type", "all"));
	    	prepareData(Constants.URL_BOOK,params,Constants.ALL_FILENAME);//准备数据
	    	//类型列表
	    	params.clear();
			params.add(new BasicNameValuePair("action", "type"));
	    	prepareData(Constants.URL_TYPE,params,Constants.BOOKTYPE_FILENAME);//准备数据
	    	//保存所有书到数据库
	    	saveAllBook2DB();
	    	
	    	flag_complated = true; 
	    	if(flag_3s){
	    		handler.removeCallbacksAndMessages(null);
				Intent intent = new Intent(LoadingActivity.this,TabMainActivity.class);
				startActivity(intent);
				finish();
	    	}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}