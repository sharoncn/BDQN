package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.BaseHeader;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 反馈详情
 * 
 * @author sharoncn
 * 
 */
public class QuestionActivity extends BaseActivity implements OnClickListener {
	private EditText content;
	private Button send, cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_question);

		BaseHeader header = (BaseHeader) findViewById(R.id.header);
		header.setTitle(getString(R.string.feedback));

		content = (EditText) findViewById(R.id.et_content);
		send = (Button) findViewById(R.id.btn_send);
		cancel = (Button) findViewById(R.id.btn_cancel);
		
		send.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:
			String c = content.getText().toString();
			if(c != null && c.length() > 0){
				sendToServer();
			}else{
				Util.showMsg(this, R.string.send_empty);
			}
			break;
		case R.id.btn_cancel:
			finish();
			break;
		}
	}

	private void sendToServer() {
		//TODO Send To server
		//没有发送到服务器的接口
		Util.showMsg(this, R.string.sendsuccess);
	}
}
