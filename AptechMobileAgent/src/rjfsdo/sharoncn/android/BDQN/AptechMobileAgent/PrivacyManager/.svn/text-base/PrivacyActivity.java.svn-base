package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.PrivacyManager;

import java.util.Collections;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Adapter.PrivacyAppsAdapter;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Components.CheckPasswordDialog;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Components.SetupPasswordDialog;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Db.PrivacyModel;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Models.AppPackageInfo;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.AppPackageInfoComparator;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Constants;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.MD5Encoder;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Util;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class PrivacyActivity extends Activity implements OnClickListener {
	private static PackageManager mPkgManager;
	private ListView list;
	private List<AppPackageInfo> apps;
	private PrivacyAppsAdapter adapter;
	private static final int MSGTYPE_DATA = 0x10;
	private static final String TAG = "PrivacyActivity";
	private ProgressDialog pd;
	private String password = null;
	// private PrivacyService mService;
	private static ContentResolver mResolver;
	private static PrivacyModel dao;
	private static SharedPreferences sp;
	//private MyConn conn;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSGTYPE_DATA:
				if (apps != null) {
					adapter.prepareData(apps);
					list.setAdapter(adapter);
				}
				if (pd != null) {
					pd.dismiss();
				}
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_privacy);
		if (mPkgManager == null)
			mPkgManager = this.getPackageManager();
		if (mResolver == null)
			mResolver = getContentResolver();
		if (sp == null)
			sp = PreferenceManager.getDefaultSharedPreferences(this);
		if(dao == null)
			dao = PrivacyModel.getInstance(this, mResolver);
		initViews();
		prepare();

	}

	private void prepare() {
		if (password == null)
			password = sp.getString(Constants.PREFERENCE_PW, null);
		if (password == null) {
			// 没有设置密码
			setupPassword();
		} else {
			checkPassword();
		}
	}

	private void checkPassword() {
		// 检查密码
		final CheckPasswordDialog dialog = new CheckPasswordDialog(this);
		final CheckPasswordListener listener = new CheckPasswordListener(dialog);
		dialog.setOnClickListener(listener).setTitle(R.string.input_pw).setCancelable(false).show();
	}

	class CheckPasswordListener implements DialogInterface.OnClickListener {
		private CheckPasswordDialog mDialog;

		public CheckPasswordListener() {
			this(null);
		}

		public CheckPasswordListener(CheckPasswordDialog dialog) {
			this.mDialog = dialog;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				final String pw = (mDialog).getEditTextContent();
				if (pw == null || "".equals(pw)) {
					dialog.dismiss();
				}
				if (!MD5Encoder.encode(pw).equals(password)) {
					Util.showMsg(PrivacyActivity.this, R.string.pw_wrong);
					return;
				} else {
					dialog.dismiss();
					initData();
				}
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				dialog.dismiss();
				finish();
				break;
			}
		}
	};

	private void setupPassword() {
		// 设置密码
		final SetupPasswordDialog dialog = new SetupPasswordDialog(this);
		final CreatePasswordListener listener = new CreatePasswordListener(dialog);
		dialog.setOnClickListener(listener).setTitle(R.string.createnewpw).setCancelable(false).show();
	}

	class CreatePasswordListener implements DialogInterface.OnClickListener {
		private SetupPasswordDialog mDialog;

		public CreatePasswordListener() {
			this(null);
		}

		public CreatePasswordListener(SetupPasswordDialog dialog) {
			this.mDialog = dialog;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				//我觉得隐私保护不应该使用默认密码。使用默认密码之后，如果用户使用时没有修改密码。
				//任何人都可以进入隐私保护中添加删除锁保护。作为用户来将，偷懒都喜欢吧。能取消又不影响使用
				//必然点取消。强制用户第一次进入输入一次密码可能更好一些。
				final String pw = mDialog.getPassword();
				final String conform = mDialog.getConform();
				if (pw == null || conform == null || "".equals(pw) || "".equals(conform)) {
					Util.showMsg(PrivacyActivity.this, R.string.input_err);
					return;
				}
				if(!pw.equals(conform)){
					Util.showMsg(PrivacyActivity.this, R.string.comform_err);
					return;
				}
				final Editor editor = sp.edit();
				final String encodePw = MD5Encoder.encode(pw);
				Log.v(TAG, "加密之后的密码:" + encodePw);
				editor.putString(Constants.PREFERENCE_PW, encodePw);
				if (!editor.commit()) {
					Util.showMsg(PrivacyActivity.this, R.string.commit_fail);
					dialog.dismiss();
					finish();
				} else {
					dialog.dismiss();
					initData();
				}
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				dialog.dismiss();
				finish();
				break;
			}
		}
	};

	private void initData() {
		// bind到service
//		conn = new MyConn();
//		Intent service = new Intent(this, PrivacyService.class);
//		this.bindService(service, conn, Service.BIND_AUTO_CREATE);
		// 获取应用列表
		pd = Util.showProgressDialog(this, R.string.loading);
		new Thread() {
			@Override
			public void run() {
				synchronized (this) {
					apps = Util.getAppsList(PrivacyActivity.this);
					if (apps != null) {
						Collections.sort(apps, AppPackageInfoComparator.getInstance());
					}
				}
				handler.sendEmptyMessage(MSGTYPE_DATA);
			}
		}.start();
	}

	private void initViews() {
		list = (ListView) findViewById(R.id.privacy_mgr_list);
		adapter = new PrivacyAppsAdapter(this);
		adapter.setLockOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		final int position = adapter.getCurrentPosition();
		final String pkgName = ((AppPackageInfo) adapter.getItem(position)).getPackageName();
		Log.v(TAG, "位置:" + position + "被点击");
		if (adapter.isProtected(position)) {
			addLock(pkgName);
		} else {
			removeLock(pkgName);
		}
	}

//	class MyConn implements ServiceConnection {
//
//		@Override
//		public void onServiceConnected(ComponentName name, IBinder service) {
//			// mService = ((MyBinder) service).getService();
//		}
//
//		@Override
//		public void onServiceDisconnected(ComponentName name) {
//		}
//	}

	/**
	 * 添加保护
	 * 
	 * @param pkgName
	 */
	public void addLock(String pkgName) {
		// 添加保护
		
//		final ContentValues values = new ContentValues();
//		values.put("packageName", pkgName);
//		mResolver.insert(Uri.parse(Constants.PRIVACY_CONTENT_URI), values);
	}

	/**
	 * 删除一条保护记录
	 * 
	 * @param pkgName
	 */
	public void removeLock(String pkgName) {
		// 删除保护
		dao.delete(pkgName);
		//mResolver.delete(Uri.parse(Constants.PRIVACY_CONTENT_URI), "packageName=?", new String[] { pkgName });
	}

	@Override
	protected void onDestroy() {
//		if (conn != null)
//			unbindService(conn);
		super.onDestroy();
	}
}
