package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * 可点击的只有一列的Panel
 * @author sharoncn
 *
 */
public class ClickableOneColumnPanel extends OneColumnPanel implements
		OnClickListener {
	protected ArrayList<TextView> allowSetContentViews = new ArrayList<TextView>();
	private static final String TAG = "ClickableOneColumnPanel";
	private OnClickListener click;
	public static final int ID_ONE = 0x041;
	protected int rowNum = 2;
	protected String[] contents;

	public ClickableOneColumnPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		prapare();
		initViews(rowNum);
	}

	protected void prapare() {
		contents = new String[] { mContext.getString(R.string.collection),
				mContext.getString(R.string.blacklist) };
	}

	private void initViews(int rowNum) {
		// View view = inflater.inflate(R.layout.panel_simple_info_clickable,
		// null);
		// TextView tv_title = (TextView) view.findViewById(R.id.panel_title);
		// TextView tv_content = (TextView)
		// view.findViewById(R.id.panel_content);
		// allowSetContentViews.add(tv_content);
		// tv_title.setText(mContext.getString(R.string.collection));
		// view.setId(ID_ONE);
		// add(view);
		//
		// view = inflater.inflate(R.layout.panel_simple_info_clickable, null);
		// tv_title = (TextView) view.findViewById(R.id.panel_title);
		// tv_content = (TextView) view.findViewById(R.id.panel_content);
		// allowSetContentViews.add(tv_content);
		// tv_title.setText(mContext.getString(R.string.blacklist));
		// view.setId(ID_ONE + 1);
		// add(view);
		for (int i = 0; i < rowNum; i++) {
			initView(ID_ONE + i, contents[i]);
		}
	}

	protected void initView(int id, String content) {
		View view = inflater
				.inflate(R.layout.panel_simple_info_clickable, null);
		TextView tv_title = (TextView) view.findViewById(R.id.panel_title);
		TextView tv_content = (TextView) view.findViewById(R.id.panel_content);
		allowSetContentViews.add(tv_content);
		tv_title.setText(content);
		view.setId(id);
		add(view);
	}

	protected void add(View view) {
		//不明白为什么吗需要点击两次才能出发onClick，待测
		((ViewGroup) view).setAddStatesFromChildren(true);
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		view.setClickable(true);
		view.setOnClickListener(this);
		this.addItem(view);
	}

	/**
	 * 为Panel的item设置内容
	 * 
	 * @param index
	 *            取值范围[0-1]
	 * @param resId
	 *            要设置的内容
	 */
	public void setContentText(int index, int resId) {
		setContentText(index, mContext.getString(resId));
	}

	/**
	 * 为Panel的item设置内容
	 * 
	 * @param index
	 *            取值范围[0-1]
	 * @param text
	 *            要设置的内容
	 */
	public void setContentText(int index, String text) {
		if (index > rowNum || index < 0) {
			throw new ArrayIndexOutOfBoundsException("index");
		}
		TextView tv = allowSetContentViews.get(index);
		if (tv != null) {
			tv.setText(text);
		}
	}

	@Override
	public void onClick(View v) {
		Log.i(TAG, "onclick");
		Animation anim = AnimationUtils.loadAnimation(mContext,
				R.anim.click_anim);
		v.startAnimation(anim);
		if (click != null) {
			click.onClick(v);
		}
	}

	public void setItemOnClickListener(OnClickListener l) {
		this.click = l;
	}
}
