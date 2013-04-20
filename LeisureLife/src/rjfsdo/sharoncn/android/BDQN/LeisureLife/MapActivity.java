package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.MapHeader;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Listener.MySearchListener;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Constants;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.MapManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.MyVerlays;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.platform.comapi.basestruct.GeoPoint;

/**
 * 地图Activity
 * 
 * @author sharoncn
 * 
 */
public class MapActivity extends BaseActivity implements OnClickListener {
	public static final String FLAG_POSITION = "position";
	public static final String FLAG_TITLE = "title";
	public static final String FLAG_CONTENT = "content";
	private static final String TAG = "MapActivity";
	private MapHeader header;
	private MapView mMapView;
	private MapController mMapController = null;
	private BMapManager mMapManager = null;
	private String x = null, y = null;
	private String title = "", content = "";
	private GeoPoint start, end;
	private MKSearch mMKSearch;
	private LocationClient mLocClient;
	/**
	 * 是否正在搜索线路
	 */
	private boolean isSearching = false;
	private ProgressDialog pd;
	private MySearchListener searchListener;

	private Handler handler = new Handler() {
		private boolean firstTime = true;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			//搜索线路返回
			case MySearchListener.FLAG_SEARCH_ROUTE:
				isSearching = false;
				if (pd != null) {
					pd.dismiss();
				}
				if (!msg.getData().getBoolean(MySearchListener.FLAG_ISSUCCESS))
					Util.showMsg(MapActivity.this, R.string.search_fail);
				break;
			//解析地址返回
			case MySearchListener.FLAG_PARSE_ADDR:
				Bundle data = msg.getData();
				//第一次获取本地地址
				if (firstTime) {
					//如果没有成功获取结果直接返回
					if(!data.getBoolean(MySearchListener.FLAG_ISSUCCESS))return;
					if (MapManager.localAddr == null)
						MapManager.localAddr = data.getString(MySearchListener.FLAG_ADDR);
					firstTime = false;
				} else {
					if(!data.getBoolean(MySearchListener.FLAG_ISSUCCESS))return;
					//如果是相同的城市则可以公交换乘
					if (canTransit(MapManager.localAddr,data.getString(MySearchListener.FLAG_ADDR))) {
						transitRun(start, end);
					} else {
						isSearching = false;
						if (pd != null) {
							pd.dismiss();
						}
						Util.showMsg(MapActivity.this, R.string.differenttcity);
					}
				}
				break;
			}
			super.handleMessage(msg);
		}
	};

	private boolean canTransit(String localAddr, String targetAddr) {
		//大于Constants.MAX_DISTANCE千米的距离不能换乘
		Log.i(TAG, "本地地址:" + localAddr);
		Log.i(TAG, "目的地地址:" + targetAddr);
		if(localAddr.equalsIgnoreCase(targetAddr)){
			return true;
		}
		return false;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_map);

		Bundle data = getIntent().getExtras();
		if (data.containsKey(FLAG_POSITION)) {
			String[] p = data.getString(FLAG_POSITION).split(";");
			x = p[0];
			y = p[1];
			//x和y非常重要所以在这里进行一个判断，防止意外发生
			if(x == null || x.equals("null") || x.equals("") || y == null || y.equals("null") || y.equals("")){
				Util.showMsg(this, R.string.norightposition);
				return;
			}
		}
		if (data.containsKey(FLAG_TITLE)) {
			title = data.getString(FLAG_TITLE);
		}
		if (data.containsKey(FLAG_CONTENT)) {
			content = data.getString(FLAG_CONTENT);
		}
		//之前直接在layout文件中声明MapView控件，会造成屏幕黑一会,应该是MapView初始化造成的。测试过程中，
		//发现MapView初始化时会创建一个名为bdcltb09的表，创建过程很长，而且最终会发生错误，错误内容为：
		//Failure 1 (table bdcltb09 already exists) on 0x2d7518 when preparing 'CREATE TABLE bdcltb09(id CHAR(40) PRIMARY KEY,time DOUBLE,tag DOUBLE, type DOUBLE , ac INT);'
		//很容易造成应用进程不响应。但是，地图初始化必须在主线程中进行。所以修改为不在layout中定义MapView，而
		//直接手动创建一个MapView的对象，并初始化，而这个过程放置在向handler投递的runnable中，在投递之前，让
		//ProgressDialog显示。先让地图Activity显示出来，其他的初始化在地图显示出来之后再做，避免用户触摸事件造成应用崩溃
		pd = Util.showProgressDialog(this, getString(R.string.data_loading));
		pd.setCancelable(false);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				init();
			}
		},1000);
	}

	@SuppressWarnings("deprecation")
	private void init() {
		mMapManager = MapManager.getInstance(this).getMapManager();
		header = (MapHeader) findViewById(R.id.map_header);
		FrameLayout mMapContainer = (FrameLayout) findViewById(R.id.map_container);
		mMapView = new MapView(this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mMapView.setLayoutParams(params);
		mMapContainer.addView(mMapView);
		//mMapView = (MapView) findViewById(R.id.bmapView);
		Log.i(TAG, "init mMapView finished");
		header.setOnButtonClickListener(this);
		searchListener = new MySearchListener(this, mMapView, handler);
		
		// 初始化MapView
		mMapView.setDoubleClickZooming(true);
		mMapView.regMapViewListener(mMapManager, mMapListener);
		mMapView.displayZoomControls(true);
		// mMapView.setOnTouchListener(null);
		mMapController = mMapView.getController();
		// initMapView();
		// mMapView.setTraffic(true);
		// mMapView.setSatellite(true);
		Log.i(TAG, x + ":" + y);
		GeoPoint point = new GeoPoint((int) Double.parseDouble(x), (int) Double.parseDouble(y));
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);// 设置地图中心点
		mMapController.setZoom(12);
		mMapController.enableClick(true);

		Drawable marker = getResources().getDrawable(R.drawable.marker);
		// myLocationOverlay = new MyLocationOverlay(mMapView);
		MyVerlays overlays = new MyVerlays(marker);
		OverlayItem item = new OverlayItem(point, content, title);
		overlays.add(item);
		mMapView.getOverlays().add(overlays);
		if (mMapView == null)
			Log.w(TAG, "mMapView is null");
		mMapView.refresh();
		Log.i(TAG, point.toString());
		end = point;
		// 初始化搜索
		mMKSearch = new MKSearch();
		mMKSearch.init(mMapManager, searchListener);

		if (MapManager.alt != -1 && MapManager.lon != -1) {
			if (start == null) {
				start = new GeoPoint(MapManager.alt, MapManager.lon);
			}
		} else {
			// 初始化location
			mLocClient = new LocationClient(this);
			mLocClient.registerLocationListener(new MyLocationListenner());
			// NotifyLister mNotifyer = new NotifyLister();
			// 4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
			// mNotifyer.SetNotifyLocation(42.03249652949337,113.3129895882556,3000,"bd09ll");
			// mLocClient.registerNotify(mNotifyer);
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);// 打开gps
			option.setCoorType("gcj02");
			// 设置坐标类型
			option.setScanSpan(1000);
			mLocClient.setLocOption(option);
			mLocClient.start();
			if (mLocClient != null && mLocClient.isStarted()) {
				mLocClient.requestLocation();
			}
		}
		if (pd != null)pd.dismiss();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case MapHeader.BUTTON_MODE_DRIVING:
			Log.i(TAG, "BUTTON_MODE_DRIVING");
			drive(start, end);
			break;
		case MapHeader.BUTTON_MODE_TRANSIT:
			Log.i(TAG, "BUTTON_MODE_TRANSIT");
			transit(start, end);
			break;
		case MapHeader.BUTTON_MODE_WALK:
			Log.i(TAG, "BUTTON_MODE_WALK");
			walk(start, end);
			break;
		}
	}

	/**
	 * 搜索驾车线路
	 * @param start
	 * @param end
	 */
	private void drive(GeoPoint start, GeoPoint end) {
		if (start == null) {
			Util.showMsg(this, R.string.locating);
			return;
		}
		if (isSearching) {
			return;
		}
		isSearching = true;
		pd = Util.showProgressDialog(this, getString(R.string.searching));
		pd.setCancelable(false);
		Log.i(TAG, "Start Point:" + start.toString() + "  End Point:" + end.toString());
		MKPlanNode _start = new MKPlanNode();
		_start.pt = start;
		MKPlanNode _end = new MKPlanNode();
		_end.pt = end;
		// 设置路线搜索策略，时间优先、最少换乘、最少步行距离或不含地铁
		mMKSearch.setDrivingPolicy(MKSearch.EBUS_TRANSFER_FIRST);
		mMKSearch.drivingSearch(null, _start, null, _end);
	}

	/**
	 * 搜索公交换乘线路
	 * @param start
	 * @param end
	 */
	private void transit(GeoPoint start, GeoPoint end) {
		if (start == null) {
			Util.showMsg(this, R.string.locating);
			return;
		}
		if (isSearching) {
			return;
		}
		//如果距离大于Constants.MAX_DISTANCE千米，定为无法公交换乘
		if(DistanceUtil.getDistance(start, end) > Constants.MAX_DISTANCE * 1000){
			Util.showMsg(this, R.string.cannottransit);
			return;
		}
		isSearching = true;
		pd = Util.showProgressDialog(this, getString(R.string.searching));
		pd.setCancelable(false);
		mMKSearch.reverseGeocode(end);// 解析终点地址
		Log.i(TAG, "Start Point:" + start.toString() + "  End Point:" + end.toString());

	}

	private void transitRun(GeoPoint start, GeoPoint end) {
		MKPlanNode _start = new MKPlanNode();
		_start.pt = start;
		MKPlanNode _end = new MKPlanNode();
		_end.pt = end;
		// 设置路线搜索策略，时间优先、最少换乘、最少步行距离或不含地铁
		mMKSearch.setTransitPolicy(MKSearch.EBUS_TRANSFER_FIRST);
		mMKSearch.transitSearch(MapManager.localAddr, _start, _end);
	}

	/**
	 * 步行线路
	 * @param start
	 * @param end
	 */
	private void walk(GeoPoint start, GeoPoint end) {
		if (start == null) {
			Util.showMsg(this, R.string.locating);
			return;
		}
		if (isSearching) {
			return;
		}
		isSearching = true;
		pd = Util.showProgressDialog(this, getString(R.string.searching));
		pd.setCancelable(false);
		Log.i(TAG, "Start Point:" + start.toString() + "  End Point:" + end.toString());
		MKPlanNode _start = new MKPlanNode();
		_start.pt = start;
		MKPlanNode _end = new MKPlanNode();
		_end.pt = end;
		// 设置路线搜索策略，时间优先、最少换乘、最少步行距离或不含地铁
		mMKSearch.setDrivingPolicy(MKSearch.EBUS_TRANSFER_FIRST);
		mMKSearch.walkingSearch(null, _start, null, _end);
	}

	private MKMapViewListener mMapListener = new MKMapViewListener() {
		@Override
		public void onMapMoveFinish() {
			// TODO 移动结束工作
		}

		@Override
		public void onClickMapPoi(MapPoi arg0) {
			// TODO 当点击热点

		}
	};

	@Override
	protected void onDestroy() {
		if(mMapView != null)mMapView.destroy();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if(mMapView != null)mMapView.onPause();
		if (mLocClient != null && mLocClient.isStarted()) {
			mLocClient.stop();
		}
		if (mMapManager != null) {
			mMapManager.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		if(mMapView != null)mMapView.onResume();
		if (mLocClient != null && !mLocClient.isStarted()) {
			mLocClient.start();
		}
		if (mMapManager != null) {
			mMapManager.start();
		}
		super.onResume();
	}

	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			if (start == null) {
				MapManager.alt = (int) (location.getLatitude() * 1E6);
				MapManager.lon = (int) (location.getLongitude() * 1E6);
				start = new GeoPoint(MapManager.alt, MapManager.lon);
				mMKSearch.reverseGeocode(start);// 当获得了本地坐标之后，请求本地地址
				Util.showMsg(MapActivity.this, R.string.locatesuccess);
				Log.v(TAG, "定位成功:" + start.toString());
			}
		}

		@Override
		public void onReceivePoi(BDLocation poiLocation) {
			// 热点查询时使用
			// Log.v(TAG, "定位成功:" + poiLocation.toString());
		}
	}

	public class NotifyLister extends BDNotifyListener {
		@Override
		public void onNotify(BDLocation mlocation, float distance) {
		}
	}
}
