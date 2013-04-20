package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * ¿ÉÐý×ªµÄImageView
 * @author sharoncn
 *
 */
public class RotatingImageView extends ImageView {
	private float degress = 0;
	private int width = 0, height = 0;

	public RotatingImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (width == 0)
			width = getWidth();
		if (height == 0)
			height = getHeight();
		canvas.clipRect(0, 0, width, height);
		canvas.save();
		canvas.rotate(degress, width / 2, height / 2);
		//Log.v("RotatingImageView", "Rotating   " + degress);
		super.onDraw(canvas);
		canvas.restore();
	}

	public void setDegress(float degress) {
		this.degress = degress;
		this.invalidate();
	}
}
