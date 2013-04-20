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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * ×¢²á½çÃæ
 * @author sharoncn
 *
 */
public class RegActivity extends BaseActivity implements OnClickListener{
	protected static final String TAG = "RegActivity";
	private static final int WHAT_SHOWMSG = 0;
	private static final int WHAT_GONEXT = 1;
	private static final String MSG_FLAG = "msg";
	private Button btn_cancel,btn_submit;
	private EditText et_username,et_password;
	private String username,password;
	private ProgressDialog pd;
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case WHAT_SHOWMSG:
				if(pd != null)pd.dismiss();
				Util.showMsg(RegActivity.this, msg.getData().getInt(MSG_FLAG));
				break;
			case WHAT_GONEXT:
				if(pd != null)pd.dismiss();
				Intent intent = new Intent(RegActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_reg);
		
		et_password = (EditText) findViewById(R.id.reg_et_psw);
		et_username = (EditText) findViewById(R.id.reg_et_un);
		btn_cancel = (Button) findViewById(R.id.reg_btn_cancel);
		btn_submit = (Button) findViewById(R.id.reg_btn_submit);
		
		btn_cancel.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
	}
	
	private boolean validate(){
		username = et_username.getText().toString();
		password = et_password.getText().toString();
		if(username == null || username.equals("") || password == null || password.equals("")){
			Util.showMsg(this, R.string.reg_error);
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.reg_btn_submit:
			if(validate()){
				Log.v(TAG,"validate true");
				// ×¢²á
				pd = Util.showProgressDialog(RegActivity.this, getString(R.string.registering));
				new Thread(){
					@Override
					public void run() {
						URLParam param = new URLParam();
						String result;
						param.addParam(URLProtocol.CMD, URLProtocol.REGISTER_CMD_VALUE);
						param.addParam(URLProtocol.NAME, username);
						param.addParam(URLProtocol.PASSWORD, password);
						try {
							result = HttpConnection.getContentString(URLProtocol.URL, param);
							if(result == null){
								return;
							}
							if(!result.contains("'code':'0'")){
								Message msg = new Message();
								msg.what = WHAT_SHOWMSG;
								Bundle data = new Bundle();
								data.putInt(MSG_FLAG, R.string.reg_fail);
								msg.setData(data);
								handler.sendMessage(msg);
								return;
							}
							String uid = Util.getUid(result);
							LifePreferences preferences = LifePreferences.getPreferences(RegActivity.this);
							preferences.saveName(username);
							preferences.savePW(password);
							preferences.setUID(uid);
							
							handler.sendEmptyMessage(WHAT_GONEXT);
						} catch (IOException e) {
							e.printStackTrace();
							if(e.getMessage().contains("Network is unreachable")){
								Message msg = new Message();
								msg.what = WHAT_SHOWMSG;
								Bundle data = new Bundle();
								data.putInt(MSG_FLAG, R.string.networkunreachable);
								msg.setData(data);
								handler.sendMessage(msg);
							}
						}
						
					}
				}.start();
				
				
			}
			break;
		case R.id.reg_btn_cancel:
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setMessage(String.format(getString(R.string.donotregister), getString(R.string.app_name)))
			.setCancelable(false)
			.setPositiveButton(R.string.ok, dialog_ok_click)
			.setNegativeButton(R.string.cancel, dialog_cancel_click)
			.show();
			break;
		}
	}
	

	private DialogInterface.OnClickListener dialog_ok_click = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			RegActivity.this.startService(new Intent(RegActivity.this,DataService.class));
			Intent intent = new Intent(RegActivity.this,TabMainActivity.class);
			intent.putExtra(TabMainActivity.FLAG_ISLOGIN, false);
			startActivity(intent);
			finish();
		}
	};
	
	private DialogInterface.OnClickListener dialog_cancel_click = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};
}
