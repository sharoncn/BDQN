package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.PowerManager;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Components.BatteryView;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Util;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

public class PowerActivity extends Activity {
	public static final String TAG = "PowerActivity";
	private TextView tv_s, tv_s_r, tv_s_v, tv_s_t, tv_s_tech, tv_s_rt;
	private BatteryView batteryView;
	private boolean isRunning = false;
	private static final int TYPE_TIME = 100;
	private static final String FLAG_TIME = "time";
	private static final long ONSECOND = 1000;
	private static final long ONEMUNITE = 60 * 1000;
	private static final long ANHOUR = 60 * 60 * 1000;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TYPE_TIME:
				final long time = msg.getData().getLong(FLAG_TIME);
				final long hour = time / ANHOUR;
				final long munite = (time % ANHOUR) / ONEMUNITE;
				final long second = (time % ONEMUNITE) / ONSECOND;
				final String sTime = String.format(
						getString(R.string.timeformat),
						new Object[] { Util.stringLeftPadding("" + hour, 2, "0"),
								Util.stringLeftPadding("" + munite, 2, "0"),
								Util.stringLeftPadding("" + second, 2, "0") });
				if (tv_s_rt != null)
					tv_s_rt.setText(String.format(getString(R.string.battery_status_rt), sTime));
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_power);

		initViews();
	}

	private Runnable timeRunnable = new Runnable() {
		@Override
		public void run() {
			Message msg = handler.obtainMessage();
			msg.what = TYPE_TIME;
			Bundle data = new Bundle();
			data.putLong(FLAG_TIME, SystemClock.elapsedRealtime());
			msg.setData(data);
			msg.sendToTarget();
			if (isRunning) {
				handler.postDelayed(this, 1000);
			}
		}
	};

	private void initViews() {
		// BatteryView
		batteryView = (BatteryView) findViewById(R.id.power_mgr_battery);

		// others
		tv_s = (TextView) findViewById(R.id.power_status);
		tv_s_r = (TextView) findViewById(R.id.power_status_r);
		tv_s_v = (TextView) findViewById(R.id.power_status_v);
		tv_s_t = (TextView) findViewById(R.id.power_status_t);
		tv_s_tech = (TextView) findViewById(R.id.power_status_tech);
		tv_s_rt = (TextView) findViewById(R.id.power_status_rt);

	}

	private BatteryReceiver receiver;

	@Override
	protected void onResume() {
		// 注册广播接收器
		isRunning = true;
		handler.postDelayed(timeRunnable, 1000);
		receiver = new BatteryReceiver();
		this.registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_OKAY));
		this.registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		this.registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_LOW));
		super.onResume();
	}

	@Override
	protected void onPause() {
		// 取消广播注册
		isRunning = false;
		handler.removeCallbacks(timeRunnable);
		this.unregisterReceiver(receiver);
		super.onPause();
	}

	private void initViewContent(Intent intent) {
		// 根据Intent初始化所有View内容
		final int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);// 健康
		final int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);// 电量
		final int plug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);// 接入类型
		final int max = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);// 最大电量
		final int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);// 电池状态
		final String tech = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);// 电池技术
		final int temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);// 电池温度
		final int v = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);// 电压
		// final boolean present =
		// intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);// 是否使用电池

		setHealth(health);
		setPower(level, max);
		setStatus(status, plug);
		setVoltage(v);
		setTemperature(temp / 10);
		setTechnology(tech);
	}

	private void setTechnology(String tech) {
		tv_s_tech.setText(String.format(getString(R.string.battery_status_tech), tech));
	}

	private void setTemperature(float temp) {
		tv_s_t.setText(String.format(getString(R.string.battery_status_t), temp));
	}

	private void setVoltage(int v) {
		tv_s_v.setText(String.format(getString(R.string.battery_status_v), v));
	}

	private void setStatus(int status, int plug) {
		StringBuffer s = new StringBuffer();
		if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
			s.append(getString(R.string.charging));
			batteryView.setCharging(true);
		} else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
			s.append(getString(R.string.discharging));
			batteryView.setCharging(false);
		} else if (status == BatteryManager.BATTERY_STATUS_FULL) {
			s.append(getString(R.string.full));
			batteryView.setCharging(false);
		} else if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
			s.append(getString(R.string.notcharging));
			batteryView.setCharging(false);
		} else {
			s.append(getString(R.string.unkown));
			batteryView.setCharging(false);
		}

		if (plug == BatteryManager.BATTERY_PLUGGED_AC) {
			s.append(getString(R.string.plugac));
		} else if (plug == BatteryManager.BATTERY_PLUGGED_USB) {
			s.append(getString(R.string.plugusb));
		}
		tv_s.setText(String.format(getString(R.string.battery_status), s.toString()));
	}

	private void setPower(int level, int max) {
		final int power = level * 100 / max;
		Log.v(TAG, "电池电量:" + level);
		batteryView.setPower(power);
	}

	private void setHealth(int health) {
		String status = getString(R.string.normal);
		if (health == BatteryManager.BATTERY_HEALTH_DEAD) {
			status = getString(R.string.dead);
		} else if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
			status = getString(R.string.overvoltage);
		} else if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
			status = getString(R.string.overheat);
		} else if (health == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
			status = getString(R.string.unkown);
		} else if (health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
			status = getString(R.string.undefine);
		}
		tv_s_r.setText(String.format(getString(R.string.battery_status_r), status));
	}

	class BatteryReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
				initViewContent(intent);
			} else if (Intent.ACTION_BATTERY_LOW.equals(action)) {
				// ACTION_BATTERY_LOW
			} else {
				// ACTION_BATTERY_OKAY
			}
		}
	}
}
