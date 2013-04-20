package rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

import android.content.Context;
import android.util.Log;

/**
 * 地图管理器，MapActivity使用
 * 
 * @author sharoncn
 * 
 */
public class MapManager {
	private static MapManager me;
	private static final String BAIDU_KEY = "17DEE563E138A30EBFBF0E9079DB9A96A7FDE2DD";
	private static final String TAG = "MapManager";
	private static Context _context;
	private static BMapManager mMapManager;
	private static boolean inited = false;
	public static int alt = -1, lon = -1;
	/**
	 * 记录本地地址,因为本地地址在使用过程中不会发生变化,所以这个地址只在地图Activity初始化本地坐标时
	 * 初始化一次
	 */
	public static String localAddr = null;

	static {
		me = new MapManager();
	}

	public static MapManager getInstance(Context context) {
		_context = context;
		return me;
	}

	public boolean initMapManager() {
		return me.initMap(_context);
	}

	private boolean initMap(Context context) {
		if (!inited) {
			mMapManager = new BMapManager(context);
			if (!mMapManager.init(BAIDU_KEY, mklst)) {
				Log.w(TAG, _context.getString(R.string.map_init_error));
			} else {
				inited = true;
			}
		}
		return inited;
	}

	public BMapManager getMapManager() {
		return mMapManager;
	}

	private MKGeneralListener mklst = new MKGeneralListener() {
		@Override
		public void onGetPermissionState(int iError) {
			if (MKEvent.ERROR_PERMISSION_DENIED == iError) {
				Util.showMsg(_context, _context.getString(R.string.map_permission_fail));
			} else {
				Util.showMsg(_context, _context.getString(R.string.map_permission_error) + iError);
			}
		}

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Util.showMsg(_context, _context.getString(R.string.map_netstate_error) + iError);
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Util.showMsg(_context, _context.getString(R.string.map_netstate_data) + iError);
			}
		}
	};
}
