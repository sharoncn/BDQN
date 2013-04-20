package rjfsdo.sharoncn.android.BDQN.AptechWeibo;

import java.io.Serializable;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Adapter.HomeAdapter;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.HasButtonHeader;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.ResponseHolder;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.StatusParser;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboDataManager;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Util;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 关于list卡顿问题，可以这么测试，注掉图片加载的代码看看是否还卡。 确定是图片加载造成的还是，高度不一致原因造成的。
 * 
 * 经过测试，跟图片加载没有关系，是ListView的item高度不一致 造成的卡顿。
 * 
 * 暂时没有想到办法解决这个问题，网上搜了半天无果。
 * 
 * 或许可以这样,将ListView的高度计算出来然后设置ListView的高度，外边包裹ScrollView来解决
 * 这么做滑动时肯定不会卡顿，但是，刚开始计算高度肯定会卡。
 * 
 * 
 * @author sharoncn
 * 
 */
public class MHomeAct extends BaseActivity implements OnClickListener, OnItemClickListener, OnScrollListener {
	private static final String TAG = "MHomeAct";
	private static final int FIRST = 1;
	private static HasButtonHeader header;
	private static HomeAdapter adapter;
	private static ListView list;
	// 更多按钮引用 ，当点击了更多之后，需要将它disable掉，不然不断点击，那情形挺恐怖的
	private View moreView;
	private int page = 1;
	private boolean isMore = false;
	
	private Handler handler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WeiboDataManager.MSGTYPE_PARSECONTENT:
				Bundle bundle = msg.getData();
				if (bundle.getBoolean(WeiboDataManager.FLAG_ISSUCCESS)) {
					List<Object> data = (List<Object>) bundle.getSerializable(WeiboDataManager.FLAG_DATA);
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
					Util.showMsg(MHomeAct.this, getString(R.string.getdata_fail) + bundle.getString(WeiboDataManager.FLAG_ERR_MSG));
				}
				changeStatus(false);
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_home);

		initHeader();
		initList();

		refresh(FIRST);
	}

	private void refresh(int page) {
		this.page = page;
		ResponseHolder holder = new ResponseHolder(handler);
		holder.setParser(StatusParser.getInstance());
		WeiboDataManager.getInstance(this).getFriendsTimeLine(holder, page);
		changeStatus(true);
	}

	private void initList() {
		list = (ListView) findViewById(R.id.home_list);
		adapter = new HomeAdapter(this);
		list.setOnItemClickListener(this);
		list.setOnScrollListener(this);
	}

	private void initHeader() {
		header = (HasButtonHeader) findViewById(R.id.home_header);
		header.setHeaderTitle(R.string.home);
		header.setLeftButtonBackgroundRes(R.drawable.header_home_write);
		header.setRightButtonBackgroundRes(R.drawable.header_home_refresh);
		header.setRotatingBackgroundRes(R.drawable.header_home_refresh);
		header.setButtonOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case HasButtonHeader.ID_LEFTBUTTON:
			writeNewStatus();
			break;
		case HasButtonHeader.ID_RIGHTBUTTON:
			refresh(FIRST);
			break;
		}
	}

	private void writeNewStatus() {
		Intent intent = new Intent(this, NewStatusAct.class);
		startActivity(intent);
	}

	private void changeStatus(boolean isLoading) {
		if (isLoading) {
			header.startButtonRotate(HasButtonHeader.ID_RIGHTBUTTON, 24);
		} else {
			header.stopButtonRotate();
		}
	}

	@Override
	protected void onPause() {
		//header.stopButtonRotate();
		super.onPause();
	}

	@Override
	public void onItemClick(AdapterView<?> adpView, View view, int position, long id) {
		if (position == adapter.getCount() - 1) {
			if (moreView != null) {
				Util.showMsg(MHomeAct.this, R.string.loading);
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

		// "详细信息页面"
		Intent intent = new Intent(this, StatusDetail.class);
		intent.putExtra(StatusDetail.FLAG_DATA, (Serializable) adapter.getItem(position));
		startActivity(intent);
	}

	// 以下为测试代码
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// System.out.println("onScroll:" + firstVisibleItem + "-" +
		// visibleItemCount + "-" + totalItemCount);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// System.out.println("onScrollStateChanged,state:" + scrollState);
	}

}
