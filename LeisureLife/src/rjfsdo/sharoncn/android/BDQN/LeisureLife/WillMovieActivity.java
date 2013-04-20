package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLProtocol;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;


/**
 * 即将上映的电影
 * @author sharoncn
 *
 */
public class WillMovieActivity extends MovieListBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setCmdValue(URLProtocol.WILLMOVIE_CMD_VALUE);
		this.setWho(DataManager.FLAG_WILLMOVIE);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onItemClick(AdapterView<?> adpView, View view, int position, long id) {
		if(position + 1 == adapter.getCount()){
			limit ++;
			initData();
			return;
		}
		ArrayList<Object> data = (ArrayList<Object>) this.adapter.getData();
		Intent intent = new Intent(this,MovieDetail.class);
		intent.putExtra(MovieDetail.FLAG_DATA, data);
		intent.putExtra(MovieDetail.FLAG_POSITION, position);
		intent.putExtra(MovieDetail.FLAG_WHO, URLProtocol.WILLMOVIE_DETAIL_CMD_VALUE);
		intent.putExtra(MovieDetail.FLAG_DATA_FLAG, DataManager.FLAG_WILLMOVIE_DETAIL);
		startActivity(intent);
	}

}
