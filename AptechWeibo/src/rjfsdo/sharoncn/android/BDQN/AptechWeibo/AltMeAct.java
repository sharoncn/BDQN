package rjfsdo.sharoncn.android.BDQN.AptechWeibo;

import java.util.List;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Adapter.AltMeAdapter;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.ResponseHolder;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.StatusParser;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboDataManager;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Util;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 微博@我
 * @author sharoncn
 *
 */
public class AltMeAct extends BaseActivity implements OnItemClickListener {
	protected static final String TAG = "AltMeAct";
	private static final int FIRST = 1;
	private ListView list;
	private int page = 1;
	private AltMeAdapter adapter;
	private ProgressBar pb;
	// 更多按钮引用 ，当点击了更多之后，需要将它disable掉，不然不断点击，那情形挺恐怖的
	private View moreView;
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
					pb.setVisibility(View.GONE);
				} else {
					Util.showMsg(AltMeAct.this, getString(R.string.getdata_fail) + bundle.getString(WeiboDataManager.FLAG_ERR_MSG));
				}
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_altme);

		initList();
		refresh(FIRST);
	}

	private void initList() {
		list = (ListView) findViewById(R.id.altme_list);
		list.setOnItemClickListener(this);
		pb = (ProgressBar) findViewById(R.id.altme_pb);
		adapter = new AltMeAdapter(this);
	}

	private void refresh(int page) {
		this.page = page;
		pb.setVisibility(View.VISIBLE);
		ResponseHolder holder = new ResponseHolder(handler);
		holder.setParser(StatusParser.getInstance());
		WeiboDataManager.getInstance(this).getMontions(holder, page);
	}

	@Override
	public void onItemClick(AdapterView<?> adpView, View view, int position, long id) {
		if (position == adapter.getCount() - 1) {
			if (moreView != null) {
				Util.showMsg(AltMeAct.this, R.string.loading);
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
	}

}
