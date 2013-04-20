package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.ShareHeader;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

/**
 * 推荐功能界面
 * 
 * @author sharoncn
 * 
 */
public class RecommendActivity extends BaseActivity implements OnClickListener {
	public static final String INTENT_IN_CONTENT = "content";

	private EditText et_content, et_number;
	private ShareHeader header;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_recommend);

		et_content = (EditText) findViewById(R.id.recommend_content);
		et_number = (EditText) findViewById(R.id.recommend_number);
		header = (ShareHeader) findViewById(R.id.recommend_header);
		initHeader();

		Bundle data = getIntent().getExtras();
		if (data != null && data.containsKey(INTENT_IN_CONTENT)) {
			et_content.setText(data.getString(INTENT_IN_CONTENT));
		}
	}

	// 设置Header
	private void initHeader() {
		header.setOnButtonClickListener(this);
		header.setTitle(getString(R.string._content));
		header.setTitleColor(R.color.black);
		header.setTitleTextSize(20);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case ShareHeader.BUTTON_BACK:
			finish();
			break;
		case ShareHeader.BUTTON_SEND:
			if(et_content != null && et_number != null && !"".equals(et_content.getText().toString()) 
			&& !"".equals(et_number.getText().toString())){
				SmsManager smsMgr = SmsManager.getDefault();
				List<String> msgs = smsMgr.divideMessage(et_content.getText().toString());
				String addr = et_number.getText().toString();
				PendingIntent pi = PendingIntent.getBroadcast(RecommendActivity.this, 0, new Intent(), 0);
				for(String msg:msgs){
					smsMgr.sendTextMessage(addr, null, msg, pi, null);
				}
				Util.showMsg(RecommendActivity.this, R.string.send_success);
				break;
			}
			Util.showMsg(RecommendActivity.this, R.string.send_empty);
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//TabMain内部Activity，为了防止back事件被处理，当back时返回false
		//如果不处理，在输入框获得焦点之后，点击back，事件被内部Activity处理，应用退出，而无法传递到TabMain
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
