package rjfsdo.sharoncn.android.BDQN.AptechWeibo;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.ButtonHeader;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.HasButtonHeader;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.ResponseHolder;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.StatusParser;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboDataManager;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Constants;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.LocationProvider;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Util;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.MediaColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 发表新的微博本身可以发表图片，但是如果在Home中无法看到自己发的图片，我觉得挺遗憾的。
 * 所以我在Home中加入了微博内容图片的展示。
 * @author sharoncn
 *
 */
public class NewStatusAct extends Activity implements OnClickListener {
	protected static final String TAG = "NewStatusAct";
	private static final int REQUESTCODE = 0x021;
	private ButtonHeader header;
	private EditText content;
	private ImageView addr, pic;
	private TextView contentlen;
	private double mapAlt = 0d, mapLon = 0d;
	private LocationManager lm;
	private boolean isPublishPhoto = false, isPublishAddr = false;
	private String photoPath = null;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WeiboDataManager.MSGTYPE_PARSECONTENT:
				final Bundle bundle = msg.getData();
				if (bundle.getBoolean(WeiboDataManager.FLAG_ISSUCCESS)) {
					Util.showMsg(NewStatusAct.this, getString(R.string.public_success));
				} else {
					Util.showMsg(NewStatusAct.this, getString(R.string.getdata_fail) + bundle.getString(WeiboDataManager.FLAG_ERR_MSG));
				}
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_act_newstatus);
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		initViews();
	}

	private void initViews() {
		// Header
		header = (ButtonHeader) findViewById(R.id.newstatus_header);
		header.setHeaderTitle(R.string.saysomething);
		header.setLeftButtonBackgroundRes(R.drawable.header_btn_common);
		header.setRightButtonBackgroundRes(R.drawable.header_btn_common);
		header.setLeftButtonText(R.string.back);
		header.setRightButtonText(R.string.publish);
		header.setButtonOnClickListener(this);

		// edit
		content = (EditText) findViewById(R.id.newstatus_content);
		content.addTextChangedListener(tw);

		// ImageView
		addr = (ImageView) findViewById(R.id.newstatus_addr);
		pic = (ImageView) findViewById(R.id.newstatus_pic);
		addr.setOnClickListener(this);
		pic.setOnClickListener(this);
		changeAddrStatus(false);
		changePhotoStatus(false);

		// TextView
		contentlen = (TextView) findViewById(R.id.newstatus_contentlen);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUESTCODE && resultCode == RESULT_OK) {
			System.out.println("" + data.getDataString());
			final Uri uri = Uri.parse(data.getDataString());
			final String[] proj = { MediaColumns.DATA };
			final Cursor cursor = managedQuery(uri, proj, // Which columns to return
					null, // WHERE clause; which rows to return (all rows)
					null, // WHERE clause selection arguments (none)
					null); // Order-by clause (ascending by name)

			final int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
			cursor.moveToFirst();
			photoPath =  cursor.getString(column_index);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.newstatus_addr:// 公开地址
			isPublishAddr = !isPublishAddr;
			if (isPublishAddr) {
				final LocationProvider lp = LocationProvider.getInstance(this);
				mapAlt = lp.getAltitude();
				mapLon = lp.getLongitude();
				System.out.println("坐标:" + mapAlt + "   " + mapLon);
			} else {
				mapAlt = 0;
				mapLon = 0;
			}
			changeAddrStatus(isPublishAddr);
			// initLocation();
			break;
		case R.id.newstatus_pic:// 上传图片并发表微博
			isPublishPhoto = !isPublishPhoto;
			if (isPublishPhoto) {
				final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, REQUESTCODE);
			}
			changePhotoStatus(isPublishPhoto);
			break;
		case HasButtonHeader.ID_LEFTBUTTON:
			this.finish();
			break;
		case HasButtonHeader.ID_RIGHTBUTTON:
			// 发布带图片的微博为:statuses/upload(其中参数file指的是图片文件路径),
			// 普通微博为：statuses/update，指定图片的微博:statuses/upload_url_text
			final String text = content.getText().toString();
			if (text == null || "".equals(text)) {
				Util.showMsg(this, R.string.newstatus_editempty);
				return;
			}

			final ResponseHolder rHolder = new ResponseHolder(handler);
			rHolder.setParser(StatusParser.getInstance());

			if (!isPublishPhoto) {
				WeiboDataManager.getInstance(this).createStatus(rHolder, text, "" + mapAlt, "" + mapLon);
			} else {
				WeiboDataManager.getInstance(this).createStatus(rHolder, text, photoPath, "" + mapAlt, "" + mapLon);
			}
			break;
		}
	}

	private void changePhotoStatus(boolean isPublishPhoto) {
		if(isPublishPhoto){
			pic.setBackgroundResource(R.drawable.newblog_pic_jbit);
		}else{
			pic.setBackgroundResource(R.drawable.newblog_pic_jbit_n);
		}
	}

	private void changeAddrStatus(boolean isPublishAddr) {
		if(isPublishAddr){
			pic.setBackgroundResource(R.drawable.newblog_pos_jbit);
		}else{
			pic.setBackgroundResource(R.drawable.newblog_pos_jbit_n);
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
			int end = content.getSelectionEnd();
			int len = s.length() - 140;
			if (len > 0) {
				Editable cPart = s.delete(end - len, end);
				content.setText(cPart);
			}
		}
	};

	// 定位
	private LocationListener locationLst = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			mapAlt = location.getAltitude();
			mapLon = location.getLongitude();
			System.out.println("lat:" + mapAlt + "  long:" + mapLon);
		}

		@Override
		public void onProviderDisabled(String provider) {
			System.out.println("onProviderDisabled:" + provider);
		}

		@Override
		public void onProviderEnabled(String provider) {
			System.out.println("onProviderEnabled:" + provider);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			System.out.println("onStatusChanged:" + provider + "   status:" + status);
		}
	};

	// 谷歌提供的定位服务貌似不起作用，无法定位，所以还是用百度的定位服务
	@SuppressWarnings("unused")
	private void initLocation() {
		System.out.println("initLocation");
		Criteria criteria = new Criteria();
		// 设置粗略精确度
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		// 设置是否需要返回海拔信息
		criteria.setAltitudeRequired(false);
		// 设置是否需要返回方位信息
		criteria.setBearingRequired(false);
		// 设置是否允许付费服务
		criteria.setCostAllowed(false);
		// 设置电量消耗等级
		criteria.setPowerRequirement(Criteria.POWER_HIGH);
		// 设置是否需要返回速度信息
		criteria.setSpeedRequired(false);
		// 第二个参数设置为false时，不管当前的那个provider是否可用，都需要进行查找，并根据条件设为最优
		String provider = lm.getBestProvider(criteria, true);
		System.out.println("最佳provider：" + provider);

		// 根据当前provider对象获取最后一次位置信息
		Location currentLocation = lm.getLastKnownLocation(provider);

		if (currentLocation == null) {
			lm.requestLocationUpdates(provider, 5000, 2000, locationLst);
		} else {
			System.out.println(currentLocation.toString());
		}
	}
}
