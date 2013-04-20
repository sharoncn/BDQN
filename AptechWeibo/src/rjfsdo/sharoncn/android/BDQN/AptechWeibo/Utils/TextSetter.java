package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils;

import java.lang.reflect.Method;

import android.util.Log;
import android.view.View;

/**
 * @see rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Setter
 * @author sharoncn
 * @deprecated
 */
@Deprecated
public class TextSetter extends ContentSetter {
	private static final String viewMethodName = "setText";
	private static final String TAG = "TextSetter";
	private Method viewMethod, method;
	private ContentFilter f;
	
	public TextSetter(String methodName) {
		super(methodName);
	}

	@Override
	public void set(View v, Object item, int position) {
		Log.i(TAG,"" + v);
		Log.i(TAG,"" + methodName);
		String[] methodArry = methodName.split("\\.");
		final int count = methodArry.length;
		Object result = item;
		try {
			final Object[] params = new Object[] {};
			final Class<?>[] classParams = new Class<?>[] {};
			for (int i = 0; i < count; i++) {
				Log.i(TAG,"Method name:" + methodArry[i]);
				if(result != null){
					method = result.getClass().getMethod(methodArry[i], classParams);
				}
				if(method != null && result != null){
					result = method.invoke(result, params);
				}
			}
			if(f != null && result != null){
				result = f.doFilter(result);
			}
			viewMethod = v.getClass().getMethod(viewMethodName, new Class<?>[] { CharSequence.class });
			Log.i(TAG,"" + result);
			if(viewMethod != null && result != null){
				viewMethod.invoke(v, result);
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

}
