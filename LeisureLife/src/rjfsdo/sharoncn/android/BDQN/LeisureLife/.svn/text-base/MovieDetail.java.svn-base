package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import java.util.HashMap;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter.MovieDetailAdapter;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.BaseHeader;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.DefaultMenu;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.BaseMovie;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.Collection;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.SaveDataUtils;
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
 * 电影详情
 * 
 * @author sharoncn
 * 
 */
public class MovieDetail extends BaseActivity implements OnItemClickListener, OnCommentLoadedListener {
	private static final String TAG = "DetailMovie";
	public static final String FLAG_DATA = "data";
	public static final String FLAG_POSITION = "position";
	/**
	 * 请求命令标识，应为URLProtocol.*_DETAIL_CMD_VALUE
	 */
	public static final String FLAG_WHO = "who";
	/**
	 * 设置DataManager的调用flag，应为:DataManager.FLAG_*_DETAIL
	 */
	public static final String FLAG_DATA_FLAG = "data_flag";
	private BaseHeader header;
	private DefaultMenu menu;
	private ListView container;
	private MovieDetailAdapter adapter;
	private int count = 0;// 循环填充数据的计数器
	private List<Object> data;
	private String who;
	private int data_flag = 1001;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			count++;
			boolean isSuccess = msg.getData().getBoolean(DataManager.FLAG_ISSUCCESS);
			if (!isSuccess) {
				// TODO something
			}
			if (count >= data.size()) {
				// for(Object m:data){
				// Log.w(TAG,m.toString());
				// }
				pd.dismiss();
				container.setAdapter(adapter);
			}
			MovieDetail.this.handleMessage(msg);
			super.handleMessage(msg);
		}
	};
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_detailmovie);

		header = (BaseHeader) findViewById(R.id.header_detailmovie);
		menu = (DefaultMenu) findViewById(R.id.menu_detailmovie);
		container = (ListView) findViewById(R.id.container_detailmovie);
		init();
	}

	@SuppressWarnings("unchecked")
	private void init() {
		header.setTitle(getString(R.string.moviedetail));
		menu.setOnItemClickListener(this);
		menu.setCollectionEnabled(false);

		adapter = new MovieDetailAdapter(this);
		Bundle bundle = getIntent().getExtras();
		data = (List<Object>) bundle.getSerializable(FLAG_DATA);
		if (data == null || data.size() == 0) {
			return;
		}

		adapter.setData(data);
		if (bundle.containsKey(FLAG_POSITION)) {
			adapter.setPosition(bundle.getInt(FLAG_POSITION));
		}
		
		if (bundle.containsKey(FLAG_WHO))who = bundle.getString(FLAG_WHO);
		if (bundle.containsKey(FLAG_DATA_FLAG))data_flag = bundle.getInt(FLAG_DATA_FLAG);
		adapter.setHeaderCommentButtonOnClickListener(comment_click);
		adapter.setHeaderDescButtonOnClickListener(desc_click);
		adapter.setOnCommentLoadedListener(this);

		pd = Util.showProgressDialog(this, getString(R.string.data_loading));
		
		// 填充详细数据
		for (Object m : data) {
			HashMap<String, String> args = new HashMap<String, String>();
			args.put(URLProtocol.CMD, who);
			args.put(URLProtocol.MID, ((BaseMovie) m).getId());
			// Log.w(TAG,m.toString());
			try {
				DataManager.getInstance(this).getDataDetail(data_flag, handler, args, m);
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
			BaseMovie movie = (BaseMovie) adapter.getItem(0);
			Intent commentIntent = new Intent(this, CommentActivity.class);
			commentIntent.putExtra(CommentActivity.FLAG_ID, movie.getId());
			commentIntent.putExtra(CommentActivity.FLAG_TYPE, URLProtocol.MOVIE_DETAIL_CMD_VALUE);
			startActivity(commentIntent);
			break;
		case 2:
			BaseMovie _movie = (BaseMovie) adapter.getItem(0);
			Intent recommendIntent = new Intent(this, RecommendActivity.class);
			recommendIntent.putExtra(RecommendActivity.INTENT_IN_CONTENT, _movie.getName());
			startActivity(recommendIntent);
			break;
		case 3:
			boolean result = SaveDataUtils.getInstance(this).saveCollection(new Collection(getString(R.string.movie),
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
