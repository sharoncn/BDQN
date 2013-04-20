package rjfsdo.sharoncn.android.BDQN.AptechWeibo;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboDataManager;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboPreference;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Util;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

/**
 * Login��Loading֮ǰ��ʾ����ô��������ô���졣�����ҽ�Login����Loading֮��
 * ������Ū�����˺�ѡ���½�ģ�����ʹ��Sina��SDKò��û�а취ʵ����һ�㡣��Ϊ�޷�
 * ����û���½ʱ���˺�����ֻ�ܷ���һ��Token��˭����֪��Token��Ӧ�ĸ��û�����
 * @author sharoncn
 *
 */
public class LoginAct extends Activity {
	protected static final String TAG = "LoginAct";
	private Class<?> targetClass;
	private Intent intent = new Intent();
	private static WeiboPreference preference;
	private Handler handler;
	private WeiboDataManager wdm;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.layout_act_login);
		
		pd = Util.showProgressDialog(this, getString(R.string.loading));
		wdm = WeiboDataManager.getInstance(LoginAct.this);
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case WeiboDataManager.MSGTYPE_LOGIN:
					if(msg.getData().getBoolean(WeiboDataManager.FLAG_ISSUCCESS)){
						Util.showMsg(LoginAct.this, R.string.certification_sucess);
						gotoMain();
					}else{
						Util.showMsg(LoginAct.this, R.string.certification_fail);
					}
					break;
				}
				super.handleMessage(msg);
			}
			
		};
		handler.postDelayed(new Runnable() {
			boolean flag = true;
			@Override
			public void run() {
				while(flag){
					Log.v(TAG, "waitting for service");
					if(wdm.getService() == null){
						continue;
					}else{
						if(pd != null) pd.dismiss();
						flag = false;
						preference = WeiboPreference.getInstance(LoginAct.this);
						String expires = preference.getExpiresTime();
						String token = preference.getAccessToken();
						String refreshToken = preference.getRefreshToken();
						if (!WeiboDataManager.getInstance(LoginAct.this).loginIfNeed(handler, expires, token,
								refreshToken)) {
							gotoMain();
						}
						
					}
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		},1000);
	}

	private void gotoMain() {
		targetClass = MainTabAct.class;
		intent.setClass(this, targetClass);
		startActivity(intent);
		this.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			AlertDialog.Builder alert = new Builder(this);
			alert.setCancelable(true)
			.setTitle(R.string.login_cancel)
			.setPositiveButton(R.string.ok, listener)
			.setNegativeButton(R.string.cancel, listener)
			.show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch(which){
			case DialogInterface.BUTTON_POSITIVE:
				finish();
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				dialog.dismiss();
				break;
			}
		}
	};
}
