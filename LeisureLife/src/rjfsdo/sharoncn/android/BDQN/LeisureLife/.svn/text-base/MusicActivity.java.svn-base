package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter.ItemBaseAdapter;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLProtocol;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 音乐会界面
 * @author sharoncn
 *
 */
public class MusicActivity extends ShowActivity implements OnItemClickListener{
	protected static final String TAG = "MusicActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setCmdValue(URLProtocol.MUSIC_CMD_VALUE);
		this.setWho(DataManager.FLAG_MUSIC);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onItemClick(AdapterView<?> adpView, View view, int position, long id) {
		Log.v(TAG,"onItemClick:" + position);
		ItemBaseAdapter adapter = (ItemBaseAdapter) adpView.getAdapter();
		ArrayList<Object> data = (ArrayList<Object>) adapter.getData();
		
		//点击了"查看更多"
		if(position + 1 == adapter.getCount()){
			limit ++;
			initData();
			return;
		}
		
		Intent intent = new Intent(this,ShowDetail.class);
		intent.putExtra(ShowDetail.FLAG_TITLE, getString(R.string.music));
		intent.putExtra(ShowDetail.FLAG_POSITION, position);
		intent.putExtra(ShowDetail.FLAG_DATA, data);
		intent.putExtra(ShowDetail.FLAG_IDSIGN, URLProtocol.MID);
		intent.putExtra(ShowDetail.FLAG_WHO, URLProtocol.MUSIC_DETAIL_CMD_VALUE);
		intent.putExtra(ShowDetail.FLAG_DATA_FLAG, DataManager.FLAG_MOVIE_DETAIL);
		startActivity(intent);
	}
}
