package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter.DefaultAdapter;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter.ItemBaseAdapter;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.BaseHeader;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Display;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLProtocol;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 看展览界面
 * @author sharoncn
 *
 */
public class SeeDisplayActivity extends ListBaseActivity implements OnItemClickListener{
	private BaseHeader header;
	private int limit = 0;
	
	@Override
	protected void setProjection(DefaultAdapter adapter){
		adapter.addProjection(DefaultAdapter.VIEW_IMG, "getImage");
		adapter.addProjection(DefaultAdapter.VIEW_TXTONE, "getName");
		adapter.addProjection(DefaultAdapter.VIEW_TXTTWO, "getAddr");
		adapter.addProjection(DefaultAdapter.VIEW_TXTTHREE, "getTime");
	}
	
	@Override
	protected void addTextViewHeader(DefaultAdapter adapter){
		adapter.addTextHeader(0,"");
		adapter.addTextHeader(1,getString(R.string.timeis));
		adapter.addTextHeader(2,getString(R.string.addris));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_display);
		
		list = (ListView) findViewById(R.id.display_list);
		list.setOnItemClickListener(this);
		header = (BaseHeader) findViewById(R.id.display_header);
		header.setTitle(getString(R.string.display));
		
		initData();
	}

	@Override
	public void onItemClick(AdapterView<?> adpView, View view, int position, long id) {
		ItemBaseAdapter adapter = (ItemBaseAdapter) adpView.getAdapter();
		if(position + 1 == adapter.getCount()){
			limit ++;
			initData();
			return;
		}
		ArrayList<Object> data = (ArrayList<Object>) adapter.getData();
		Intent intent = new Intent(this,DisplayDetail.class);
		intent.putExtra(DisplayDetail.FLAG_POSITION, position);
		intent.putExtra(DisplayDetail.FLAG_DATA, data);
		startActivity(intent);
	}
	
	private void initData() {
		super.initData(this, Display.class, URLProtocol.DISPLAY_CMD_VALUE, DataManager.FLAG_DISPLAY, limit);
	}
	
}
