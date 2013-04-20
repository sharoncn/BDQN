package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Service;

import java.util.Date;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Interfaces.ITrafficService;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Models.TrafficInfo;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Receivers.ShutdownReceiver;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.TrafficManager.TrafficModel;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.TrafficStats;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class TrafficService extends Service {
	private static final String TAG = "TrafficService";
	public static final String ACTION_REFRESH = "rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.ACTION_REFRESH";
	public boolean flag = true;
	private static final int ONEMUNITE = 60 * 1000;

	private Date lastTime;
	private static TrafficModel dao;
	private static ShutdownReceiver shutdownReceiver;
	private static TrafficBinder binder;

	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		if (dao == null)
			dao = TrafficModel.getInstance(this);

		if (binder == null)
			binder = new TrafficBinder();

		if (shutdownReceiver == null)
			shutdownReceiver = new ShutdownReceiver(binder);

		// 注册关机接收器
		registerShutdown();

		lastTime = new Date();
		checkRecord();
		new CountThread().start();
		super.onCreate();
	}

	private void registerShutdown() {
		final IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SHUTDOWN);
		registerReceiver(shutdownReceiver, filter);
	}

	private void checkRecord() {
		if (!dao.recordExists()) {
			dao.addNewDay(getTraffic());
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	/**
	 * 仅关机时调用，调用之后服务终止
	 */
	public void onSaveCount() {
		Log.v(TAG, "保存流量记录");
		flag = false;
		final TrafficInfo traffic = getTraffic();
		dao.setEndTraffic(traffic);
	}

	class CountThread extends Thread {

		@Override
		public void run() {
			while (flag) {
				final Date now = new Date();
				if (now.getTime() - lastTime.getTime() >= ONEMUNITE) {
					final TrafficInfo traffic = getTraffic();
					if (!dao.recordExists()) {
						Log.v(TAG, "刷新数据:记录不存在，创建");
						dao.addNewDay(traffic);
					} else {
						Log.v(TAG, "刷新数据:记录已存在，更新");
						dao.setEndTraffic(traffic);
					}
					final Intent intent = new Intent(ACTION_REFRESH);
					sendBroadcast(intent);
					synchronized (lastTime) {
						lastTime = now;
					}
				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public class TrafficBinder extends Binder implements ITrafficService {
		public TrafficService getService() {
			return TrafficService.this;
		}

		@Override
		public void onShutdown() {
			onSaveCount();
		}
	}

	@Override
	public void onDestroy() {
		Log.v(TAG, "TrafficService onDestroy");
		flag = false;
		// 取消关机注册
		if (shutdownReceiver != null)
			unregisterReceiver(shutdownReceiver);
		super.onDestroy();
	}

	private static double lastRx = 0;
	private static double lastTx = 0;
	private static long lastRxPkg = 0;
	private static long lastTxPkg = 0;

	private TrafficInfo getTraffic() {
		// 开机获得流量会重置
		final TrafficInfo traffic = new TrafficInfo();

		final double thisRx = TrafficStats.getMobileRxBytes();
		final double thisTx = TrafficStats.getMobileTxBytes();
		final long thisRxPkg = TrafficStats.getMobileRxPackets();
		final long thisTxPkg = TrafficStats.getMobileTxPackets();

		final double download = thisRx - lastRx;
		final double upload = thisTx - lastTx;
		final long downloadPkg = thisRxPkg - lastRxPkg;
		final long uploadPkg = thisTxPkg - lastTxPkg;

		lastRx = thisRx;
		lastTx = thisTx;
		lastRxPkg = thisRxPkg;
		lastTxPkg = thisTxPkg;

		Log.i(TAG, "download:" + download);
		Log.i(TAG, "upload:" + upload);
		Log.i(TAG, "downloadPkg:" + downloadPkg);
		Log.i(TAG, "uploadPkg:" + uploadPkg);
		traffic.setStartAll(0);
		traffic.setStartDownload(0);
		traffic.setStartUpload(0);
		traffic.setStartPackages(0);
		traffic.setEndAll(download + upload);
		traffic.setEndDownload(download);
		traffic.setEndUpload(upload);
		traffic.setEndPackages(downloadPkg + uploadPkg);
		return traffic;
	}

}
