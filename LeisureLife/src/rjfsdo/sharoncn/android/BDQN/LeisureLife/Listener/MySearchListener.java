package rjfsdo.sharoncn.android.BDQN.LeisureLife.Listener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.platform.comapi.basestruct.GeoPoint;

/**
 * 地图搜索监听器,MapActivity中使用
 * 
 * @author sharoncn
 * 
 */
public class MySearchListener implements MKSearchListener {
	private static final String TAG = "MySearchListener";
	public static final int FLAG_SEARCH_ROUTE = 100;
	public static final int FLAG_PARSE_ADDR = 101;
	public static final String FLAG_ADDR = "addr";
	public static String FLAG_ISSUCCESS = "isSuccess";
	private Context context;
	private MapView mMapView;
	private Handler handler;

	public MySearchListener(Context context, MapView mMapView, Handler handler) {
		this.context = context;
		this.mMapView = mMapView;
		this.handler = handler;
	}

	@Override
	public void onGetAddrResult(MKAddrInfo result, int iError) {
		if (iError != 0) {
            String str = String.format("错误号：%d", iError);
            Log.v(TAG,"OnError:" + str);
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putBoolean(FLAG_ISSUCCESS,false);
            msg.setData(data);
            msg.what = FLAG_PARSE_ADDR;
            handler.sendMessage(msg);
            return;
        } 
		Log.v(TAG,"" + result.strAddr);
		Log.v(TAG,"" + result.strBusiness);
		
		String city = result.addressComponents.city;
		Log.v(TAG,"获得的城市名称为:" + city);
		Message msg = new Message();
        Bundle data = new Bundle();
        data.putBoolean(FLAG_ISSUCCESS,true);
        data.putString(FLAG_ADDR, city);
        msg.setData(data);
        msg.what = FLAG_PARSE_ADDR;
        handler.sendMessage(msg);
	}

	@Override
	public void onGetBusDetailResult(MKBusLineResult result, int iError) {}

	@Override
	public void onGetDrivingRouteResult(MKDrivingRouteResult result, int iError) {
		Log.v(TAG,"onGetDrivingRouteResult:" + iError);
		if (result == null) {
			return;
		}
		RouteOverlay overlay = new RouteOverlay((Activity) context, mMapView);
		overlay.setData(result.getPlan(0).getRoute(0));
		prepareReturn(overlay, result.getStart().pt, result.getEnd().pt);
	}

	/**
	 * 准备返回
	 * @param overlay  要添加的覆盖物
	 * @param start    起点坐标
	 * @param end      终点坐标
	 */
	private void prepareReturn(Overlay overlay, GeoPoint start, GeoPoint end) {
		mMapView.getOverlays().clear();//先清理覆盖物，然后再添加
		mMapView.getOverlays().add(overlay);
		mMapView.refresh();
		zoomToRightLevel(start, end);
		mMapView.getController().animateTo(getRightCenter(start, end));
		Log.i(TAG, "搜索结果已经返回,延迟1秒投递消息");
		if (handler != null){
			Message msg = new Message();
        	Bundle data = new Bundle();
        	data.putBoolean(FLAG_ISSUCCESS,true);
        	msg.setData(data);
        	msg.what = FLAG_SEARCH_ROUTE;
			handler.sendMessageDelayed(msg, 1000);
		}
	}

	/**
	 * 将地图缩放到合适的大小
	 * @param start 起点坐标
	 * @param end   终点坐标
	 */
	private void zoomToRightLevel(GeoPoint start, GeoPoint end) {
		double distance_alt = DistanceUtil.getDistance(start, new GeoPoint(end.getLatitudeE6(), start.getLongitudeE6()));
		double distance_lon = DistanceUtil.getDistance(start, new GeoPoint(start.getLatitudeE6(), end.getLongitudeE6()));
		Log.i(TAG, "开始点和结束点之间的经纬度距离:" + distance_lon + "  " + distance_alt);
		Log.i(TAG, "当前缩放级别:" + mMapView.getZoomLevel());
		Log.i(TAG, "最大缩放级别:" + mMapView.getMaxZoomLevel());
		Log.i(TAG, "最小缩放级别:" + mMapView.getMinZoomLevel());
		MapController controller = mMapView.getController();
		//如果distance大于地图的尺寸，缩小地图，适应坐标
		//Math.sqrt(Math.pow(mMapView.getLatitudeSpan(),2) + Math.pow(mMapView.getLongitudeSpan(),2))
		double mapDistance_Alt = mMapView.getLatitudeSpan();
		double mapDistance_Lon = mMapView.getLongitudeSpan();
		if(distance_alt > mapDistance_Alt || distance_lon > mapDistance_Lon){
			zoomIn(controller,distance_alt,distance_lon);
		}else if(distance_alt * 2 < mapDistance_Alt && distance_lon * 2 < mapDistance_Lon){
			//否则，如果distance * 2小于地图的尺寸，尝试放大地图，适应坐标
			zoomOut(controller,distance_alt,distance_lon);
			zoomIn(controller,distance_alt,distance_lon);
		}
	}

