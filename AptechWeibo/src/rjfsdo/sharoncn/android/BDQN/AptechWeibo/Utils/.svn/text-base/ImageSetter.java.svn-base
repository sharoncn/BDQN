package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils;

import java.lang.reflect.Method;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboDataManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;

/**
 * @see rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Setter
 * @author sharoncn
 * @deprecated
 */
@Deprecated
public class ImageSetter extends ContentSetter {
	private static final String viewMethodName = "setImageDrawable";
	private static final String TAG = "ImageSetter";
	private Method viewMethod, method;
	private ContentFilter f;
	private Handler handler;
	private Context context;

	public ImageSetter(String methodName, Context context) {
		super(methodName);
		this.context = context;
	}

	@Override
	public void set(View v, Object item, int position) {
		Log.i(TAG, "" + v);
		Log.i(TAG, "" + methodName);
		String[] methodArry = methodName.split("\\.");
		final int count = methodArry.length;
		Object result = item;
		try {
			final Object[] params = new Object[] {};
			final Class<?>[] classParams = new Class<?>[] {};
			for (int i = 0; i < count; i++) {
				Log.i(TAG, "" + methodArry[i]);
				if(result != null){
					method = result.getClass().getMethod(methodArry[i], classParams);
				}
				if (method != null && result != null) {
					result = method.invoke(result, params);
				}
			}
			if (f != null && result != null) {
				result = f.doFilter(result);
			}
			viewMethod = v.getClass().getMethod(viewMethodName, new Class<?>[] { Drawable.class });
			Log.i(TAG, "" + result);
			if (viewMethod != null && result != null) {
				viewMethod.invoke(v,
						WeiboDataManager.getInstance(context).getImage(result.toString(),
								handler == null ? null : handler, position));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Setter setFilter(ContentFilter f) {
		this.f = f;
		return this;
	}

	public Setter setHandler(Handler handler) {
		this.handler = handler;
		return this;
	}
}
