package rjfsdo.sharoncn.android.BDQN.AptechWeibo;

import java.util.Timer;
import java.util.TimerTask;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboDataManager;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Service.DataService;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.ImageCache;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class TestGetImage extends Activity {
	ImageView img;
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case WeiboDataManager.MSGTYPE_IMAGEOK:
				Bundle data = msg.getData();
				if (data.getBoolean(WeiboDataManager.FLAG_ISSUCCESS)) {
					final String key = data.getString(WeiboDataManager.FLAG_IMAGEKEY);
					final Bitmap d = (Bitmap) data.getParcelable(WeiboDataManager.FLAG_DATA);
					ImageCache.getInstance(TestGetImage.this).put(key, d);
					img.setImageBitmap(d);
				}
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		Intent intent = new Intent(this,DataService.class);
		startService(intent);
		img = (ImageView) findViewById(R.id.img);
		//Thread.sleep(time)
		new Timer().schedule(new TimerTask(){
			@Override
			public void run() {
				WeiboDataManager.getInstance(TestGetImage.this).getImage("http://tp1.sinaimg.cn/2024714892/50/5598700648/0", handler, 0);
			}
		}, 2000);
		
	}

}
