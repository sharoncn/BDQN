package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 更多界面使用的Panel
 * @author sharoncn
 *
 */
public final class MorePanel extends ImageClickablePanel {
	private static final String TAG = "MorePanel";
	private static ArrayList<Bitmap> bmps = new ArrayList<Bitmap>();
			
	public MorePanel(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void prapare() {
		Resources res = mContext.getResources();
		Bitmap bmp = ((BitmapDrawable)res.getDrawable(R.drawable.moreitems_setting_icon)).getBitmap();
		Log.i(TAG, "bmp is null?" + (bmp == null));
		Log.i(TAG, "bmps is null?" + (bmps == null));
		bmps.add(bmp);
		
		bmp = ((BitmapDrawable)res.getDrawable(R.drawable.moreitems_accountmanage_icon)).getBitmap();
		bmps.add(bmp);
		
		bmp = ((BitmapDrawable)res.getDrawable(R.drawable.moreitems_browweibo_icon)).getBitmap();
		bmps.add(bmp);
		
		bmp = ((BitmapDrawable)res.getDrawable(R.drawable.moreitems_officialweibo_icon)).getBitmap();
		bmps.add(bmp);
		
		bmp = ((BitmapDrawable)res.getDrawable(R.drawable.moreitems_feedback_icon)).getBitmap();
		bmps.add(bmp);
		
		bmp = ((BitmapDrawable)res.getDrawable(R.drawable.moreitems_about_icon)).getBitmap();
		bmps.add(bmp);
		
		rowNum = 6;
		contents = new String[]{
				mContext.getString(R.string.setup),
				mContext.getString(R.string.accountmgr),
				mContext.getString(R.string.browsermode),
				mContext.getString(R.string.homepage),
				mContext.getString(R.string.feedback),
				mContext.getString(R.string.about)
		};
	}

	@Override
	protected void initView(int id, String content) {
		Log.i(TAG,"id:" + id);
		super.initView(id, content);
		setImage(id,bmps.get(id - ID_ONE));
	}
	
}
