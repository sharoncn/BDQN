package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.FlashManager;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.widget.RemoteViews;

public class FlashWidget extends AppWidgetProvider {
	public static final String ACTION_CLICK = "rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.click";
	public static final String ACTION_MOVE = "rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.move";
	public static final String ACTION_RESPONSE = "rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.response";
	private static RemoteViews mView;
	private static boolean isLighting = false;
	private static AppWidgetManager mAppWidgetManager;

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("接收到ACTION");
		if (mAppWidgetManager == null)
			mAppWidgetManager = AppWidgetManager.getInstance(context);
		// 当接收到action
		final String action = intent.getAction();

		if (ACTION_CLICK.equals(action)) {
			System.out.println("Action is " + ACTION_CLICK);
			doClickActon(context);
		} else if (ACTION_MOVE.equals(action)) {
			// TODO move事件
			//希望做成类似于360那样可以随意跟随手指移动的widget，可以放置在任意位置
			//不过貌似还是很有难度的。以后再考虑吧。
			System.out.println("Action is " + ACTION_MOVE);

		} else if (ACTION_RESPONSE.equals(action)) {
			// FlashActivity Action
			isLighting = !isLighting;
			refreshWidgetStatus(context);
			System.out.println("Action is " + ACTION_RESPONSE);
		} else {
			System.out.println("Action is " + action);
		}

		super.onReceive(context, intent);
	}

	//ClickActon
	private void doClickActon(Context context) {
		isLighting = !isLighting;
		// 更新桌面手电筒状态
		refreshWidgetStatus(context);
		//因为如果没有摄像头，启动Activity，那么activity返回时会发送广播ACTION_RESPONSE
		//此类接收到这个Action就会调整状态并重置isLighting。所以凡ACTION_CLICK，只需要判断有没有
		//闪光灯，需不需要打开Activity
		final boolean hasFlash = hasFlash(context);
		if (isLighting) {
			// 获取Camera有没有Flash功能
			if (!hasFlash) {
				System.out.println("No Flash");
				openLightAct(context);
				return;
			}
		}
		if (hasFlash) {
			lightCameraFlash(isLighting);
		}
	}

	//刷新widget状态
	private void refreshWidgetStatus(Context context) {
		int[] appWidgetIds = mAppWidgetManager.getAppWidgetIds(new ComponentName(context, FlashWidget.class));
		for (int appWidgetId : appWidgetIds) {
			updateAppWidget(context, mAppWidgetManager, appWidgetId);
		}
	}

	//打开FlashActivity
	private void openLightAct(Context context) {
		// 如果没有，打开startForResult启动FlashActivity，并将屏幕亮度设置为最亮
		Intent intent = new Intent(context, FlashActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	private static String flashMode = "";
	private void lightCameraFlash(boolean isLighting) {
		// 如果有闪光灯,设置Camera.Parameters.FLASH_MODE_TORCH
		Camera camera = Camera.open();
		Parameters params = camera.getParameters();
		if(isLighting){
			flashMode = params.getFlashMode();
			params.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(params);
		}else{
			params.setFlashMode(flashMode);
			camera.setParameters(params);
		}
	}

	//判断是否有闪光灯
	//这里判断的并不准确，根据硬件配置获取是否有闪光灯。我的M9就有闪光灯的硬件配置，但是并没有真正的闪光灯
	private boolean hasFlash(Context context) {
		boolean hasFlash = false;

		final FeatureInfo[] features = context.getPackageManager().getSystemAvailableFeatures();
		if (features != null) {
			final int count = features.length;

			for (int i = 0; i < count; i++) {
				final FeatureInfo feature = features[i];
				if (PackageManager.FEATURE_CAMERA_FLASH.equals(feature.name)) {
					hasFlash = true;
				}
			}
		}
		return hasFlash;
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// 当update
		System.out.println("onUpdate");
		final int count = appWidgetIds.length;
		for (int i = 0; i < count; i++) {
			int appWidgetId = appWidgetIds[i];
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	/**
	 * 真正更新Widget状态在这里进行
	 * @param context
	 * @param appWidgeManger
	 * @param appWidgetId
	 */
	private void updateAppWidget(Context context, AppWidgetManager appWidgeManger, int appWidgetId) {
		System.out.println("updateAppWidget");
		mView = new RemoteViews(context.getPackageName(), R.layout.layout_widget_flash);
		Intent intentClick = new Intent(ACTION_CLICK);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
		mView.setOnClickPendingIntent(R.id.widget_img, pendingIntent);
		mView.setImageViewResource(R.id.widget_img, isLighting ? R.drawable.lighting : R.drawable.light);
		appWidgeManger.updateAppWidget(appWidgetId, mView);
	}
}
