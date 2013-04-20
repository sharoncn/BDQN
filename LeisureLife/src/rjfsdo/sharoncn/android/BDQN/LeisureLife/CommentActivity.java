package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import java.util.HashMap;
import java.util.Map;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.BaseHeader;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.LifePreferences;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Recommend;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLProtocol;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 评论界面
 * 
 * @author sharoncn
 * 
 */
public class CommentActivity extends BaseActivity implements OnClickListener {
	public static final String FLAG_ID = "id";
	/**
	 * type就是URLProtocol.xxx_DETAIL_CMD_VALUE
	 */
	public static final String FLAG_TYPE = "type";
	private static final String TAG = "CommentActivity";
	private EditText text;
	private Button btn_back, btn_publish;
	private BaseHeader header;
	private String name, id;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == DataManager.FLAG_SEND_RECOMMAND) {
				if (msg.getData().getBoolean(DataManager.FLAG_ISSUCCESS)) {
					Util.showMsg(CommentActivity.this, R.string.sendsuccess);
					finish();
				}
			}
			CommentActivity.this.handleMessage(msg);
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_act_comment);
		init();
	}

	private void init() {
		header = (BaseHeader) findViewById(R.id.comment_header);
		text = (EditText) findViewById(R.id.comment_text);
		btn_back = (Button) findViewById(R.id.comment_btn_back);
		btn_publish = (Button) findViewById(R.id.comment_btn_publish);

		header.setTitleColor(R.color.white);
		header.setTitle(getString(R.string.comment));
		text.setHint(R.string.comment_content_hint);
		btn_back.setOnClickListener(this);
		btn_publish.setOnClickListener(this);
		name = LifePreferences.getPreferences(this).getName();
		Bundle data = getIntent().getExtras();
		if (data != null) {
			id = data.getString(FLAG_ID);
		} else {
			Log.e(TAG, "未正确传递参数！");
			Toast.makeText(this, R.string.comment_args_illegal,
					Toast.LENGTH_SHORT).show();
			btn_publish.setEnabled(false);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.comment_btn_back:
			finish();
			break;
		case R.id.comment_btn_publish:
			String txt = text.getText().toString();
			if (txt != null && txt.length() > 0) {
				publishComment(txt);
			}
			break;
		}
	}

	private void publishComment(String txt) {
		Map<String, String> args = new HashMap<String, String>();
		args.put(URLProtocol.CMD, URLProtocol.SEND_COMMENT_CMD_VALUE);
		args.put(URLProtocol.TYPE, "" + 1);
		args.put(URLProtocol.NAME, name);
		args.put(URLProtocol.CONTENT, txt);
		args.put(URLProtocol.TID, id);
		try {
			DataManager.getInstance(this).getData(Recommend.class,
					DataManager.FLAG_SEND_RECOMMAND, handler, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
