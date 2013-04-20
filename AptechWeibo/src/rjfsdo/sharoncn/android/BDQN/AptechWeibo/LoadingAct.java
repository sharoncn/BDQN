package rjfsdo.sharoncn.android.BDQN.AptechWeibo;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboDBHelper;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboPreference;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Service.DataService;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Constants;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.LocationProvider;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.SoundManager;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

public class LoadingAct extends Activity {
	private Handler handler;
	private static WeiboPreference pf;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_loading);

		pf = WeiboPreference.getInstance(this);
		
		init();

		handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(LoadingAct.this, LoginAct.class);
				startActivity(intent);
				finish();
			}
		}, 3000);
	}

	private void init() {
		initDataService();
		initDataBase();
		setup();
		initSoundPool();
		checkSoundSwitch();
		initLocationProvider();
	}

	private void initLocationProvider() {
		LocationProvider.getInstance(getApplicationContext()).initLocation();
	}

	private void initDataBase() {
		SQLiteDatabase db = new WeiboDBHelper(this).getReadableDatabase();
		db.close();
	}

	/**
	 * ≤‚ ‘”√
	 * @deprecated
	 */
	@Deprecated
	private void setup() {
		pf.saveIsPlaySound(true);
	}

	private void checkSoundSwitch() {
		Constants.ISPLAYSOUND = pf.getIsPlaySound();
	}

	private void initSoundPool() {
		// ≥ı ºªØSoundManager
		SoundManager sm = SoundManager.getInstance();
		sm.addSound(this, R.raw.sound_button);
		// sm.addSound(this, R.raw.sound_button);
		// sm.addSound(this, R.raw.sound_button);
		// sm.addSound(this, R.raw.sound_button);
		// sm.addSound(this, R.raw.sound_button);
	}

	private void initDataService() {
		Intent intent = new Intent(this, DataService.class);
		this.startService(intent);
	}
}