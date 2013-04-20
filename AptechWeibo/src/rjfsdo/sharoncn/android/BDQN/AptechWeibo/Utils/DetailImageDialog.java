package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboDataManager;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.HasOriginalImage;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * 微博详情中用于显示大图的dialog
 * @author sharoncn
 *
 */
public class DetailImageDialog extends Builder {
	protected static final String TAG = "DetailImageDialog";
	private HasOriginalImage hasImage;
	protected AlertDialog dialog = null;
	private ImageView img;
	private ProgressBar pb;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WeiboDataManager.MSGTYPE_IMAGEOK:
				final Bundle data = msg.getData();
				if (data.getBoolean(WeiboDataManager.FLAG_ISSUCCESS)) {
					final String key = data.getString(WeiboDataManager.FLAG_IMAGEKEY);
					final Bitmap bmp = (Bitmap) data.getParcelable(WeiboDataManager.FLAG_DATA);
					ImageCache.getInstance(mContext).put(key, bmp);
					Log.i(TAG,"获得的图片宽高:" + bmp.getWidth() + "   " + bmp.getHeight());
					post(new SetImageRunnable(bmp));
					pb.setVisibility(View.GONE);
				}
				break;
			}
			super.handleMessage(msg);
		}
	};
	private Context mContext;

	public DetailImageDialog(Context context) {
		super(context);
		this.mContext = context;
	}

	public DetailImageDialog(Context context, HasOriginalImage hasImage) {
		this(context);
		this.hasImage = hasImage;
		initView(context);
		initImage();
	}

	private void initImage() {
		final String url = hasImage.getOriginal_pic();
		if (url != null) {
			final Drawable d = WeiboDataManager.getInstance(mContext).getImage(url, handler, -10);
			Log.i(TAG,"从缓存获得的图片宽高:" + d.getMinimumWidth() + "   " + d.getMinimumHeight());
			handler.post(new SetImageRunnable(((BitmapDrawable)d).getBitmap()));
		}
	}

	private void initView(Context context) {
		final View view = LayoutInflater.from(context).inflate(R.layout.dialog_detailimage, null);
		this.setView(view);
		img = (ImageView) view.findViewById(R.id.dlg_detail_iv);
		pb = (ProgressBar) view.findViewById(R.id.dlg_detail_pb);
		pb.setVisibility(View.GONE);
		//pb.setVisibility(View.VISIBLE);
	}

	class SetImageRunnable implements Runnable{
		private Bitmap bmp;
		public SetImageRunnable(Bitmap bmp) {
			this.bmp = bmp;
		}
		
		@Override
		public void run() {
			final int width = bmp.getWidth();
			final int height = bmp.getHeight();
			final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
			img.setLayoutParams(params);
			img.setBackgroundDrawable(new BitmapDrawable(bmp));
			img.postInvalidate();
		}
		
	}
	
	@Override
	public AlertDialog create() {
		if (dialog == null) {
			dialog = super.create();
		}

		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
				WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		dialog.show();

		FrameLayout fl = (FrameLayout) dialog.findViewById(android.R.id.custom);
		if (fl != null) {
			((FrameLayout) fl.getParent()).setBackgroundResource(android.R.color.transparent);
			//((FrameLayout) fl.getParent()).setBackgroundColor(Color.argb(0xFF, 0xFF, 0xFF, 0xFF));
		}

		return dialog;
	}

	@Override
	public AlertDialog show() {
		return create();
	}

}
