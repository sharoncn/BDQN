package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.LifePreferences;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.SaveDataHelper;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.MapManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 载入界面
 * @author sharoncn
 *
 */
public class LoadingActivity extends BaseActivity {
	private Handler handler;
	private LifePreferences preferences;
	private Class<?> clazz = null;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_loading);
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				intent = new Intent(LoadingActivity.this,clazz);
				startActivity(intent);
				finish();
				super.handleMessage(msg);
			}
		};
		
		MapManager.getInstance(this).initMapManager();
		
		initDataBase();
		preferences = LifePreferences.getPreferences(this);
		
		//如果UID已经存在表示不需要再次注册到登录界面，如果不存在表示需要注册
		String uid = preferences.getUID();
		if(uid != null && !"".equals(uid)){
			clazz = LoginActivity.class;
		}else{
			clazz = RegActivity.class;
		}

		handler.sendEmptyMessageDelayed(0, 3000);
	}
	
	private void initDataBase(){
		SaveDataHelper helper = new SaveDataHelper(this);
		SQLiteDatabase db = helper.getReadableDatabase();
		db.close();
	}
}
