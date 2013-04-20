package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import android.content.Context;
import android.util.Log;

/**
 * 定位
 * @author sharoncn
 *
 */
public class LocationProvider {
	private static LocationProvider me;
	private static Context mContext;
	private static double lat = -1, lon = -1;
	
	static {
		me = new LocationProvider();
	}

	private static LocationClient mLocationClient;
	

	private LocationProvider() {}

	public static LocationProvider getInstance(Context context){
		if(mContext == null)mContext = context;
		return me;
	}
	
	public void initLocation(){
		if(mLocationClient == null)mLocationClient = new LocationClient(mContext);
		mLocationClient.registerLocationListener(locationLst);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");//返回的定位结果包含地址信息
		//option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);//禁止启用缓存定位
		mLocationClient.setLocOption(option);
		mLocationClient.start();
		if (mLocationClient != null && mLocationClient.isStarted())
			mLocationClient.requestLocation();
		else
			Log.d("LocSDK3", "locClient is null or not started");
	}
	
	private BDLocationListener locationLst = new BDLocationListener() {
		@Override
		public void onReceivePoi(BDLocation location) {
		}
		
		@Override
		public void onReceiveLocation(BDLocation location) {
			if(lat == -1){
				lat = location.getLatitude();
				System.out.print("获得Location返回,lat：" + lat);
			}
			if(lon == -1){
				lon = location.getLongitude();
				System.out.print("获得Location返回,lon：" + lon);
			}
		}
	};
	
	public double getAltitude(){
		return lat;
	}
	
	public double getLongitude(){
		return lon;
	}
}
