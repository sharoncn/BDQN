package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.AppsManager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Adapter.AppInstalledAdapter;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Components.AppHeader;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Components.Menu;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Models.AppPackageInfo;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.AppPackageInfoComparator;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Util;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

/**
 * 1.本来想用AIDL调用deletePackage来删除应用的，但是没有权限。
 * 2.现在使用传递意图，利用广播接收器接收ACTION_PACKAGE_REMOVED，得到反馈。
 * 3.使用运行时直接掉命令实现，有人说这种方法。应该可以实现，没测试。
 * 4.不知道使用JNI直接调底层能不能实现。没有frameworks\base\core\java\android\app\ApplicationPackageManager的源代码
 * 以后可以分析下。
 * 5.不知道调用调试桥能不能实现。
 * 
 * @author sharoncn
 *
 */
public class AppsManagerActivity extends Activity implements OnClickListener {
	public static final String TAG = "AppsManagerActivity";
	private static final int FLAG_DATA_OK = 0x01;
	private static final int FLAG_DELETE_OK = 0x02;
	private static final int FLAG_DELETE_FIAL = 0x03;
	protected static final int CODE_REQUEST = 0x10;
	private ListView list;
	private Menu menu;
	private AppHeader header;
	private AppInstalledAdapter adapter;
	private static PackageManager mPackageManager;
	private ArrayList<AppPackageInfo> allData = new ArrayList<AppPackageInfo>();
	private ProgressDialog pd;
	private int uninstallPosition = -1;// 当前删除的应用在Adapter中的位置

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (pd != null)
				pd.dismiss();
			switch (msg.what) {
			case FLAG_DATA_OK:
				Collections.sort(allData, AppPackageInfoComparator.getInstance());
				adapter.prepareData(allData);
				list.setAdapter(adapter);
				break;
			case FLAG_DELETE_OK:
				allData.remove(adapter.getItem(uninstallPosition));
				adapter.removePositio(uninstallPosition);
				Util.showMsg(AppsManagerActivity.this, R.string.uninstall_success);
				break;
			case FLAG_DELETE_FIAL:
				Util.showMsg(AppsManagerActivity.this, R.string.uninstall_fail);
				break;
			}

			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_appmanager);

		final IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		filter.addDataScheme("package");
		this.registerReceiver(receiver, filter);
		if (mPackageManager == null)
			mPackageManager = this.getPackageManager();
		initViews();
		initData();
	}

	@Override
	protected void onDestroy() {
		this.unregisterReceiver(receiver);
		super.onDestroy();
	}

	private void initData() {
		pd = Util.showProgressDialog(this, R.string.loading);
		final ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
		final MemoryInfo outInfo = new MemoryInfo();
		am.getMemoryInfo(outInfo);
		header.setMemoryRemainingSize(outInfo.availMem);
		new PrepareDataThread().start();
	}

	private void initViews() {
		// List
		list = (ListView) findViewById(R.id.app_mgr_list);
		adapter = new AppInstalledAdapter(this);
		adapter.setOnClickListener(listener);
		// Menu
		menu = (Menu) findViewById(R.id.app_mgr_menu);
		menu.setButtonOnClickListener(this);
		// Header
		header = (AppHeader) findViewById(R.id.app_mgr_header);
	}

	/**
	 * ListItem中的安装卸载监听器
	 */
	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int position = -1;
			AppPackageInfo app = null;
			switch (v.getId()) {
			case AppInstalledAdapter.ID_BTN_OPEN:
				position = adapter.getCurrentPosition(v);
				app = (AppPackageInfo) adapter.getItem(position);
				openApp(app.getPackageName());
				break;
			case AppInstalledAdapter.ID_BTN_UNINSTALL:
				position = adapter.getCurrentPosition(v);
				uninstallPosition = position;
				app = (AppPackageInfo) adapter.getItem(position);
				Util.uninstallApp(AppsManagerActivity.this, app.getPackageName());
				// pd = Util.showProgressDialog(AppsManagerActivity.this,
				// R.string.deleting);
				// new DeletePackageThread(AppsManagerActivity.this,
				// app.getPackageName()).start();
				break;
			}
		}
	};

	// 打开应用程序
	private void openApp(String pkgName) {
		Intent intent = this.getPackageManager().getLaunchIntentForPackage(pkgName);
		if (intent == null) {
			Util.showMsg(this, R.string.intentisnull);
			return;
		}
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case Menu.BTN_ID_ALL:
			if (menu.getActiveBtn() != Menu.BTN_ID_ALL) {
				adapter.prepareData(allData);
				adapter.notifyDataSetChanged();
			}
			break;
		case Menu.BTN_ID_NOSYS:
			if (menu.getActiveBtn() != Menu.BTN_ID_NOSYS) {
				final ArrayList<AppPackageInfo> part = getPartOfAllData(false);
				adapter.prepareData(part);
				adapter.notifyDataSetChanged();
			}
			break;
		case Menu.BTN_ID_SYS:
			if (menu.getActiveBtn() != Menu.BTN_ID_SYS) {
				final ArrayList<AppPackageInfo> part = getPartOfAllData(true);
				adapter.prepareData(part);
				adapter.notifyDataSetChanged();
			}
			break;
		}
	}

	private ArrayList<AppPackageInfo> getPartOfAllData(boolean isSys) {
		final ArrayList<AppPackageInfo> part = new ArrayList<AppPackageInfo>();
		final int count = allData.size();
		for (int i = 0; i < count; i++) {
			final AppPackageInfo app = allData.get(i);
			if (app.isSysFlag() ^ isSys) {
				part.add(app);
			}
		}
		return part;
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
				final Uri uri = Uri.parse(intent.getDataString());
				final String pkgName = uri.getEncodedSchemeSpecificPart();
				
				final AppPackageInfo app = (AppPackageInfo) adapter.getItem(uninstallPosition);
				if (app.getPackageName().equals(pkgName)) {
					int flag = -1;
					flag = intent.getExtras().getBoolean(Intent.EXTRA_DATA_REMOVED) ? FLAG_DELETE_OK
							: FLAG_DELETE_FIAL;
					mHandler.sendEmptyMessage(flag);
				}

			}
		}
	};

	// 删除应用的线程
	// 出现安全异常,这也是个知识点吧,保留在这里
	// Caused by: java.lang.SecurityException: Neither user 10027 nor current
	// process has android.permission.DELETE_PACKAGES.
	//
	// 有时候我们可能在Androidmanifest.xml中声明了某些权限，比如说 android.permission.SET_TIME
	// 可能出现ERROR/AndroidRuntime(3759): java.lang.SecurityException: Neither user
	// 10024 nor current process has android.permission.SET_TIME
	// 这样的错误，10024是系统的UID，所以如果你没有系统的UID使用Platform的APK签名是没有权限使用相关方法的。
	// 对于这种情况考虑
	// 1.拥有Root权限，这里Android123推荐大家发掘自己的潜能，寻找0day漏洞。
	// 2.设置你APK为UID和system的一样，但必须找到你当前固件平台的签名，参考
	// platform.x509.pem和platform.pk8的用处

	/**
	 * @author sharoncn
	 * @deprecated
	 */
	@Deprecated
	class DeletePackageThread extends Thread {
		private String pkgName;
		private Context mContext;

		public DeletePackageThread(Context context, String pkgName) {
			this.pkgName = pkgName;
			this.mContext = context;
		}

		@Override
		public void run() {
			final PackageDeleteObserver observer = new PackageDeleteObserver();
			final PackageManager mPkgMgr = mContext.getPackageManager();
			try {
				final Method method = PackageManager.class.getDeclaredMethod("deletePackage", String.class,
						IPackageDeleteObserver.class, int.class);
				if (method != null) {
					method.invoke(mPkgMgr, pkgName, observer, 0);
				} else {
					mHandler.sendEmptyMessage(FLAG_DELETE_FIAL);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// .deletePackage(pkgName, observer, 0);
		}
	}

	// 包删除回调
	/**
	 * @author sharoncn
	 * @deprecated
	 */
	@Deprecated
	class PackageDeleteObserver extends IPackageDeleteObserver.Stub {
		@Override
		public void packageDeleted(boolean succeeded) {
			final int flag = succeeded ? FLAG_DELETE_OK : FLAG_DELETE_FIAL;
			mHandler.sendEmptyMessage(flag);
		}
	}

	// 获得应用大小的回调
	class PkgSizeObserver extends IPackageStatsObserver.Stub {
		private int invokeCount = 0;

		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
			synchronized (allData) {
				invokeCount++;
				final String pkgName = pStats.packageName;
				final long cachesize = pStats.cacheSize;
				final long datasize = pStats.codeSize;
				final long codesize = pStats.codeSize;
				final long totalSize = cachesize + datasize + codesize;
				// Log.v(TAG, "包名:" + pkgName + "    应用大小:" + totalSize);
				// Log.i(TAG, "调用次数:" + invokeCount + "   data.size:" +
				// allData.size());
				final int count = allData.size();
				for (int i = 0; i < count; i++) {
					final AppPackageInfo app = allData.get(i);
					if (app.getPackageName().equals(pkgName)) {
						app.setSize(totalSize);
						break;
					}
				}
				if (invokeCount >= count) {
					// Log.v(TAG, "发送消息");
					mHandler.sendEmptyMessage(FLAG_DATA_OK);
				}
			}
		}
	}

	// 获得应用大小的线程
	class PrepareDataThread extends Thread {
		@Override
		public void run() {
			allData = (ArrayList<AppPackageInfo>) Util.getAppsList(AppsManagerActivity.this);
			try {
				Method method = PackageManager.class.getDeclaredMethod("getPackageSizeInfo", String.class,
						IPackageStatsObserver.class);
				if (method != null) {
					final PkgSizeObserver pkgSizeObserver = new PkgSizeObserver();
					for (AppPackageInfo app : allData) {
						final String pkgName = app.getPackageName();
						// Log.i(TAG, "pkgname:" + pkgName);
						if (pkgSizeObserver == null) {
							Log.i(TAG, "pkgSizeObserver is null");
						}
						if (mPackageManager == null)
							Log.i(TAG, "mPackageManager is null");
						method.invoke(mPackageManager, new Object[] { pkgName, pkgSizeObserver });
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
