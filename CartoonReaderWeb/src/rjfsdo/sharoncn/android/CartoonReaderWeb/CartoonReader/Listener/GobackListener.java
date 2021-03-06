package rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Listener;

import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;


public class GobackListener implements OnClickListener{
	private static final String TAG = "GobackListener";
	private Activity activity;
	
	public GobackListener(Activity activity){
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(activity,MainActivity.class);
		Bundle bundle = activity.getIntent().getExtras();
		String picPath = null;
		if(bundle != null && bundle.containsKey("picPath")){
			picPath = bundle.getString("picPath");
		}
		Log.v(TAG,"传递到主界面的picPath：" + (picPath == null ? "控制" : picPath));
		if(bundle != null && bundle.size() > 0){
			intent.putExtras(bundle);
		}
		activity.startActivity(intent);
		activity.finish();
	}
}