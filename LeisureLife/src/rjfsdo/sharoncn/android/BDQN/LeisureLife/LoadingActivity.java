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
 * �������
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
		
		//���UID�Ѿ����ڱ�ʾ����Ҫ�ٴ�ע�ᵽ��¼���棬��������ڱ�ʾ��Ҫע��
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
