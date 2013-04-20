package rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils;

import java.util.HashMap;
import java.util.Map;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;

/**
 * 之前的设计 用来做方法映射
 * @author sharoncn
 * @deprecated
 */
public class MethodSelector {
	public static Map<String,String> projection = new HashMap<String,String>();
	
	static{
		projection.put("" + DataManager.FLAG_CONCERT, "getConcertList");
		projection.put("" + DataManager.FLAG_CONCERT_DETAIL, "getConcertDetail");
		projection.put("" + DataManager.FLAG_DELICACIES, "getDelicaciesList");
		projection.put("" + DataManager.FLAG_DELICACIES_DETAIL, "getDelicaciesDetail");
		projection.put("" + DataManager.FLAG_DISPLAY, "getDisplayList");
		projection.put("" + DataManager.FLAG_DISPLAY_DETAIL, "getDisplayDetail");
		projection.put("" + DataManager.FLAG_MOVIE, "getMovieList");
		projection.put("" + DataManager.FLAG_MOVIE_DETAIL, "getMovieDetail");
		projection.put("" + DataManager.FLAG_MUSIC, "getMusicList");
		projection.put("" + DataManager.FLAG_MUSIC_DETAIL, "getMusicDetail");
		projection.put("" + DataManager.FLAG_PKO, "getPKOList");
		projection.put("" + DataManager.FLAG_PKO_DETAIL, "getPKODetail");
		projection.put("" + DataManager.FLAG_PLAY, "getPlayList");
		projection.put("" + DataManager.FLAG_PLAY_DETAIL, "getPlayDetail");
		projection.put("" + DataManager.FLAG_GET_RECOMMAND, "getRecommend");
		projection.put("" + DataManager.FLAG_SEND_RECOMMAND, "sendRecomnend");
		projection.put("" + DataManager.FLAG_WILLMOVIE, "getWillMovieList");
		projection.put("" + DataManager.FLAG_WILLMOVIE_DETAIL, "getWillMovieDetail");
	}
	
	public static String getMethod(int flag){
		return projection.get("" + flag);
	}
}
