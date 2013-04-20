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

/**
 * 戏曲界面
 * @author sharoncn
 *
 */
public class PkoActivity extends ShowActivity {
	private static final String TAG = "PkoActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setCmdValue(URLProtocol.PKO_CMD_VALUE);
		this.setWho(DataManager.FLAG_PKO);
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
		intent.putExtra(ShowDetail.FLAG_TITLE, getString(R.string.pko));
		intent.putExtra(ShowDetail.FLAG_POSITION, position);
		intent.putExtra(ShowDetail.FLAG_DATA, data);
		intent.putExtra(ShowDetail.FLAG_IDSIGN, URLProtocol.PID);
		intent.putExtra(ShowDetail.FLAG_WHO, URLProtocol.PKO_DETAIL_CMD_VALUE);
		intent.putExtra(ShowDetail.FLAG_DATA_FLAG, DataManager.FLAG_PKO_DETAIL);
		startActivity(intent);
	}
}
