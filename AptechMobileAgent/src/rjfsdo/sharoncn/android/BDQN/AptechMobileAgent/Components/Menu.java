package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Components;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import android.content.Context;
import android.util.AttributeSet;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public final class Menu extends LinearLayout implements OnClickListener {
	public static final int BTN_ID_ALL = R.id.menu_all;
	public static final int BTN_ID_NOSYS = R.id.menu_nosys;
	public static final int BTN_ID_SYS = R.id.menu_sys;
	//private static final String TAG = "Menu";

	private static int layoutRes = R.layout.components_menu_default;
	private static LayoutInflater mInflater;
	private Button btn_all, btn_nosys, btn_sys;
	private OnClickListener listener;

	public Menu(Context context) {
		this(context, null);
	}

	public Menu(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (mInflater == null)
			mInflater = LayoutInflater.from(context);
		initViews();
	}

	private void initViews() {
		final View view = mInflater.inflate(layoutRes, null);
		final LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(params);
		this.addView(view);
		btn_all = (Button) view.findViewById(BTN_ID_ALL);
		btn_nosys = (Button) view.findViewById(BTN_ID_NOSYS);
		btn_sys = (Button) view.findViewById(BTN_ID_SYS);

		btn_all.setOnClickListener(this);
		btn_all.setBackgroundResource(R.drawable.btn_pressed);
		btn_nosys.setOnClickListener(this);
		btn_sys.setOnClickListener(this);
		btn_all.setTag(true);
		btn_nosys.setTag(false);
		btn_sys.setTag(false);
	}

	/**
	 * ÉèÖÃmenuµÄ°´Å¥Click¼àÌýÆ÷
	 * @param l ¼àÌýÆ÷
	 */
	public void setButtonOnClickListener(OnClickListener l) {
		this.listener = l;
	}

	@Override
	public void onClick(View v) {
		setViewBg(v, R.drawable.btn_pressed);
		if (listener != null)
			listener.onClick(v);
		toNormal(v);
		v.setTag(true);
	}

	private void toNormal(View v) {
		if (!v.equals(btn_all)) {
			setViewBg(btn_all, R.drawable.btn_normal);
			btn_all.setTag(false);
		}
		if (!v.equals(btn_nosys)) {
			setViewBg(btn_nosys, R.drawable.btn_normal);
			btn_nosys.setTag(false);
		}
		if (!v.equals(btn_sys)) {
			setViewBg(btn_sys, R.drawable.btn_normal);
			btn_sys.setTag(false);
		}
	}

	private void setViewBg(View view, int bgRes) {
		view.setBackgroundResource(bgRes);
	}

	public int getActiveBtn() {
		if(Boolean.parseBoolean("" + btn_all.getTag())){
			return BTN_ID_ALL;
		}else if(Boolean.parseBoolean("" + btn_nosys.getTag())){
			return BTN_ID_NOSYS;
		}else{
			return BTN_ID_SYS;
		}
	}

}
