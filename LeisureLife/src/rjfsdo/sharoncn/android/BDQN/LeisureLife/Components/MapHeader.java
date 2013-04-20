package rjfsdo.sharoncn.android.BDQN.LeisureLife.Components;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 地图Activity中使用的头部
 * @author sharoncn
 *
 */
public class MapHeader extends BaseHeader {
	public static final int BUTTON_MODE_TRANSIT = R.id.map_mode_transit;
	public static final int BUTTON_MODE_DRIVING = R.id.map_mode_driving;
	public static final int BUTTON_MODE_WALK = R.id.map_mode_walk;
	private View container;
	
	public MapHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		container = LayoutInflater.from(context).inflate(R.layout.layout_header_map, null);
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, Util.dp2Px(context, 60));
		params.gravity = Gravity.CENTER;
		container.setLayoutParams(params);
		container.setBackgroundResource(R.color.dark_gray);
		this.addView(container);
		
		View btn = container.findViewById(BUTTON_MODE_TRANSIT);
		this.registerButtons(btn);
		btn = container.findViewById(BUTTON_MODE_DRIVING);
		this.registerButtons(btn);
		btn = container.findViewById(BUTTON_MODE_WALK);
		this.registerButtons(btn);
	}

	@Override
	public void setBackgroundResource(int resid) {
		this.container.setBackgroundResource(resid);
	}

	@Override
	public void setTitle(String title) {}

	@Override
	public void setTitleColor(int resid) {}

	@Override
	public void setTitleTextSize(float size) {}

	@Override
	public void setTitleVisibility(int visibility) {}

}
