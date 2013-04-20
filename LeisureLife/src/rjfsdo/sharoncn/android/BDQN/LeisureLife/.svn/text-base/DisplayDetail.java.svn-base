package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import java.util.HashMap;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter.DisplayDetailAdapter;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.BaseHeader;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.DefaultMenu;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.Collection;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.SaveDataUtils;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Display;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Listener.OnCommentLoadedListener;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLProtocol;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 展览详情
 * 
 * @author sharoncn
 * 
 */
public class DisplayDetail extends BaseActivity implements OnItemClickListener ,OnCommentLoadedListener {
	private static final String TAG = "DisplayDetail";
	public static final String FLAG_DATA = "data";
	public static final String FLAG_POSITION = "position";
	private BaseHeader header;
	private DefaultMenu menu;
	private ListView list;
	private DisplayDetailAdapter adapter;
	private int count = 0;
	private List<Object> data;
	private int data_flag = DataManager.FLAG_DISPLAY_DETAIL;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (data_flag == msg.what) {
				count++;
				boolean isSuccess = msg.getData().getBoolean(DataManager.FLAG_ISSUCCESS);
				if (!isSuccess) {
					// TODO something
				}
				if (count >= data.size()) {
					pd.dismiss();
					list.setAdapter(adapter);
				}
			}
			DisplayDetail.this.handleMessage(msg);
			super.handleMessage(msg);
		}
	};
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_displaydetail);

		init();
	}

	@SuppressWarnings("unchecked")
	private void init() {
		header = (BaseHeader) findViewById(R.id.displaydetail_header);
		menu = (DefaultMenu) findViewById(R.id.displaydetail_menu);
		list = (ListView) findViewById(R.id.displaydetail_list);

		// 头部
		header.setTitle(getString(R.string.display));

		// 底部menu
		menu.setOnItemClickListener(this);
		menu.setCollectionEnabled(false);

		adapter = new DisplayDetailAdapter(this);
		
		Bundle bundle = getIntent().getExtras();
		data = (List<Object>) bundle.getSerializable(FLAG_DATA);
		if (data == null || data.size() == 0) {
			return;
		}
		adapter.setData(data);
		if (bundle.containsKey(FLAG_POSITION)) {
			adapter.setPosition(bundle.getInt(FLAG_POSITION));
		}
		adapter.setHeaderCommentButtonOnClickListener(comment_click);
		adapter.setHeaderDescButtonOnClickListener(desc_click);
		adapter.setOnCommentLoadedListener(this);

		pd = Util.showProgressDialog(this, getString(R.string.data_loading));
		
		// 填充详细数据
		for (Object display : data) {
			HashMap<String, String> args = new HashMap<String, String>();
			args.put(URLProtocol.CMD, URLProtocol.DISPLAY_DETAIL_CMD_VALUE);
			args.put(URLProtocol.DID, ((Display) display).getId());
			// Log.w(TAG,m.toString());
			try {
				DataManager.getInstance(this).getDataDetail(data_flag, handler, args, display);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private OnClickListener comment_click = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if ((Boolean) v.getTag()) {
				adapter.setCommentVisibility(View.VISIBLE);
			} else {
				adapter.setCommentVisibility(View.GONE);
			}
		}
	};

	private OnClickListener desc_click = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if ((Boolean) v.getTag()) {
				adapter.setDescriptionVisibility(View.VISIBLE);
			} else {
				adapter.setDescriptionVisibility(View.GONE);
			}
		}
	};

	@Override
	public void onItemClick(AdapterView<?> adpView, View view, int position, long id) {
		Log.v(TAG, "菜单被点击,item：" + position);
		switch (position) {
		case 0:
			if (adapter.getPosition() > 0) {
				adapter.setPosition(adapter.getPosition() - 1);
				adapter.notifyDataSetChanged();
			}
			break;
		case 1:
			Display display = (Display) adapter.getItem(0);
			Intent commentIntent = new Intent(this, CommentActivity.class);
			commentIntent.putExtra(CommentActivity.FLAG_ID, display.getId());
			commentIntent.putExtra(CommentActivity.FLAG_TYPE, "" + 1);
			startActivity(commentIntent);
			break;
		case 2:
			Display _display = (Display) adapter.getItem(0);
			Intent recommendIntent = new Intent(this, RecommendActivity.class);
			recommendIntent.putExtra(RecommendActivity.INTENT_IN_CONTENT, _display.getName());
			startActivity(recommendIntent);
			break;
		case 3:
			boolean result = SaveDataUtils.getInstance(this).saveCollection(new Collection(getString(R.string.display_1),
					adapter.getItem(0).toString(), Util.recommendList2String(adapter.getComment())));
			if(result){
				Util.showMsg(this, R.string.collection_success);
			}else{
				Util.showMsg(this, R.string.collection_fail);
			}
			break;
		case 4:
			if (adapter.getPosition() < adapter.getData().size() - 1) {
				adapter.setPosition(adapter.getPosition() + 1);
				adapter.notifyDataSetChanged();
			}
			break;
		}
	}

	@Override
	public void onCommentLoaded() {
		menu.setCollectionEnabled(true);
	}

}
