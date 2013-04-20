package rjfsdo.sharoncn.android.BDQN.LeisureLife.Test;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Listener.OnComplatedListener;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Service.DataService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class TestAct extends Activity implements OnComplatedListener{
	private static final String TAG = "TestAct";
	private static final int FLAG_STARTTHREAD = 101;
	private static final int FLAG_SETIMAGE = 102;
	
	private static final String FLAG_IMAGEID = "imageId";
	private ImageView image;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
				case FLAG_STARTTHREAD:
					new ImageThread(TestAct.this,"10002").start();
					break;
				case FLAG_SETIMAGE:
					String imageId = msg.getData().getString(FLAG_IMAGEID);
					image.setImageDrawable(DataManager.getInstance(TestAct.this).getImage(imageId));
					break;
			}
			
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_test);
		
		image = (ImageView) findViewById(R.id.test_image);
		
		Intent intent = new Intent(this,DataService.class);
		this.startService(intent);
		handler.sendEmptyMessageDelayed(FLAG_STARTTHREAD, 3000);
	}
	
	class ImageThread extends Thread{
		private String imageId;
		private Context context;

		public ImageThread(Context context,String imageId) {
			this.context = context;
			this.imageId = imageId;
		}

		@Override
		public void run() {
			Log.v(TAG,"Act Thread Start");
			DataManager.getInstance(context).getImage(0, imageId, TestAct.this);
		}
	}

	@Override
	public void onComplated(int index, String imageId) {
		Log.v(TAG,"onComplated");
		Message msg = new Message();
		Bundle data = new Bundle();
		data.putString(FLAG_IMAGEID, imageId);
		msg.setData(data);
		msg.what = FLAG_SETIMAGE;
		handler.sendMessage(msg);
	}
}
