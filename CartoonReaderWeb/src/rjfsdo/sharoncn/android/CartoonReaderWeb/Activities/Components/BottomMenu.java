package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * 封装底部menu
 * @author sharoncn
 *
 */
public class BottomMenu extends LinearLayout {
	private LinearLayout container;
	private ImageButton home,shelf,browser,search,more;
	public static final String TAG_HOME = "home";
	public static final String TAG_SHELF = "shelf";
	public static final String TAG_BROWSER = "browser";
	public static final String TAG_SEARCH = "search";
	public static final String TAG_MORE = "more";
	
	public BottomMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = LayoutInflater.from(context);
		container = (LinearLayout) inflater.inflate(R.layout.layout_bottom_menu, null);
		this.addView(container);
		home = (ImageButton) container.findViewById(R.id.menu_btn_home);
		home.setTag("home");
		shelf = (ImageButton) container.findViewById(R.id.menu_btn_shelf);
		shelf.setTag("shelf");
		browser = (ImageButton) container.findViewById(R.id.menu_btn_browser);
		browser.setTag("browser");
		search = (ImageButton) container.findViewById(R.id.menu_btn_search);
		search.setTag("search");
		more = (ImageButton) container.findViewById(R.id.menu_btn_more);
		more.setTag("more");
	}
	
	/**
	 * 为"首页"按钮设置监听器
	 * @param l 要设置的监听器
	 * @return
	 */
	public BottomMenu setOnHomeClickListener(OnClickListener l){
		home.setOnClickListener(l);
		return this;
	}
	
	/**
	 * 为"书架"按钮设置监听器
	 * @param l 要设置的监听器
	 * @return
	 */
	public BottomMenu setOnshelfClickListener(OnClickListener l){
		shelf.setOnClickListener(l);
		return this;
	}
	
	/**
	 * 为"浏览"按钮设置监听器
	 * @param l 要设置的监听器
	 * @return
	 */
	public BottomMenu setOnBrowserClickListener(OnClickListener l){
		browser.setOnClickListener(l);
		return this;
	}
	
	/**
	 * 为"搜索"按钮设置监听器
	 * @param l 要设置的监听器
	 * @return
	 */
	public BottomMenu setOnSearchClickListener(OnClickListener l){
		search.setOnClickListener(l);
		return this;
	}
	
	/**
	 * 为"更多"按钮设置监听器
	 * @param l 要设置的监听器
	 * @return
	 */
	public BottomMenu setOnMoreClickListener(OnClickListener l){
		more.setOnClickListener(l);
		return this;
	}
	
	/**
	 * 为各个Button设置监听器，此类将为各个Button设置tag，包括home，shelf，browser，search，more
	 * 当点击首页按钮，则OnClick方法传递的view的tag为"home"。shelf对应书架按钮，browser对应浏览，search
	 * 对应搜索，more对应更多
	 * @param l 要设置的监听器
	 * @return
	 */
	public BottomMenu setUnifiedClickListener(OnClickListener l){
		setOnHomeClickListener(l);
		setOnshelfClickListener(l);
		setOnBrowserClickListener(l);
		setOnSearchClickListener(l);
		setOnMoreClickListener(l);
		return this;
	}
}
