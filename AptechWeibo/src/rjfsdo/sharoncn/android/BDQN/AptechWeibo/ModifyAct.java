package rjfsdo.sharoncn.android.BDQN.AptechWeibo;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.CitySelectPanel;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.ClickableOneColumnPanel;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.EditablePanel;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.HasButtonHeader;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components.ImageClickablePanel;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.User;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;

/**
 * 修改个人信息界面
 * @author sharoncn
 *
 */
public class ModifyAct extends Activity implements OnClickListener {
	public static final String FLAG_USER = "user_data";
	public static final String FLAG_IMG = "icon_data";
	private static final String TAG = "ModifyAct";
	private HasButtonHeader header;
	private ImageClickablePanel iconPanel;
	private EditablePanel namePanel;
	private CitySelectPanel cityPanel;

	private RadioButton male, famale;
	private Button submit, cancel;

	private User user;
	private Bitmap icon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_editinfo);
		Bundle data = getIntent().getExtras();
		if (data.containsKey(FLAG_USER)) {
			user = (User) data.getSerializable(FLAG_USER);
		}
		if (data.containsKey(FLAG_IMG)) {
			icon = data.getParcelable(FLAG_IMG);
		}
		initViews();
	}

	private void initViews() {
		// Header
		header = (HasButtonHeader) findViewById(R.id.modify_header);
		
		// IconPanel
		iconPanel = (ImageClickablePanel) findViewById(R.id.modify_panel_icon);
		
		// NamePanel
		namePanel = (EditablePanel) findViewById(R.id.modify_panel_editinfo);
		
		// CityPanel
		cityPanel = (CitySelectPanel) findViewById(R.id.modify_panel_city);

		// others
		male = (RadioButton) findViewById(R.id.modify_rb_m);
		famale = (RadioButton) findViewById(R.id.modify_rb_f);
		submit = (Button) findViewById(R.id.modify_btn_submit);
		cancel = (Button) findViewById(R.id.modify_btn_cancel);

		setup();
	}

	private void setup() {
		header.setLeftButtonBackgroundRes(R.drawable.header_btn_back);
		header.setRightButtonBackgroundRes(R.drawable.modify_right_btn);
		header.setHeaderTitle(R.string.modify_info);
		header.setButtonOnClickListener(this);
		iconPanel.setItemOnClickListener(this);
		submit.setOnClickListener(this);
		cancel.setOnClickListener(this);
		
		iconPanel.setImage(0,icon != null ? icon : ((BitmapDrawable) this.getResources().getDrawable(R.drawable.nohead))
				.getBitmap());
		
		if(user == null){
			return;
		}
		namePanel.setEditContent(0, user.getScreen_name());
		namePanel.setEditContent(1, user.getDescription());
		
		cityPanel.setCurrentProvince(Integer.parseInt(user.getProvince()));
		cityPanel.setCurrentCity(user.getCity());
		
		String gender = user.getGender();
		if(gender.equals("m")){
			male.setChecked(true);
		}else{
			famale.setChecked(true);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case HasButtonHeader.ID_LEFTBUTTON:
			exit();
			//TODO "返回"
			Log.i(TAG,"ImageButtonHeader.ID_LEFTBUTTON is click");
			break;
		case HasButtonHeader.ID_RIGHTBUTTON:
			//TODO "不明白是做什么的"
			Log.i(TAG,"ImageButtonHeader.ID_RIGHTBUTTON is click");
			break;
		case R.id.modify_btn_submit:
			submitToServer();
			//TODO "提交"
			Log.i(TAG,"modify_btn_submit is click");
			break;
		case R.id.modify_btn_cancel:
			exit();
			//TODO "返回"
			Log.i(TAG,"modify_btn_cancel is click");
			break;
		case ClickableOneColumnPanel.ID_ONE:
			chooseIcon();
			//TODO "选择头像"
			Log.i(TAG,"ImageClickablePanel.ID_ONE is click");
			break;
		}
	}
	
	private void exit(){
		this.finish();
	}
	
	private void submitToServer(){
		//新浪暂时没有提供修改个人资料的接口
	}
	
	private void chooseIcon(){
		
	}
}
