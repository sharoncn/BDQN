package rjfsdo.sharoncn.android.BDQN.AptechWeibo;

import java.util.List;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Adapter.FavoriteAdapter;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.HasButtonHeader;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.FavoritesParser;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.ResponseHolder;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboDataManager;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Favorite;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Status;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Util;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

/**
 * 收藏界面
 * 
 * @author sharoncn
 * 
 */
public class CollectionAct extends Activity implements OnClickListener, OnItemClickListener, OnItemLongClickListener,
		android.content.DialogInterface.OnClickListener {
	protected static final String TAG = "CollectionAct";
	private HasButtonHeader header;
	private ListView list;
	private int page = 1;
	private static final int FIRST = 1;
	private boolean isMore = false;
	private FavoriteAdapter adapter;
	// 更多按钮引用 ，当点击了更多之后，需要将它disable掉，不然不断点击，那情形挺恐怖的
	private View moreView;

	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WeiboDataManager.MSGTYPE_PARSECONTENT:
				Bundle bundle = msg.getData();
				if (bundle.getBoolean(WeiboDataManager.FLAG_ISSUCCESS)) {
					List<Object> data = (List<Object>) bundle.getSerializable(WeiboDataManager.FLAG_DATA);
					if (data.size() == 0 && !isMore) {
						// 如果data.size()为0,表示是单独的Favorite,传递的FavoriteParser没有设置isSingle标识,所以返回的size为0
						refresh(FIRST);
						return;
					}
					if (isMore) {
						Log.w(TAG, "ADD DATA ,数据量为:" + data.size());
						adapter.addData(data);
						isMore = false;
						if (moreView != null) {
							moreView.setEnabled(true);
							moreView = null;
						}
						adapter.notifyDataSetChanged();
					} else {
						adapter.setData(data);
						list.setAdapter(adapter);
						Log.w(TAG, "SET DATA ,数据量为:" + data.size());
					}
				} else {
					Util.showMsg(CollectionAct.this,
							getString(R.string.getdata_fail) + bundle.getString(WeiboDataManager.FLAG_ERR_MSG));
				}
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_collection);

		initViews();
		refresh(FIRST);
	}

	private void initViews() {
		// Header
		header = (HasButtonHeader) findViewById(R.id.collection_header);
		header.setHeaderTitle(R.string.collection);
		header.setLeftButtonBackgroundRes(R.drawable.header_btn_back);
		header.setRightButtonBackgroundRes(R.drawable.header_btn_home);
		header.setButtonOnClickListener(this);

		// List
		list = (ListView) findViewById(R.id.collection_list);
		adapter = new FavoriteAdapter(this);
		list.setOnItemClickListener(this);
		list.setOnItemLongClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case HasButtonHeader.ID_LEFTBUTTON:
			this.finish();
			break;
		case HasButtonHeader.ID_RIGHTBUTTON:
			goHome();
			break;
		}
	}

	private void refresh(int page) {
		Log.v(TAG, "page is:" + page);
		this.page = page;
		ResponseHolder holder = new ResponseHolder(handler);
		holder.setParser(FavoritesParser.getInstance());
		WeiboDataManager.getInstance(this).getFavorites(holder, page);
	}

	private void goHome() {
		Intent intent = new Intent(this, MainTabAct.class);
		startActivity(intent);
		this.finish();
	}

	@Override
	public void onItemClick(AdapterView<?> adpView, View view, int position, long id) {
		if (position == adapter.getCount() - 1) {
			if (moreView != null) {
				Util.showMsg(CollectionAct.this, R.string.loading);
				return;// 如果moreView不为空表示正在加载数据
			}
			// 更多按钮被点击
			Log.v(TAG, "更多按钮被点击");
			isMore = true;
			moreView = view;
			view.setEnabled(false);
			refresh(++page);
			return;
		}
		final Favorite favorite = (Favorite) adapter.getItem(position);
		final Status status = favorite.getStatus();
		Intent intent = new Intent(this, StatusDetail.class);
		intent.putExtra(StatusDetail.FLAG_DATA, status);
		startActivity(intent);
	}

	private int position = -1;

	@Override
	public boolean onItemLongClick(AdapterView<?> adpView, View view, int position, long id) {
		if (position != adapter.getCount() - 1) {
			this.position = position;
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setMessage(R.string.collection_delete_comfirm).setTitle(R.string.delete_comfirm).setCancelable(false)
					.setPositiveButton(R.string.delete, this).setNegativeButton(R.string.cancel, this).show();
			return true;
		}
		return false;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:// 删除
			if (position == -1)
				return;
			final Favorite favorite = (Favorite) adapter.getItem(position);
			delete(Long.parseLong(favorite.getStatus().getId()));
			break;
		case DialogInterface.BUTTON_NEGATIVE:// 取消
			dialog.dismiss();
			position = -1;
			break;
		}
	}

	private void delete(long id) {
		ResponseHolder holder = new ResponseHolder(handler);
		holder.setParser(FavoritesParser.getInstance());
		WeiboDataManager.getInstance(this).deleteFavorite(holder, id);
	}
}
