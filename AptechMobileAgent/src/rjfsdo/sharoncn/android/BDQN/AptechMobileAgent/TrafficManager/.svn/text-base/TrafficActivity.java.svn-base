package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.TrafficManager;

import java.util.HashMap;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Service.TrafficService;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Constants;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Util;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class TrafficActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {
	private static final String TAG = "TrafficActivity";
	private TextView upload, download, total, title;
	private EditText et_reserve;
	private CheckBox cb_reserve;
	private Button btn_day, btn_month, btn_query;
	private static SharedPreferences sp;
	private DataRefreshReceiver receiver;
	private boolean isMonthShow = true;// 当前显示的月统计还是天统计
	private TrafficModel dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_traffic);
		if (sp == null)
			sp = PreferenceManager.getDefaultSharedPreferences(this);
		if (dao == null)
			dao = TrafficModel.getInstance(this);
		
		initViews();
		initContent();
		registerDataRefreshReceiver();
	}

	private void registerDataRefreshReceiver() {
		if (receiver == null)
			receiver = new DataRefreshReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(TrafficService.ACTION_REFRESH);
		registerReceiver(receiver, filter);
	}

	private void initContent() {
		refresh(true);
	}

	private void initViews() {
		upload = (TextView) findViewById(R.id.traffic_upload);
		download = (TextView) findViewById(R.id.traffic_download);
		total = (TextView) findViewById(R.id.traffic_total);
		title = (TextView) findViewById(R.id.traffic_title);
		title.setText(R.string.count_month);

		et_reserve = (EditText) findViewById(R.id.traffic_et_reserve);
		cb_reserve = (CheckBox) findViewById(R.id.traffic_cb_save);
		et_reserve
				.setText("" + sp.getFloat(Constants.PREFERENCE_RESERVE, 0.0f));
		cb_reserve.setChecked(sp
				.getBoolean(Constants.PREFERENCE_ISSETUP, false));
		cb_reserve.setOnCheckedChangeListener(this);

		btn_day = (Button) findViewById(R.id.traffic_btn_day);
		btn_month = (Button) findViewById(R.id.traffic_btn_month);
		btn_query = (Button) findViewById(R.id.traffic_sms_query);
		btn_day.setOnClickListener(this);
		btn_month.setOnClickListener(this);
		btn_query.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.traffic_btn_day:
			// 显示当天流量
			isMonthShow = true;
			title.setText(R.string.count_day);
			refresh(isMonthShow);
			break;
		case R.id.traffic_btn_month:
			// 显示当月流量
			isMonthShow = false;
			title.setText(R.string.count_month);
			refresh(isMonthShow);
			break;
		case R.id.traffic_sms_query:
			// 短信查询流量
			gotoSmsQuery();
			break;
		}
	}

	private void refresh(boolean showMonth) {
		// 刷新view内容
		HashMap<String,Double> result;
		if(showMonth){
			result = dao.getMonthTotalTraffic();
		}else{
			result = dao.getDayTotalTraffic();
		}
		if(result != null){
			upload.setText(Util.getFormatedSize(result.get(TrafficModel.KEY_UPLOAD)));
			download.setText(Util.getFormatedSize(result.get(TrafficModel.KEY_DOWNLOAD)));
			total.setText(Util.getFormatedSize(result.get(TrafficModel.KEY_TOTAL)));
		}else{
			upload.setText(Util.getFormatedSize(0));
			download.setText(Util.getFormatedSize(0));
			total.setText(Util.getFormatedSize(0));
		}
	}

	// 跳转到短信查询流量
	private void gotoSmsQuery() {
		Intent intent = new Intent(this, TrafficSmsQuery.class);
		startActivity(intent);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// 保存到SharedPreferences
		// keyname Constants.PREFERENCE_RESERVE
		if (isChecked) {
			final String reserveValue = et_reserve.getText().toString();
			if (!TextUtils.isEmpty(reserveValue)) {
				final Editor editor = sp.edit();
				float value = 0.0f;
				try {
					value = Float.parseFloat(reserveValue);
				} catch (Exception e) {
				}
				editor.putFloat(Constants.PREFERENCE_RESERVE, value);
				editor.putBoolean(Constants.PREFERENCE_ISSETUP, isChecked);
				editor.commit();
			}
		}
	}

	@Override
	protected void onDestroy() {
		if (receiver != null)
			unregisterReceiver(receiver);
		super.onDestroy();
	}

	//当TrafficService更新数据库时会发送TrafficService.ACTION_REFRESH广播
	class DataRefreshReceiver extends BroadcastReceiver {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (TrafficService.ACTION_REFRESH.equals(intent.getAction())) {
				// 刷新界面
				Log.i(TAG,"Action:" + TrafficService.ACTION_REFRESH + "  刷新界面");
				refresh(isMonthShow);
			}
		}

	}
}
