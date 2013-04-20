package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter.HomeAdapter;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.BaseHeader;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.HomeDataUtil;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.HomeDataUtil.HomeData;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * 活动功能界面
 * 
 * @author sharoncn
 * 
 */
public class HomeActivity extends BaseActivity implements OnItemClickListener {
	private GridView home_gview;
	private HomeAdapter adapter;
	private BaseHeader header;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_home);

		header = (BaseHeader) findViewById(R.id.main_header);
		//header.setBackgroundResource(R.drawable.head);
		header.setTitle("休闲生活");
		home_gview = (GridView) findViewById(R.id.home_gview);
		initGrid();
	}

	private void initGrid() {
		home_gview.setOnItemClickListener(this);
		adapter = new HomeAdapter(this, HomeDataUtil.getHomeData(this));
		home_gview.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> adpView, View view, int position,
			long id) {
		HomeData data = (HomeData) adpView.getAdapter().getItem(position);
		Class<?> clazz = null;
		switch (data.getResid()) {
		case R.drawable.watchmovie:
			clazz = TabMovieActivity.class;
			break;
		case R.drawable.delicious:
			clazz = FindDelicacies.class;
			break;
		case R.drawable.display:
			clazz = SeeDisplayActivity.class;
			break;
		case R.drawable.seeshow:
			clazz = SeeShowActivity.class;
			break;
		}
		
		Intent intent = new Intent(this,clazz);
		startActivity(intent);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//TabMain内部Activity，为了防止back事件被处理，当back时返回false
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
