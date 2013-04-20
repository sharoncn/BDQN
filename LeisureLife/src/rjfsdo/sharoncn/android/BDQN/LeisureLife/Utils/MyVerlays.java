package rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.OverlayItem;

/**
 * 覆盖物，MapActivity中使用
 * 显示指示地点的marker
 * @author sharoncn
 *
 */
public class MyVerlays extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> items = new ArrayList<OverlayItem>();

	public MyVerlays(Drawable marker) {
		super(marker);
	}

	@Override
	protected OverlayItem createItem(int position) {
		return items.get(position);
	}

	@Override
	public int size() {
		return items.size();
	}

	public void add(OverlayItem item) {
		items.add(item);
		populate();
	}
}
