package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import java.io.IOException;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.LifePreferences;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.HttpConnection;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLParam;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLProtocol;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Service.DataService;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * 登陆界面
 * 
 * @author sharoncn
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
	// private static final String TAG = null;
	private static final int WHAT_SHOWMSG = 0;
	private static final int WHAT_GONEXT = 1;
	private static final String MSG_FLAG = "msg";
	private EditText et_un, et_pw;
	private CheckBox cb_rp, cb_al;
	private Button btn_ok, btn_cancel;
	private String username, password, uid;
	private LifePreferences preferences;
	private ProgressDialog pd;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_SHOWMSG:
				if (pd != null)pd.dismiss();
				Util.showMsg(LoginActivity.this, msg.getData().getInt(MSG_FLAG));
				break;
			case WHAT_GONEXT:
				if (pd != null)pd.dismiss();
				gotoTabMain();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_login);

		preferences = LifePreferences.getPreferences(this);
		et_un = (EditText) findViewById(R.id.login_et_un);
		et_pw = (EditText) findViewById(R.id.login_et_psw);
		btn_ok = (Button) findViewById(R.id.login_btn_submit);
		btn_cancel = (Button) findViewById(R.id.login_btn_cancel);
		cb_al = (CheckBox) findViewById(R.id.login_cb_al);
		cb_rp = (CheckBox) findViewById(R.id.login_cb_rp);

		username = preferences.getName();
		password = preferences.getPW();
		uid = preferences.getUID();
		// 先检查是否记住了密码
		boolean isRemeberPW = preferences.getRemberPW();
		if (isRemeberPW) {
			et_un.setText(username);
			et_pw.setText(password);
			cb_rp.setChecked(isRemeberPW);
		} else {
			et_un.setText(username);
		}

		if (preferences.getAutoLogin()) {
			cb_al.setChecked(true);
			if (login(uid)) {
				gotoTabMain();
			}
		}

		btn_ok.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		
		Util.startService(this,DataService.class);
	}

	/**
	 * 登陆服务器
	 * @param uid 登陆使用的uid
	 * @return 成功返回true，否则返回false
	 */
	private boolean login(String uid) {
		// 提交
		URLParam param = new URLParam();
		String result;
		param.addParam(URLProtocol.CMD, URLProtocol.LOGIN_CMD_VALUE);
		param.addParam(URLProtocol.UID, uid);
		try{
			result = HttpConnection.getContentString(URLProtocol.URL, param);
			if(result == null){
				return false;
			}
			if (!result.contains("'code':'0'")) {
				return false;
			}
			return true;
		}catch(IOException e){
			if(e.getMessage().contains("Network is unreachable")){
				Message msg = new Message();
				msg.what = WHAT_SHOWMSG;
				Bundle data = new Bundle();
				data.putInt(MSG_FLAG, R.string.networkunreachable);
				msg.setData(data);
				handler.sendMessage(msg);
			}
			return false;
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn_submit:
			String tUserName = et_un.getText().toString();
			String tPassword = et_pw.getText().toString();
			if (tUserName == null || tPassword == null
					|| !tUserName.equals(username)
					|| !tPassword.equals(password)) {
				Util.showMsg(this, R.string.up_error);
				return;
			}
			pd = Util.showProgressDialog(this, getString(R.string.logining));
			new Thread() {
				@Override
				public void run() {
					if (!login(uid)) {
						Message msg = new Message();
						msg.what = WHAT_SHOWMSG;
						Bundle data = new Bundle();
						data.putInt(MSG_FLAG, R.string.login_fail);
						msg.setData(data);
						handler.sendMessage(msg);
						return;
					} else {
						preferences.saveName(username);
						preferences.savePW(password);
						preferences.setUID(uid);
						preferences.setAutoLogin(cb_al.isChecked());
						preferences.setRemberPW(cb_rp.isChecked());
						Message msg = new Message();
						msg.what = WHAT_GONEXT;
						handler.sendMessage(msg);
					}
				}
			}.start();

			break;
		case R.id.login_btn_cancel:
			AlertDialog.Builder dlg = new AlertDialog.Builder(this);
			dlg.setMessage(R.string.login_cancel_tips)
					.setPositiveButton(R.string.ok, ok_click)
					.setNegativeButton(R.string.cancel, cancel_click).show();
			break;
		}
	}

	private void gotoTabMain() {
		Intent intent = new Intent(this, TabMainActivity.class);
		intent.putExtra(TabMainActivity.FLAG_ISLOGIN, true);
		startActivity(intent);
		finish();
	}

	private DialogInterface.OnClickListener ok_click = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			finish();
		}
	};

	private DialogInterface.OnClickListener cancel_click = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};
}
