package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import java.util.HashMap;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter.ShowDetailAdapter;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.BaseHeader;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.DefaultMenu;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.Collection;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.SaveDataUtils;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.Show;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Listener.OnCommentLoadedListener;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLProtocol;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
 * 请首先初始化idSign,who,data_flag
 * @author sharoncn
 *
 */
public class ShowDetail extends BaseActivity implements OnItemClickListener, OnClickListener ,OnCommentLoadedListener{
	public static final String FLAG_TITLE = "title";
	public static final String FLAG_DATA = "data";
	public static final String FLAG_POSITION = "position";
	/**
	 * 设置请求的id标识，应为URLProtocol.?ID
	 */
	public static final String FLAG_IDSIGN = "idSign";
	/**
	 * 请求命令标识，应为URLProtocol.*_DETAIL_CMD_VALUE
	 */
	public static final String FLAG_WHO = "who";
	/**
	 * 设置DataManager的调用flag，应为:DataManager.FLAG_*_DETAIL
	 */
	public static final String FLAG_DATA_FLAG = "data_flag";
	private static final String TAG = "ShowDetail";
	private BaseHeader header;
	private DefaultMenu menu;
	private ListView container;
	private ShowDetailAdapter adapter;
	private int count = 0;//循环填充数据的计数器
	private List<Object> data;
	
	
	private String idSign = "";
	private String who = "";
	private int data_flag = 1001;
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if( data_flag == msg.what){
				count++;
				boolean isSuccess = msg.getData().getBoolean(DataManager.FLAG_ISSUCCESS);
				if(!isSuccess){
					//TODO something
				}
				if(count >= data.size()){
					pd.dismiss();
					container.setAdapter(adapter);
				}
			}
			ShowDetail.this.handleMessage(msg);
			super.handleMessage(msg);
		}
	};
	private ProgressDialog pd;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_showdetail);
		
		header = (BaseHeader) findViewById(R.id.show_detail_header);
		menu = (DefaultMenu) findViewById(R.id.show_detail_menu);
		container = (ListView) findViewById(R.id.show_detail_list);
		init();
	}

	@SuppressWarnings("unchecked")
	private void init() {
		menu.setOnItemClickListener(this);
		menu.setCollectionEnabled(false);

		adapter = new ShowDetailAdapter(this);
		Bundle bundle = getIntent().getExtras();
		data = (List<Object>) bundle.getSerializable(FLAG_DATA);
		if (data == null || data.size() == 0) {
			return;
		}
		
		header.setTitle(bundle.getString(FLAG_TITLE));
		if (bundle.containsKey(FLAG_IDSIGN))idSign = bundle.getString(FLAG_IDSIGN);
		if (bundle.containsKey(FLAG_WHO))who = bundle.getString(FLAG_WHO);
		if (bundle.containsKey(FLAG_DATA_FLAG))data_flag = bundle.getInt(FLAG_DATA_FLAG);
		adapter.setData(data);
		if (bundle.containsKey(FLAG_POSITION)) {
			adapter.setPosition(bundle.getInt(FLAG_POSITION));
		}

		adapter.setHeaderCommentButtonOnClickListener(comment_click);
		adapter.setHeaderDescButtonOnClickListener(desc_click);
		adapter.setContentButtonOnClickListener(this);
		adapter.setOnCommentLoadedListener(this);
		
		pd = Util.showProgressDialog(this, getString(R.string.data_loading));
		
		//填充详细数据
		for(Object show:data){
			HashMap<String, String> args = new HashMap<String, String>();
			args.put(URLProtocol.CMD, who);
			args.put(idSign, ((Show)show).getId());
//			Log.w(TAG,m.toString());
			try {
				DataManager.getInstance(this).getDataDetail(data_flag, handler, args, show);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private OnClickListener comment_click = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if((Boolean) v.getTag()){
				adapter.setCommentVisibility(View.VISIBLE);
			}else{
				adapter.setCommentVisibility(View.GONE);
			}
		}
	};

	private OnClickListener desc_click = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if((Boolean) v.getTag()){
				adapter.setDescriptionVisibility(View.VISIBLE);
			}else{
				adapter.setDescriptionVisibility(View.GONE);
			}
		}
	};

	@Override
	public void onItemClick(AdapterView<?> adpView, View view, int position,
			long id) {
		Log.v(TAG,"菜单被点击,item：" + position);
		switch (position) {
		case 0:
			if(adapter.getPosition() > 0){
				adapter.setPosition(adapter.getPosition() - 1);
				adapter.notifyDataSetChanged();
			}
			break;
		case 1:
			Show show = (Show)adapter.getItem(0);
			Intent commentIntent = new Intent(this,CommentActivity.class);
			commentIntent.putExtra(CommentActivity.FLAG_ID, show.getId());
			commentIntent.putExtra(CommentActivity.FLAG_TYPE, who);
			startActivity(commentIntent);
			break;
		case 2:
			Show _show = (Show)adapter.getItem(0);
			Intent recommendIntent = new Intent(this,RecommendActivity.class);
			recommendIntent.putExtra(RecommendActivity.INTENT_IN_CONTENT, _show.getName());
			startActivity(recommendIntent);
			break;
		case 3:
			boolean result = SaveDataUtils.getInstance(this).saveCollection(new Collection(getString(R.string.show), 
					adapter.getItem(0).toString(), Util.recommendList2String(adapter.getComment())));
			if(result){
				Util.showMsg(this, R.string.collection_success);
			}else{
				Util.showMsg(this, R.string.collection_fail);
			}
			break;
		case 4:
			if(adapter.getPosition() < adapter.getData().size() - 1){
				adapter.setPosition(adapter.getPosition() + 1);
				adapter.notifyDataSetChanged();
			}
			break;
		}
	}

	@Override
	public void onClick(View v) {
		Show show = (Show) adapter.getItem(0);
		switch(v.getId()){
		case ShowDetailAdapter.CONTENT_BTN_FIND:
			Log.v(TAG,"find按钮被点击");
			Intent intent = new Intent(this,MapActivity.class);
			intent.putExtra(MapActivity.FLAG_POSITION, show.getMapx() + ";" + show.getMapy());
			startActivity(intent);
			break;
		case ShowDetailAdapter.CONTENT_BTN_RESERVE:
			Intent callIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + show.getCall()));
			startActivity(callIntent);
			Log.v(TAG,"reserve按钮被点击");
			break;
		}
	}

	@Override
	public void onCommentLoaded() {
		menu.setCollectionEnabled(true);
	}
	
}
