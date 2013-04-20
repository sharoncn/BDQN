package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter.DefaultAdapter;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.BaseHeader;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Delicacies;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLProtocol;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 找美食界面
 * 
 * @author sharoncn
 * 
 */
public class FindDelicacies extends ListBaseActivity implements OnItemClickListener {
	private BaseHeader header;
	private int limit = 0;

	protected void setProjection(DefaultAdapter adapter) {
		adapter.addProjection(DefaultAdapter.VIEW_IMG, "getImage");
		adapter.addProjection(DefaultAdapter.VIEW_TXTONE, "getName");
		adapter.addProjection(DefaultAdapter.VIEW_TXTTWO, "getLabel");
		adapter.addProjection(DefaultAdapter.VIEW_TXTTHREE, "getAddr");
		adapter.addProjection(DefaultAdapter.VIEW_TXTFOUR, "getAvg");
	}

	protected void addTextViewHeader(DefaultAdapter adapter) {
		adapter.addTextHeader(0, "");
		adapter.addTextHeader(1, getString(R.string.labelis));
		adapter.addTextHeader(2, getString(R.string.addris));
		adapter.addTextHeader(3, getString(R.string.avgis));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_finddelicacies);

		list = (ListView) findViewById(R.id.finddelicacies_list);
		list.setOnItemClickListener(this);
		header = (BaseHeader) findViewById(R.id.finddelicacies_header);
		header.setTitle(getString(R.string.finddelicacies));

		initData();
	}

	@Override
	public void onItemClick(AdapterView<?> adpView, View view, int position, long id) {
		if (position + 1 == adapter.getCount()) {
			limit++;
			initData();
			return;
		}

		ArrayList<Object> data = (ArrayList<Object>) adapter.getData();
		Intent intent = new Intent(this, DelicaciesDetail.class);
		intent.putExtra(DelicaciesDetail.FLAG_DATA, data);
		intent.putExtra(DelicaciesDetail.FLAG_POSITION, position);
		startActivity(intent);
	}

	private void initData(){
		super.initData(this, Delicacies.class, URLProtocol.DELICACISE_CMD_VALUE, DataManager.FLAG_DELICACIES, limit);
	}
}
