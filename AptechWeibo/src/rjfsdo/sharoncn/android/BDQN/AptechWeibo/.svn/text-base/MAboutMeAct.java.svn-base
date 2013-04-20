package rjfsdo.sharoncn.android.BDQN.AptechWeibo;

import java.util.ArrayList;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.AddrPanel;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.ClickableOneColumnPanel;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.CountPanel;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.DefaultHeader;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.ProvincesProvider;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.ResponseHolder;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.UserIdParser;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.UserParser;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Uid;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.User;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboDataManager;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Province;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.ImageCache;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Util;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 我的信息
 * @author sharoncn
 *
 */
public class MAboutMeAct extends BaseActivity implements OnClickListener {
	private static final String TAG = "MAboutMeAct";
	private DefaultHeader header;
	private AddrPanel addrPanel;
	private CountPanel countPanel;
	private ClickableOneColumnPanel clickPanel;
	private User user;
	private Bitmap icon;
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			switch (msg.what) {
			case WeiboDataManager.MSGTYPE_USERID:
				if (bundle.getBoolean(WeiboDataManager.FLAG_ISSUCCESS)) {
					List<Object> data = (List<Object>) bundle.getSerializable(WeiboDataManager.FLAG_DATA);
					if(data.size() > 0){
						long id = ((Uid)data.get(0)).getUid();
						requestUserInfo(id);
					}
					Log.v(TAG, "获得的数据数量:" + data.size());
				} else {
					Util.showMsg(MAboutMeAct.this, getString(R.string.getdata_fail) + bundle.getString(WeiboDataManager.FLAG_ERR_MSG));
				}
				break;
			case WeiboDataManager.MSGTYPE_USER:
				if (bundle.getBoolean(WeiboDataManager.FLAG_ISSUCCESS)) {
					List<Object> data = (List<Object>) bundle.getSerializable(WeiboDataManager.FLAG_DATA);
					if(data.size() > 0){
						user = (User) data.get(0);
						initContent(user);
					}
					Log.v(TAG, "获得的数据数量:" + data.size());
				} else {
					Util.showMsg(MAboutMeAct.this, getString(R.string.getdata_fail) + bundle.getString(WeiboDataManager.FLAG_ERR_MSG));
				}
				break;
			case WeiboDataManager.MSGTYPE_IMAGEOK:
				if (bundle.getBoolean(WeiboDataManager.FLAG_ISSUCCESS)) {
					final String key = bundle.getString(WeiboDataManager.FLAG_IMAGEKEY);
					Log.i(TAG,"请求图片返回成功,图片路径:" + key);
					final Bitmap bmp = (Bitmap) bundle.getParcelable(WeiboDataManager.FLAG_DATA);
					ImageCache.getInstance(MAboutMeAct.this).put(key, bmp);
					icon = bmp;
					img.setImageBitmap(icon);
				}
				break;
			}
			super.handleMessage(msg);
		}
	};
	private ImageView img;
	private Button btnEdit;
	private TextView tv_username;
	private ImageView gender;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_aboutme);
		
		img = (ImageView) findViewById(R.id.aboutme_img);
		btnEdit = (Button) findViewById(R.id.aboutme_edit);
		btnEdit.setOnClickListener(this);
		tv_username = (TextView) findViewById(R.id.aboutme_nickname);
		gender = (ImageView) findViewById(R.id.aboutme_gender);
		
		initHeader();
		initPanels();
		if(WeiboDataManager.uid == -1){
			requestUserId();
		}else{
			requestUserInfo(WeiboDataManager.uid);
		}
	}
	
	private void requestUserInfo(long id) {
		ResponseHolder holder = new ResponseHolder(handler, WeiboDataManager.MSGTYPE_USER);
		holder.setParser(UserParser.getInstance());
		WeiboDataManager.getInstance(this).getUserInfo(holder,id);
	}

	private void requestUserId(){
		ResponseHolder holder = new ResponseHolder(handler, WeiboDataManager.MSGTYPE_USERID);
		holder.setParser(UserIdParser.getInstance());
		WeiboDataManager.getInstance(this).getUserId(holder);
	}

	private void initPanels() {
		addrPanel = (AddrPanel) findViewById(R.id.aboutme_panel);

		countPanel = (CountPanel) findViewById(R.id.aboutme_countpanel);

		clickPanel = (ClickableOneColumnPanel) findViewById(R.id.aboutme_clickablepanel);
		clickPanel.setClickable(true);
		clickPanel.setItemOnClickListener(this);
	}

	private void initContent(User user) {
		if(user == null){
			return;
		}
		//头像
		img.setImageDrawable(WeiboDataManager.getInstance(this).getImage(user.getImage(), handler, -100));
		//昵称
		tv_username.setText(user.getScreen_name());
		//性别
		gender.setEnabled(user.getGender().equals("m"));
		//地址
		addrPanel.setContentText(0, user.getLocation());
		//描述
		addrPanel.setContentText(1, user.getDescription());
		//统计信息
		countPanel.setContentText(0, 0, user.getFriends_count());
		countPanel.setContentText(0, 1, user.getStatuses_count());
		countPanel.setContentText(1, 0, user.getFollowers_count());
		countPanel.setContentText(1, 1, "0");//话题数量指的是什么，不是很明白，自己发表的微博数量？那个是微博数量。
		//收藏和黑名单
		clickPanel.setContentText(0, user.getFavourites_count());
	}
	 
	private void initHeader() {
		header = (DefaultHeader) findViewById(R.id.aboutme_header);
		header.setHeaderTitle(R.string.aboutme);

		ProvincesProvider pp = ProvincesProvider.getInstance(this);
		ArrayList<Province> provinces = pp.getProvinces();

		for (Province p : provinces) {
			System.out.println(p.toString());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case ClickableOneColumnPanel.ID_ONE:
			toCollection();
			break;
		case ClickableOneColumnPanel.ID_ONE + 1:
			// TODO "黑名单"
			break;
		case R.id.aboutme_edit:
			toModify();
			Log.v(TAG, "edit");
			break;
		}
	}

	private void toCollection() {
		Intent intent = new Intent(this,CollectionAct.class);
		startActivity(intent);
	}

	private void toModify() {
		icon = ((BitmapDrawable)(img.getDrawable())).getBitmap();
		if(user != null && icon != null){
			Intent intent = new Intent(this,ModifyAct.class);
			Bundle data = new Bundle();
			data.putSerializable(ModifyAct.FLAG_USER, user);
			data.putParcelable(ModifyAct.FLAG_IMG, icon);
			intent.putExtras(data);
			startActivity(intent);
		}else{
			Util.showMsg(this, R.string.pic_preparing);
		}
	}

}
