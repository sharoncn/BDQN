package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 首页,排行,推荐,分类等activity的信息显示的基类
 * 用LinearLayout封装各View，可作为组件使用
 * @author sharoncn
 *
 */
public abstract class BaseCanvas extends LinearLayout {
	private Context context;
	private ImageButton more;
	protected LinearLayout container;
	private TextView title;
	protected ListView list;
	private LinearLayout container_title;
	
	public BaseCanvas(Context context) {
		this(context,null);
	}

	public BaseCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initComponents();
	}

	private void initComponents(){
		//初始化头部
		initHeader();
		//初始化内容部分
		initContent();
	}

	/**
	 * 初始化头部模块
	 */
	private void initHeader(){
		//头部容器
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		container = new LinearLayout(this.context);
		container.setOrientation(LinearLayout.HORIZONTAL);
		container.setLayoutParams(params);
		container.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		container.setBackgroundResource(R.drawable.book_bg);
		
		//标题容器
		LayoutParams params_Title_Container = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params_Title_Container.weight = 2;
		container_title = new LinearLayout(this.context);
		container_title.setOrientation(LinearLayout.HORIZONTAL);
		container_title.setLayoutParams(params_Title_Container);
		container_title.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		container_title.setBackgroundResource(android.R.color.transparent);
		
		//标题
		title = new TextView(context);
		LayoutParams params_title = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params_title.leftMargin = 20;
		title.setLayoutParams(params_title);
		title.setTextColor(Color.WHITE);
		title.setTextSize(15);
		title.setText("全部");
		container_title.addView(title);
		title.setVisibility(GONE);
		
		//更多按钮
		more = new ImageButton(context);
		LayoutParams params_btn = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params_btn.rightMargin = 20;
		more.setLayoutParams(params_btn);
		more.setBackgroundResource(R.drawable.book_bg_more);
		more.setVisibility(VISIBLE);
		
		container.addView(container_title);
		container.addView(more);
		this.addView(container);
		
		//内容ListView
		list = new ListView(context);
		LayoutParams params_list = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		params_list.setMargins(5, 5, 5, 5);
		list.setLayoutParams(params_list);
		list.setDivider(getResources().getDrawable(R.drawable.splitline));
		list.setPadding(0, 0, 0, 2);
		
		list.setCacheColorHint(Color.TRANSPARENT);
		this.addView(list);
	}
	
	/**
	 * 为"更多"按钮设置监听器
	 * @param l OnClickListener 监听器
	 */
	public void setHeaderButtonOnClickListener(View.OnClickListener l){
		more.setOnClickListener(l);
	}
	
	/**
	 * 设置更多按钮的可见性,默认可见
	 * @param visibility One of VISIBLE, INVISIBLE, or GONE. 
	 */
	public void setHeaderButtonVisibility(int visibility){
		more.setVisibility(visibility);
	}
	
	/**
	 * 设置HeaderButton的背景图片，默认是"更多"图片
	 * @param resid 图片ID
	 */
	public void setHeaderButtonBackgoundResources(int resid){
		more.setBackgroundResource(resid);
	}
	
	/**
	 * 设置背景
	 * @param resid int 背景图片ID
	 */
	public void setBackgroundResource(int resid){
		container.setBackgroundResource(resid);
	}
	
	/**
	 * 设置标题
	 * @param resId
	 */
	public void setTitleText(int resId){
		setTitleText(context.getString(resId));
	}
	
	/**
	 * 设置标题
	 * @param text
	 */
	public void setTitleText(String text){
		title.setText(text);
	}
	
	/**
	 * 设置标题可见性，默认不可见
	 * @param visibility One of VISIBLE, INVISIBLE, or GONE. 
	 */
	public void setTitleVisibility(int visibility){
		title.setVisibility(visibility);
	}
	
	/**
	 * 设置整个Header的可见性
	 * @param visibility
	 */
	public void setHeaderVisibility(int visibility){
		container.setVisibility(visibility);
	}
	
	/**
	 * 获得用于内容展示的ListView对象
	 * @return ListView
	 */
	public ListView getContentListView(){
		return list;
	}
	
	public void hideDivider(){
		list.setDivider(context.getResources().getDrawable(android.R.color.transparent));
	}
	
	protected abstract void initContent();
}
