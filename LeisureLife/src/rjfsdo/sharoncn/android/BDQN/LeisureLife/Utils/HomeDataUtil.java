package rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;


public class HomeDataUtil {

	public static List<HomeData> getHomeData(Context context) {
		List<HomeData> homeDatas = new ArrayList<HomeData>();
		HomeData data = new HomeData(context.getString(R.string.watchmovie),R.drawable.watchmovie);
		homeDatas.add(data);
		
		data = new HomeData(context.getString(R.string.finddelicacies),R.drawable.delicious);
		homeDatas.add(data);
		
		data = new HomeData(context.getString(R.string.display),R.drawable.display);
		homeDatas.add(data);
		
		data = new HomeData(context.getString(R.string.seeshow),R.drawable.seeshow);
		homeDatas.add(data);
		return homeDatas;
	}

	public static class HomeData {
		private String name;//看电影，找美食，看展览，看演出
		private int resid;  //resid分别对应 R.drawable.watchmovie R.drawable.delicious R.drawable.display R.drawable.seeshow

		public HomeData(String name, int resid) {
			super();
			this.name = name;
			this.resid = resid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getResid() {
			return resid;
		}

		public void setResid(int resid) {
			this.resid = resid;
		}

	}
}
