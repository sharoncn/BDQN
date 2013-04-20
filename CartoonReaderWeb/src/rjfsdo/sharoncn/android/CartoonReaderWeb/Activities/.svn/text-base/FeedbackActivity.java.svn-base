package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Core.BaseActivity;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Data.FeedbackDao;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Utils;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FeedbackActivity extends BaseActivity {
	public static final String TAG = "FeedbackActivity";
	private Button btn_Cancel,btn_Submit;
	private EditText feedback_edit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_feedback);
		add(this);
		
		this.setHeaderBackgroundResources(R.drawable.top_bg_noword);
		this.setHeaderTitleVisibility(View.VISIBLE);
		this.setHeaderTitle(getString(R.string.feedback_title));
		this.setHeaderTextSize(20);
		
		btn_Cancel = (Button) findViewById(R.id.feedback_btn_cancel);
		btn_Submit = (Button) findViewById(R.id.feedback_btn_submit);
		feedback_edit = (EditText) findViewById(R.id.feedback_et);
		btn_Cancel.setOnClickListener(btn_Click);
		btn_Submit.setOnClickListener(btn_Click);
	}
	
	private OnClickListener btn_Click = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.feedback_btn_cancel:
				Intent intent = new Intent(FeedbackActivity.this,TabMainActivity.class);
				startActivity(intent);
				finish();
				break;
			case R.id.feedback_btn_submit:
				submit(feedback_edit.getText().toString());
				break;
			}
		}
	};
	
	private void submit(String content){
		if(content == null || content.equals("")){
			Utils.showMsg(this, getString(R.string.advise_content));
			return;
		}
		//提交到服务器,按照设计文档说明：这里只存储到本地数据库
		long result = FeedbackDao.getInstance(this).feedBack(content);
		if(result == -1){
			Utils.showMsg(this, getString(R.string.advise_fail));
			return;
		}
		Utils.showMsg(this, getString(R.string.advise_success));
	}
	
	@Override
	public String getTag() {
		return TAG;
	}
}
