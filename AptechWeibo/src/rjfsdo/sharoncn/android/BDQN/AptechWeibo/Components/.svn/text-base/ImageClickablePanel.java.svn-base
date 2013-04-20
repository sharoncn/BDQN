package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ´øÍ¼Æ¬¿òµÄPanel
 * @author sharoncn
 *
 */
public class ImageClickablePanel extends ClickableOneColumnPanel {
	private ImageView img;
	public ImageClickablePanel(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void prapare() {
		rowNum = 1;
		contents = new String[]{mContext.getString(R.string.change_icon)};
	}
	
	@Override
	protected void initView(int id, String content) {
		View view = inflater.inflate(R.layout.panel_simple_image_clickable, null);
		img = (ImageView) view.findViewById(R.id.panel_title);
		TextView tv_content = (TextView) view.findViewById(R.id.panel_content);
		tv_content.setText(content);
		allowSetContentViews.add(tv_content);
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(params);
		view.setId(id);
		add(view);
	}

	public void setImage(int index,Bitmap bmp){
		img.setImageBitmap(bmp);
	}
	
}
