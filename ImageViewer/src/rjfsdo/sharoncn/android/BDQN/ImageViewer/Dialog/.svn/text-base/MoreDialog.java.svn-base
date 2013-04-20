package rjfsdo.sharoncn.android.BDQN.ImageViewer.Dialog;

import rjfsdo.sharoncn.android.BDQN.ImageViewer.R;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 更多对话框
 * @author sharoncn
 *
 */
public class MoreDialog extends BaseDialog {
	private Context context;
	private LinearLayout container;
	private OnClickListener listener;
	
	public MoreDialog(Context context) {
		super(context);
		this.context = context;
		container = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_dialog_more, null);
		this.setView(container);
	}

	@Override
	public Builder setItems(CharSequence[] items, OnClickListener listener) {
		this.listener = listener;
		for(int i = 0; i < items.length; i++){
			TextView tv = new TextView(context);
			tv.setGravity(Gravity.CENTER_VERTICAL);
			tv.setClickable(true);
			tv.setTextColor(Color.WHITE);
			tv.setText(items[i]);
			tv.setTag(i);
			tv.setPadding(dp2Px(20), 0, 0, 0);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, dp2Px(40));
			//params.setMargins(10, 10, 10, 10);
			tv.setLayoutParams(params);
			tv.setOnClickListener(item_click);
			tv.setBackgroundResource(R.drawable.dialog_delete_button_selector);
			container.addView(tv);
			if(i < items.length - 1){
				ImageView separator = new ImageView(context);
				separator.setLayoutParams(new LayoutParams(dp2Px(240), 1));
				separator.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
				container.addView(separator);
			}
		}
		return this;
	}

	private View.OnClickListener item_click = new View.OnClickListener() {
		public void onClick(View v) {
			if(listener != null){
				listener.onClick(dialog, (Integer) v.getTag());
				dialog.dismiss();
			}
		}
	};
	
	private int dp2Px(int dp){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		int pixs = dp * (dm.densityDpi/160);
		return pixs;
	}
}
