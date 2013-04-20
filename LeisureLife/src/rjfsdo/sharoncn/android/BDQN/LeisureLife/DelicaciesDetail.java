package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import java.util.HashMap;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter.DelicaciesDetailAdapter;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.BaseHeader;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Delicacies;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Delicacies.Food;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Listener.OnComplatedListener;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLProtocol;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

/**
 * 美食详情
 * 
 * @author sharoncn
 * 
 */
public class DelicaciesDetail extends BaseActivity implements OnClickListener, OnComplatedListener {
	public static final String FLAG_DATA = "data";
	public static final String FLAG_POSITION = "position";
	protected static final String TAG = "DelicaciesDetail";
	private static final int FLAG_IMAGE_OK = 1001;
	private BaseHeader header;
	private ListView list;
	private DelicaciesDetailAdapter adapter;
	private List<Object> data;
	private DataManager dataManager;
	private ProgressDialog pd;
	private int foodImageCount = -1;

	private Handler handler = new Handler() {
		private int count = 0;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DataManager.FLAG_DELICACIES_DETAIL:

				Bundle bundle = msg.getData();
				boolean isSuccess = bundle.getBoolean(DataManager.FLAG_ISSUCCESS);
				if (isSuccess) {
					final Delicacies d = (Delicacies) bundle.get(DataManager.FLAG_DATA);
					final List<Food> foods = d.getFood();
					final int count = foods.size();
					for (int i = 0; i < count; i++) {
						dataManager.getImage(-1, foods.get(i).getImage(), DelicaciesDetail.this);
						foodImageCount ++;
					}
				} else {
					// TODO something
				}
				break;
			case FLAG_IMAGE_OK:
				count++;
				if (count >= foodImageCount) {
					pd.dismiss();
					list.setAdapter(adapter);
					list.setEnabled(false);
				}
				break;
			}
			DelicaciesDetail.this.handleMessage(msg);
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_delicaciesdetail);
		dataManager = DataManager.getInstance(this);
		init();
	}

	@SuppressWarnings("unchecked")
	private void init() {
		header = (BaseHeader) findViewById(R.id.delicaciesdtail_header);
		header.setTitle(getString(R.string.delicaciesdetail));

		list = (ListView) findViewById(R.id.delicaciesdtail_list);
		adapter = new DelicaciesDetailAdapter(this);
		adapter.setContentButtonOnClickListener(this);

		// 获取数据
		Bundle bundle = getIntent().getExtras();
		data = (List<Object>) bundle.getSerializable(FLAG_DATA);
		if (data == null || data.size() == 0) {
			return;
		}

		adapter.setData(data);
		if (bundle.containsKey(FLAG_POSITION)) {
			adapter.setPosition(bundle.getInt(FLAG_POSITION));
		}
		pd = Util.showProgressDialog(this, getString(R.string.data_loading));

		// 填充详细数据
		for (Object d : data) {
			HashMap<String, String> args = new HashMap<String, String>();
			args.put(URLProtocol.CMD, URLProtocol.DELICACISE_DETAIL_CMD_VALUE);
			args.put(URLProtocol.DID, ((Delicacies) d).getId());
			try {
				dataManager.getDataDetail(DataManager.FLAG_DELICACIES_DETAIL, handler, args, d);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		adapter.dispatchTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void onClick(View v) {
		Delicacies d = (Delicacies) adapter.getItem(0);
		switch (v.getId()) {
		case DelicaciesDetailAdapter.CONTENT_BTN_FIND:
			Log.v(TAG,"find按钮被点击");
			Intent intent = new Intent(this,MapActivity.class);
			intent.putExtra(MapActivity.FLAG_POSITION, d.getMapx() + ";" + d.getMapy());
			startActivity(intent);
			break;
		case DelicaciesDetailAdapter.CONTENT_BTN_RESERVE:
			Intent callIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + d.getCall()));
			startActivity(callIntent);
			Log.v(TAG,"reserve按钮被点击");
			break;
		}
	}

	@Override
	public void onComplated(int index, String imageId) {
		handler.sendEmptyMessage(FLAG_IMAGE_OK);
	}
}
