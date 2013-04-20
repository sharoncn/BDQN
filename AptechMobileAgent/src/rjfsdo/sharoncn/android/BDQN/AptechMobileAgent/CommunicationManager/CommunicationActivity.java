package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.CommunicationManager;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Util;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CommunicationActivity extends Activity implements OnClickListener {
	private Button btn_bak,btn_restore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_communication);
		
		initViews();
		//先查询一次
		//在魅族M9上，当涉及联系人，短信查询时会提示用户允许应用操作(权限确认)。
		//弹出一个选择的对话框。权限确认检查本身是异步的。放在查询操作之前，会造成查询的Corsor无数据。
		//所以在Activity中提前查询，确认权限。当然可以使用广播接收器接收确认广播，但是没必要这么复杂吧。
		try{
			getContentResolver().query(Uri.parse(CommunicationModel.SMS_URI), null, null, null, null);
		}catch(Exception e){}
	}

	private void initViews() {
		btn_bak = (Button) findViewById(R.id.communication_bakup);
		btn_restore = (Button) findViewById(R.id.communication_restore);
		btn_bak.setOnClickListener(this);
		btn_restore.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.communication_bakup:
			if(CommunicationModel.getInstance(this).backupSms()){
				Util.showMsg(this, R.string.bak_sms_success);
			}else{
				Util.showMsg(this, R.string.bak_sms_fail);
			}
			break;
		case R.id.communication_restore:
			CommunicationModel.getInstance(this).recoverySms();
			break;
		}
	}

	
}