	/**
	 * 缩小地图
	 * @param controller   地图控制器
	 * @param distance_alt 纬度跨度 
	 * @param distance_lon 经度跨度
	 */
	private void zoomIn(MapController controller,double distance_alt,double distance_lon){
		Log.i(TAG, "缩小地图");
		int mapDistance_Alt = 0;
		int mapDistance_Lon = 0;
		int tmp_Alt = mMapView.getLatitudeSpan();
		int tmp_Lon = mMapView.getLongitudeSpan();
		
		while(true){
			tmp_Alt = mMapView.getLatitudeSpan();
			tmp_Lon = mMapView.getLongitudeSpan();
			//等待地图图层响应
			if(tmp_Alt == mapDistance_Alt && mapDistance_Lon == tmp_Lon){
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				mapDistance_Alt = tmp_Alt;
				mapDistance_Lon = tmp_Lon;
				Log.i(TAG, "开始点和结束点之间的经纬度距离:" + distance_lon + "  " + distance_alt);
				Log.i(TAG, "地图经度的跨度距离:" + mapDistance_Lon);
				Log.i(TAG, "地图纬线的跨度距离:" + mapDistance_Alt);
				Log.i(TAG, "当前缩放级别:" + mMapView.getZoomLevel());
				if(distance_alt > tmp_Alt || distance_lon > tmp_Lon){
					controller.setZoom(mMapView.getZoomLevel() - 1);
					Log.i(TAG, "进行缩放:" + mMapView.getZoomLevel());
				}else{
					break;
				}
				
			}
			
		}
		Log.i(TAG, "得到合适的缩放级别:" + mMapView.getZoomLevel());
	}
	
	/**
	 * 放大地图
	 * @param controller   地图控制器
	 * @param distance_alt 纬度跨度 
	 * @param distance_lon 经度跨度
	 */
	private void zoomOut(MapController controller,double distance_alt,double distance_lon){
		Log.i(TAG, "放大地图");
		int mapDistance_Alt = 0;
		int mapDistance_Lon = 0;
		int tmp_Alt = mMapView.getLatitudeSpan();
		int tmp_Lon = mMapView.getLongitudeSpan();

		while(true){
			tmp_Alt = mMapView.getLatitudeSpan();
			tmp_Lon = mMapView.getLongitudeSpan();
			//等待地图图层响应
			if(tmp_Alt == mapDistance_Alt && mapDistance_Lon == tmp_Lon){
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				mapDistance_Alt = tmp_Alt;
				mapDistance_Lon = tmp_Lon;
				Log.i(TAG, "开始点和结束点之间的经纬度距离:" + distance_lon + "  " + distance_alt);
				Log.i(TAG, "地图经度的跨度距离:" + mapDistance_Lon);
				Log.i(TAG, "地图纬线的跨度距离:" + mapDistance_Alt);
				Log.i(TAG, "当前缩放级别:" + mMapView.getZoomLevel());
				if((distance_alt * 2 < tmp_Alt && distance_lon * 2 < tmp_Lon)){
					controller.setZoom(mMapView.getZoomLevel() + 1);
					Log.i(TAG, "进行缩放:" + mMapView.getZoomLevel());
				}else{
					break;
				}
			}
			
		}
		Log.i(TAG, "得到合适的缩放级别:" + mMapView.getZoomLevel());
	}
	
	/**
	 * 得到线路中心点
	 * @param start 起点
	 * @param end   终点
	 * @return  返回两点连线的中心点坐标
	 */
	private GeoPoint getRightCenter(GeoPoint start, GeoPoint end) {
		return new GeoPoint((start.getLatitudeE6() + end.getLatitudeE6()) / 2,
				(start.getLongitudeE6() + end.getLongitudeE6()) / 2);
	}

	@Override
	public void onGetPoiDetailSearchResult(int result, int iError) {}

	@Override
	public void onGetPoiResult(MKPoiResult arg0, int result, int iError) {}

	@Override
	public void onGetSuggestionResult(MKSuggestionResult result, int iError) {}

	@Override
	public void onGetTransitRouteResult(MKTransitRouteResult result, int iError) {
		Log.v(TAG,"onGetTransitRouteResult:" + iError);
		if (result == null) {
			Message msg = new Message();
            Bundle data = new Bundle();
            data.putBoolean(FLAG_ISSUCCESS,false);
            msg.setData(data);
            msg.what = FLAG_SEARCH_ROUTE;
            handler.sendMessage(msg);
			return;
		}
		TransitOverlay overlay = new TransitOverlay((Activity) context, mMapView);
		overlay.setData(result.getPlan(0));
		prepareReturn(overlay, result.getStart().pt, result.getEnd().pt);
	}

	@Override
	public void onGetWalkingRouteResult(MKWalkingRouteResult result, int iError) {
		Log.v(TAG,"onGetWalkingRouteResult:" + iError);
		if (result == null) {
			Message msg = new Message();
            Bundle data = new Bundle();
            data.putBoolean(FLAG_ISSUCCESS,false);
            msg.setData(data);
            msg.what = FLAG_SEARCH_ROUTE;
            handler.sendMessage(msg);
			return;
		}
		RouteOverlay overlay = new RouteOverlay((Activity) context, mMapView);
		overlay.setData(result.getPlan(0).getRoute(0));
		prepareReturn(overlay, result.getStart().pt, result.getEnd().pt);
	}

}