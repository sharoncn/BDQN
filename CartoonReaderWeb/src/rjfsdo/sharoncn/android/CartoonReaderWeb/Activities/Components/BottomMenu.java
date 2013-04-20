package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * ��װ�ײ�menu
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
	 * Ϊ"��ҳ"��ť���ü�����
	 * @param l Ҫ���õļ�����
	 * @return
	 */
	public BottomMenu setOnHomeClickListener(OnClickListener l){
		home.setOnClickListener(l);
		return this;
	}
	
	/**
	 * Ϊ"���"��ť���ü�����
	 * @param l Ҫ���õļ�����
	 * @return
	 */
	public BottomMenu setOnshelfClickListener(OnClickListener l){
		shelf.setOnClickListener(l);
		return this;
	}
	
	/**
	 * Ϊ"���"��ť���ü�����
	 * @param l Ҫ���õļ�����
	 * @return
	 */
	public BottomMenu setOnBrowserClickListener(OnClickListener l){
		browser.setOnClickListener(l);
		return this;
	}
	
	/**
	 * Ϊ"����"��ť���ü�����
	 * @param l Ҫ���õļ�����
	 * @return
	 */
	public BottomMenu setOnSearchClickListener(OnClickListener l){
		search.setOnClickListener(l);
		return this;
	}
	
	/**
	 * Ϊ"����"��ť���ü�����
	 * @param l Ҫ���õļ�����
	 * @return
	 */
	public BottomMenu setOnMoreClickListener(OnClickListener l){
		more.setOnClickListener(l);
		return this;
	}
	
	/**
	 * Ϊ����Button���ü����������ཫΪ����Button����tag������home��shelf��browser��search��more
	 * �������ҳ��ť����OnClick�������ݵ�view��tagΪ"home"��shelf��Ӧ��ܰ�ť��browser��Ӧ�����search
	 * ��Ӧ������more��Ӧ����
	 * @param l Ҫ���õļ�����
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
