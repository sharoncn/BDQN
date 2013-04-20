package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.PrivacyManager;

import java.util.Iterator;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Components.ModifyPasswordDialog;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Service.PrivacyService;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Constants;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.MD5Encoder;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Util;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//程序锁界面
public class PrivacyPWDActivity extends Activity implements OnClickListener {
	public static final String FLAG_PKGNAME = "pkgName";
	private static final String TAG = "PrivacyPWDActivity";
	private static MyBinder binder;
	private TextView tv_pkgname;
	private ImageView icon;
	private EditText et_pw;
	private Button btn_unlock, btn_modify;
	private String pkgName = null;
	private static SharedPreferences sp;
	private static MyConn conn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_privacy_pwd);

		if (sp == null)
			sp = PreferenceManager.getDefaultSharedPreferences(this);

		initViews();
		
		final Intent intent = new Intent(this, PrivacyService.class);
		if (conn == null) {
			conn = new MyConn();
		}
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}

	private void initViews() {
		tv_pkgname = (TextView) findViewById(R.id.privacy_pwd_pkgname);
		icon = (ImageView) findViewById(R.id.privacy_pwd_icon);
		et_pw = (EditText) findViewById(R.id.privacy_pwd_et);
		btn_unlock = (Button) findViewById(R.id.privacy_pwd_unlock);
		btn_modify = (Button) findViewById(R.id.privacy_pwd_modify);
		btn_unlock.setOnClickListener(this);
		btn_modify.setOnClickListener(this);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.v(TAG, "onNewIntent");
		//因为使用SingleInstance模式启动的Activity，所以必须重写OnNewIntent
		//为Activity设置新的Intent，否则获得包名会发生错误。
		this.setIntent(intent);
		super.onNewIntent(intent);
	}

	@Override
	protected void onStart() {
		Log.v(TAG, "onStart");
		super.onStart();
	}

	@Override
	protected void onResume() {
		Log.v(TAG, "onResume");
		final Bundle data = getIntent().getExtras();
		if (data != null && data.containsKey(FLAG_PKGNAME)) {
			pkgName = data.getString(FLAG_PKGNAME);
			Log.i(TAG,"pkgName:" + pkgName);
			Iterator<String> keys = data.keySet().iterator();
			while(keys.hasNext()){
				final String key = keys.next();
				final Object value = data.get(key);
				Log.v(TAG,"key:" + key + "   value:" + value);
			}
		}else{Log.v(TAG, "Intent.getExtras() is null or no containsKey");}
		super.onResume();
		initViewContent();
	}

	private void initViewContent() {
		if (pkgName != null) {
			Log.v(TAG, "得到的包名为：" + pkgName);
			try {
				final PackageInfo pkgInfo = getPackageManager().getPackageInfo(pkgName,
						PackageManager.GET_UNINSTALLED_PACKAGES);
				final ApplicationInfo app = pkgInfo.applicationInfo;
				icon.setImageDrawable((getPackageManager().getApplicationIcon(app)));
				tv_pkgname.setText(getPackageManager().getApplicationLabel(app).toString());
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}else{
			Log.v(TAG, "pkgName is null：" + pkgName);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.privacy_pwd_unlock:
			// 解锁
			unlock();
			break;
		case R.id.privacy_pwd_modify:
			// 修改密码
			modityPw();
			break;
		}
	}

	private void modityPw() {
		// 弹出一个dialog用于修改密码
		new ModifyPasswordDialog(this).setTitle(R.string.modify_pw).show();
	}

	private void unlock() {
		final String pw = et_pw.getText().toString();
		if (pw == null || pw.length() == 0) {
			Util.showMsg(this, R.string.input_err);
			return;
		}
		// 比较密码
		final String md_pw = MD5Encoder.encode(pw);
		if (!sp.getString(Constants.PREFERENCE_PW, "").equals(md_pw)) {
			Util.showMsg(this, R.string.pw_wrong);
			return;
		}
		// 密码正确，临时取消保护
		binder.unlock(pkgName);
		finish();
	}

	@Override
	protected void onDestroy() {
		if (conn != null) {
			unbindService(conn);
		}
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}



	class MyConn implements ServiceConnection {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			if (binder == null) {
				binder = (MyBinder) service;
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
	}
}
