package rjfsdo.sharoncn.android.BDQN.AptechWeibo;

import java.util.List;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.DefaultMenu;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.DetailPanel;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.HasButtonHeader;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.ResponseHolder;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.StatusParser;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboDataManager;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Status;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.User;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.DetailImageDialog;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.ExpressionFilter;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.ImageCache;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Util;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 微博详情界面
 * @author sharoncn
 *
 */
public class StatusDetail extends Activity implements OnClickListener {
	public static final String FLAG_DATA = "data";
	protected static final String TAG = "StatusDetail";
	private static final int ICON_POSITION = -1000;
	private static final int PIC_POSITION = -1001;
	private HasButtonHeader header;
	private DefaultMenu menu;
	private DetailPanel panel;
	private ImageView icon, pic;
	private TextView username, content;
	private Status status;
	private static ExpressionFilter expressionFilter;

	private Handler handler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			final Bundle bundle = msg.getData();
			switch (msg.what) {
			case WeiboDataManager.MSGTYPE_IMAGEOK:
				if (bundle.getBoolean(WeiboDataManager.FLAG_ISSUCCESS)) {
					final String key = bundle.getString(WeiboDataManager.FLAG_IMAGEKEY);
					Log.i(TAG, "请求图片返回成功,图片路径:" + key);
					final Bitmap bmp = (Bitmap) bundle.getParcelable(WeiboDataManager.FLAG_DATA);
					ImageCache.getInstance(StatusDetail.this).put(key, bmp);
					int position = bundle.getInt(WeiboDataManager.FLAG_POSITION);
					if (position == ICON_POSITION) {
						icon.setImageBitmap(bmp);
					} else {
						pic.setVisibility(View.VISIBLE);
						pic.setImageBitmap(bmp);
					}
				}
				break;
			case WeiboDataManager.MSGTYPE_PARSECONTENT:
				if (bundle.getBoolean(WeiboDataManager.FLAG_ISSUCCESS)) {
					Util.showMsg(StatusDetail.this, R.string.op_success);
					final List<Object> data = (List<Object>) bundle.getSerializable(WeiboDataManager.FLAG_DATA);
					Status status = null;
					if (data.size() > 0) {
						status = (Status) data.get(0);
					} else {
						Log.w(TAG, "data size is zero");
					}
					if (status != null) {
						initViewsContent(status);
					}
				} else {
					Util.showMsg(StatusDetail.this, getString(R.string.getdata_fail) + bundle.getString(WeiboDataManager.FLAG_ERR_MSG));
				}
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_statusdetail);
		expressionFilter = ExpressionFilter.getInstance(this);
		// 初始化Views
		initViews();

		// 为View初始化内容
		final Bundle data = getIntent().getExtras();
		if (data.containsKey(FLAG_DATA)) {
			status = (Status) data.get(FLAG_DATA);
			initViewsContent(status);
		}
	}

	private void initViews() {
		// header
		header = (HasButtonHeader) findViewById(R.id.detail_header);
		header.setLeftButtonBackgroundRes(R.drawable.header_btn_back);
		header.setRightButtonBackgroundRes(R.drawable.header_btn_home);
		header.setButtonOnClickListener(this);
		header.setHeaderTitle(R.string.statusdetail);

		// menu
		menu = (DefaultMenu) findViewById(R.id.detail_menu);
		menu.setOnClickListener(this);

		// panel
		panel = (DetailPanel) findViewById(R.id.detail_panel);

		// 其他
		icon = (ImageView) findViewById(R.id.detail_icon);
		pic = (ImageView) findViewById(R.id.detail_pic);
		pic.setOnClickListener(this);
		username = (TextView) findViewById(R.id.detail_username);
		content = (TextView) findViewById(R.id.detail_content);
	}

	private void initViewsContent(Status status) {

		if (status != null) {
			final User user = status.getUser();
			if (user != null) {
				icon.setImageDrawable(WeiboDataManager.getInstance(this).getImage(user.getImage(), handler, ICON_POSITION));
				username.setText(user.getScreen_name());
			}
			final String statusPic = status.getImage();
			Log.v(TAG,"statusPic:" + statusPic);
			if (statusPic != null && !"".equals(statusPic)) {
				Log.v(TAG,"设置pic");
				pic.setImageDrawable(WeiboDataManager.getInstance(this).getImage(statusPic, handler, PIC_POSITION));
			}
			content.setText((Spannable) expressionFilter.doFilter(status.getText()));
			panel.setContentText(0, 0, status.getComments_count());
			panel.setContentText(0, 1, status.getReposts_count());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case HasButtonHeader.ID_LEFTBUTTON:
			this.finish();
			break;
		case HasButtonHeader.ID_RIGHTBUTTON:
			toHome();
			break;
		case DefaultMenu.ID_REFRESH:
			if (this.status != null) {
				refreshStatus(status);
			}
			break;
		case DefaultMenu.ID_COLLECTION:
			if (this.status != null) {
				collectStatus(status);
			}
			break;
		case DefaultMenu.ID_COMMENT:
			if (this.status != null) {
				commentStatus(status);
			}
			break;
		case DefaultMenu.ID_REPOST:
			if (this.status != null) {
				repostStatus(status);
			}
			break;
		case DefaultMenu.ID_MORE:
			toMore();
			break;
		case R.id.detail_pic:
			showDetailPic();
			break;
		}
	}

	private void showDetailPic() {
		//更大的图片展示
		DetailImageDialog dialog = new DetailImageDialog(this, status);
		dialog.setCancelable(true)
		.show();
	}

	/**
	 * To home
	 */
	private void toHome() {
		final Intent intent = new Intent(this, MainTabAct.class);
		startActivity(intent);
		this.finish();
	}

	/**
	 * 转发
	 * @param s
	 */
	private void repostStatus(Status s) {
		if (s == null) {
			return;
		}
		final Intent intent = new Intent(this, CreateCommentAct.class);
		intent.addFlags(CreateCommentAct.FLAG_REPOST);
		intent.putExtra(CreateCommentAct.FLAG_DATA, s);
		startActivity(intent);
	}

	/**
	 * 评论
	 * @param s
	 */
	private void commentStatus(Status s) {
		if (s == null) {
			return;
		}
		final Intent intent = new Intent(this, CreateCommentAct.class);
		intent.addFlags(CreateCommentAct.FLAG_COMMENT);
		intent.putExtra(CreateCommentAct.FLAG_DATA, s);
		startActivity(intent);
	}

	/**
	 * 刷新
	 * @param s
	 */
	private void refreshStatus(Status s) {
		final String id = s.getId();
		final ResponseHolder holder = new ResponseHolder(handler);
		holder.setParser(StatusParser.getInstance(true));
		WeiboDataManager.getInstance(this).getSingleStatus(holder, id);
	}

	/**
	 * 收藏
	 * @param s
	 */
	private void collectStatus(Status s) {
		final String id = s.getId();
		final ResponseHolder holder = new ResponseHolder(handler);
		holder.setParser(StatusParser.getInstance());
		WeiboDataManager.getInstance(this).createFavorite(holder, id);
	}

	private void toMore() {
		// TODO "更多"
	}
}
