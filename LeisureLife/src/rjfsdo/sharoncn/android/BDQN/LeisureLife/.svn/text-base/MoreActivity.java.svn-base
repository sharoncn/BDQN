package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import java.util.ArrayList;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter.MoreAdapter;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.BaseHeader;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 更多功能界面
 * 
 * @author sharoncn
 * 
 */
public class MoreActivity extends BaseActivity implements OnItemClickListener {
	private ListView list;
	private List<Integer> data = new ArrayList<Integer>();
	private BaseHeader header;
	private MoreAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_more);

		list = (ListView) findViewById(R.id.more_list);
		header = (BaseHeader) findViewById(R.id.more_header);

		header.setTitle(getString(R.string.more));
		list.setCacheColorHint(Color.TRANSPARENT);

		data.add(R.string.about);
		data.add(R.string.help);
		data.add(R.string.feedback);
		data.add(R.string.exit);
		adapter = new MoreAdapter(this, data);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> adpView, View view, int position,
			long id) {
		int item = (Integer) adpView.getAdapter().getItem(position);
		Class<?> clazz = null;
		switch (item) {
		case R.string.about:
			clazz = AboutActivity.class;
			break;
		case R.string.help:
			clazz = HelpActivity.class;
			break;
		case R.string.feedback:
			clazz = QuestionActivity.class;
			break;
		case R.string.exit:
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setMessage(R.string.exit_ask)
			.setTitle(R.string.app_name)
			.setPositiveButton(R.string.no, alertBtnClick)
			.setNegativeButton(R.string.yes, alertBtnClick)
			.show();
			return;
		}
		Intent intent = new Intent(MoreActivity.this,clazz);
		startActivity(intent);
	}
	
	DialogInterface.OnClickListener alertBtnClick = new DialogInterface.OnClickListener(){
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch(which){
			case DialogInterface.BUTTON_POSITIVE:
				dialog.dismiss();
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				Util.exit(MoreActivity.this);
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//TabMain内部Activity，为了防止back事件被处理，当back时返回false
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
