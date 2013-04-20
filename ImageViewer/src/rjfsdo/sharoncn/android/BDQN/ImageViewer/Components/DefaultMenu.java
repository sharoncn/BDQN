package rjfsdo.sharoncn.android.BDQN.ImageViewer.Components;

import rjfsdo.sharoncn.android.BDQN.ImageViewer.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class DefaultMenu extends BaseMenu{
	public static final int ID_DELETE = R.id.menu_delete;
	public static final int ID_INFO = R.id.menu_info;
	public static final int ID_MORE = R.id.menu_more;
	public static final int INDEX_DELETE = 0;
	public static final int INDEX_INFO = 1;
	public static final int INDEX_MORE = 2;
	private ImageButton btn_delete,btn_info,btn_more;
	private View separator;
	private LinearLayout container;

	public DefaultMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		//inflate资源时，如果group参数设置为NULL会造成LayoutParams丢失，
		//有人说如果要得到正确的LayoutParams需要指定root，需要将attachToRoot设置为false，
		//经过测试依然无效，是不是我指定的root参数有问题？不论如何依然得不到正确的LayoutParams。以后再查查
		View view = LayoutInflater.from(context).inflate(R.layout.layout_components_menu, null);//(ViewGroup) this.getParent(),false);
		separator = view.findViewById(R.id.menu_separator);
		container = (LinearLayout) view.findViewById(R.id.menu_container);
		btn_delete = (ImageButton) view.findViewById(R.id.menu_delete);
		btn_info = (ImageButton) view.findViewById(R.id.menu_info);
		btn_more = (ImageButton) view.findViewById(R.id.menu_more);
		view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(view);
		
		this.registerClickableView(INDEX_DELETE,btn_delete);
		this.registerClickableView(INDEX_INFO,btn_info);
		this.registerClickableView(INDEX_MORE,btn_more);
	}

	@Override
	public void setBackgroundResource(int resid) {
		container.setBackgroundResource(resid);
		super.setBackgroundResource(resid);
	}
	
	/**
	 * 设置分隔符的可见性
	 * @param visibility
	 */
	public void setSeparatorVisibility(int visibility){
		separator.setVisibility(visibility);
	}
}
