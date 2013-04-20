package rjfsdo.sharoncn.android.BDQN.AptechWeibo;

import java.lang.reflect.Method;

import com.weibo.sdk.android.api.WeiboAPI.COMMENTS_TYPE;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.ButtonHeader;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.HasButtonHeader;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.CommentParser;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.ResponseHolder;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.StatusParser;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboDataManager;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Status;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Constants;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Util;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 评论界面
 * @author sharoncn
 *
 */
public class CreateCommentAct extends Activity implements OnClickListener, OnCheckedChangeListener {

	public static final int FLAG_REPOST = 0x1;
	public static final String FLAG_DATA = "data";
	public static final int FLAG_COMMENT = 0x2;
	private String methodName = "repost";
	private Status status;
	private ButtonHeader header;
	private EditText comment;
	private TextView contentlen;
	private CheckBox toOwner,isComment;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WeiboDataManager.MSGTYPE_PARSECONTENT:
				Bundle bundle = msg.getData();
				if (bundle.getBoolean(WeiboDataManager.FLAG_ISSUCCESS)) {
					Util.showMsg(CreateCommentAct.this, R.string.op_success);
				} else {
					//"获取数据失败："
					Util.showMsg(CreateCommentAct.this, getString(R.string.getdata_fail) + bundle.getString(WeiboDataManager.FLAG_ERR_MSG));
				}
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_createcomment);

		initViews();
		initViewContent();
	}

	private void initViewContent() {
		Intent intent = getIntent();
		int flag = intent.getFlags();
		if (flag == FLAG_REPOST) {
			methodName = "repost";
			isComment.setVisibility(View.VISIBLE);
		} else {
			methodName = "comment";
			isComment.setVisibility(View.GONE);
		}
		Bundle data = getIntent().getExtras();
		if (data.containsKey(FLAG_DATA)) {
			status = (Status) data.get(FLAG_DATA);
		}
	}

	private void initViews() {
		// Header
		header = (ButtonHeader) findViewById(R.id.ccomment_header);
		header.setHeaderTitle(R.string.comment);
		header.setLeftButtonBackgroundRes(R.drawable.header_btn_common);
		header.setRightButtonBackgroundRes(R.drawable.header_btn_common);
		header.setLeftButtonText(R.string.back);
		header.setRightButtonText(R.string.publish);
		header.setButtonOnClickListener(this);
		
		// edit
		comment = (EditText) findViewById(R.id.ccomment_content);
		comment.addTextChangedListener(tw);
		
		// TextView
		contentlen = (TextView) findViewById(R.id.ccomment_contentlen);
		
		//CheckBox
		toOwner = (CheckBox) findViewById(R.id.ccomment_to_owner);
		isComment = (CheckBox) findViewById(R.id.ccomment_is_comment);
		isComment.setOnCheckedChangeListener(this);
	}

	public void repost(){
		String id = status.getId();
		ResponseHolder holder = new ResponseHolder(handler);
		holder.setParser(StatusParser.getInstance());
		String content = status.getText();
		if(isComment.isChecked()){
			content = content + comment.getText().toString();
		}
		WeiboDataManager.getInstance(this).repostStatus(holder, id, content, COMMENTS_TYPE.NONE);
	}
	
	public void comment(){
		if(!isOK()){
			return;
		}
		String id = status.getId();
		ResponseHolder holder = new ResponseHolder(handler );
		holder.setParser(CommentParser.getInstance());
		WeiboDataManager.getInstance(this).commentStatus(holder, comment.getText().toString(), id, toOwner.isChecked());
	}
	
	private boolean isOK(){
		String text = comment.getText().toString();
		if(text != null && !"".equals(text)){
			return true;
		}
		return false;
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case HasButtonHeader.ID_LEFTBUTTON:
			this.finish();
			break;
		case HasButtonHeader.ID_RIGHTBUTTON:
			try {
				Method method = this.getClass().getMethod(methodName, new Class<?>[]{});
				method.invoke(this, new Object[]{});
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	private TextWatcher tw = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			int remain = Constants.CONTENT_INPUT_MAX - s.length();
			if (remain > 0) {
				contentlen.setText(String.format(getString(R.string.caninputlen), remain));
			} else {
				contentlen.setText(String.format(getString(R.string.caninputlen), 0));
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			int end = comment.getSelectionEnd();
			int len = s.length() - 140;
			if (len > 0) {
				Editable cPart = s.delete(end - len, end);
				comment.setText(cPart);
			}
		}
	};

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			comment.setEnabled(true);
		}else{
			comment.setEnabled(false);
		}
	}
	
}
