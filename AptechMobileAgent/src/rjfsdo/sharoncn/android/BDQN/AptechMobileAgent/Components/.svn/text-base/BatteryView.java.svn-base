package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Components;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
//import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 电池View，提供设置电量和是否充电的接口，将电池的状态显示，特效等封装起来。
 * @author sharoncn
 *
 */
public class BatteryView extends LinearLayout {
	protected static final String TAG = "BatteryView";
	private static int layoutRes = R.layout.components_battery;
	private static Context mContext;
	private static LayoutInflater inflater;
	private int mPower = 0;// 电量
	private boolean isCharging = false;// 是否正在充电
	private ImageView iv_border, iv_power, iv_charging;
	private FrameLayout container;
	private TextView tv_power;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			notifyStatusChange();
			super.handleMessage(msg);
		}
	};

	public BatteryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (mContext == null)
			mContext = context;
		if (inflater == null)
			inflater = LayoutInflater.from(context);
		initViews();
	}

	private void initViews() {
		final View view = inflater.inflate(layoutRes, null);
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(params);
		this.setGravity(Gravity.CENTER);
		this.addView(view);

		iv_border = (ImageView) view.findViewById(R.id.battery_border);
		iv_power = (ImageView) view.findViewById(R.id.battery_power);
		container = (FrameLayout) view.findViewById(R.id.battery_container);
		tv_power = (TextView) findViewById(R.id.tv_power);
		iv_charging = (ImageView) findViewById(R.id.battery_charging);
	}

	/**
	 * 设置是否处于充电状态状态
	 * 
	 * @param isCharging
	 */
	public void setCharging(boolean isCharging) {
		if (isCharging) {
			startCharging();
		} else {
			stopCharging();
		}
	}

	private void startCharging() {
		this.isCharging = true;
		if (chargingThread != null && chargingThread.isAlive()) {
			chargingThread.interrupt();
			chargingThread = null;
		}
		chargingThread = new ChargingThread();
		chargingThread.start();
	}

	private void stopCharging() {
		this.isCharging = false;
		notifyStatusChange();
	}

	private Thread chargingThread;

	class ChargingThread extends Thread {
		@Override
		public void run() {
			while (isCharging) {
				handler.sendEmptyMessage(0);
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	};

	/**
	 * 设置电量
	 * 
	 * @param power
	 *            电量0-100
	 */
	public void setPower(int power) {
		if (power < 0) {
			this.mPower = 0;
		} else if (power > 100) {
			this.mPower = 100;
		} else {
			this.mPower = power;
		}
		notifyStatusChange();
	}

	enum Status {
		charging, normal, lowPower
	}

	private int currentStep = 0;// 充电状态动画效果当前显示电量未知
	private int eachStep = 0;// 每次增长数量
	private Status status = Status.normal;// 标示当前图片处于什么状态，当改变图片时改变标示
	private int eachLevelWidth = 0;// 总电量/100

	private void notifyStatusChange() {
		if (eachStep == 0) {
			int width = container.getWidth();
			if (width == 0) {
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						notifyStatusChange();
					}
				}, 1000);
				return;
			}
			eachLevelWidth = width / 100;
			eachStep = width / 5;
		}

		if (isCharging) {
			if (status != Status.charging)
				chargingStatus();
			currentStep += eachStep;
			moveNextStep(currentStep);
			//Log.v(TAG, "当前step:" + currentStep + "  eachStep:" + eachStep);
		} else {
			if (mPower >= 30) {
				// 电量充足 设置图片为绿色
				normalStatus();
			} else {
				// 电量不足 设置图片为红色
				lowPowerStatus();
			}
			// 设置电量图片大小
			setPowerValue(mPower * eachLevelWidth);
		}
	}

	private void setPowerValue(int value) {
		LayoutParams params = new LayoutParams(value, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		iv_power.setLayoutParams(params);
	}

	// 充电状态
	private void chargingStatus() {
		status = Status.charging;
		iv_border.setBackgroundResource(R.drawable.battery_green);
		iv_power.setBackgroundResource(R.drawable.battery_full);
		iv_charging.setVisibility(View.VISIBLE);
		tv_power.setText(mPower + "%");
	}

	// 非充电状态，非低电量
	private void normalStatus() {
		status = Status.normal;
		iv_border.setBackgroundResource(R.drawable.battery_nomal);
		iv_power.setBackgroundResource(R.drawable.battery_full);
		iv_charging.setVisibility(View.GONE);
		tv_power.setText(mPower + "%");
	}

	// 非充电状态，低电量
	private void lowPowerStatus() {
		status = Status.lowPower;
		iv_border.setBackgroundResource(R.drawable.battery_nomal);
		iv_power.setBackgroundResource(R.drawable.battery_full_yellow);
		iv_charging.setVisibility(View.GONE);
		tv_power.setText(mPower + "%");
	}

	private void moveNextStep(int nextStep) {
		final int powerWidth = mPower * eachLevelWidth;
		if (nextStep > powerWidth) {
			nextStep = powerWidth;
		}
		// 设置电池full图片的位置
		setPowerValue(nextStep);

		if (nextStep == powerWidth) {
			currentStep = -eachStep;
		}
	}
}
