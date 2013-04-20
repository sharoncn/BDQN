package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.TrafficManager;

import java.util.List;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.SpnProvider;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.SpnProvider.SpnInfo;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Util;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TrafficSmsQuery extends Activity implements OnClickListener {
	private static final String TAG = "TrafficSmsQuery";
	private static TelephonyManager mTeleManager;
	private static SmsManager mSmsManager;
	private EditText spn,sendto,applyWhat;
	private Button send;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_traffic_smsquery);
		if(mTeleManager == null)
			mTeleManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		if(mSmsManager == null)
			mSmsManager = SmsManager.getDefault();
		
		initView();
		initContent();
	}

	private void initContent() {
		final String code = mTeleManager.getSimOperator();
		final SpnProvider spnProvider = SpnProvider.getInstance(this);
		Log.v(TAG,"code:" + code);
		SpnInfo spnInfo = spnProvider.getSpnInfoByCode(code);
		if(spnInfo == null){
			return;
		}
		spn.setText(spnInfo.getName());
		sendto.setText(spnInfo.getNumber());
		applyWhat.setText(spnInfo.getText());
	}

	private void initView() {
		spn = (EditText) findViewById(R.id.traffic_spn);
		sendto = (EditText) findViewById(R.id.traffic_sendto);
		applyWhat = (EditText) findViewById(R.id.traffic_applywhat);
		
		send = (Button) findViewById(R.id.traffic_btn_send);
		send.setOnClickListener(this);
	}

	//·¢ËÍ¶ÌÐÅ
	private void sendMessage(){
		final String txt_Spn = spn.getText().toString();
		final String txt_Sendto = sendto.getText().toString();
		final String txt_ApplyWhat = applyWhat.getText().toString();
		
		if(TextUtils.isEmpty(txt_Spn) || TextUtils.isEmpty(txt_Sendto) || TextUtils.isEmpty(txt_ApplyWhat)){
			Util.showMsg(this, R.string.input_err);
			return;
		}
		
		final List<String> msgs = mSmsManager.divideMessage(txt_ApplyWhat);
		final PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(), 0);
		for(String msg:msgs){
			mSmsManager.sendTextMessage(txt_Sendto, null, msg, pi, null);
		}
		Util.showMsg(this, R.string.send_success);
	}

	@Override
	public void onClick(View v) {
		sendMessage();
	}
}
