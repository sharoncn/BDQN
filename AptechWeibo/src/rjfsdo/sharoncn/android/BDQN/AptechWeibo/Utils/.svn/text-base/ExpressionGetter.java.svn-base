package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 如果每次遇到表情都去网上下那太麻烦了，提前下载到项目中或者Loading时开始下载保存到缓存目录
 * 提前下载都项目中这样很方便，但是扩展性要差一截。但是要是每次都下感觉也挺耗流量的。
 * @author sharoncn
 *
 */
public class ExpressionGetter implements ImageGetter {
	private static final String TAG = "ExpressionGetter";
	private static HashMap<String, String> expresions;
	private static AssetManager am;
	private static ExpressionGetter me;
	private static Gson gson;

	static {
		me = new ExpressionGetter();
	}

	private ExpressionGetter() {
	}

	public static ExpressionGetter getInstance(Context context) {
		if (am == null) {
			am = context.getAssets();
			initExpressions();
		}
		return me;
	}

	private static void initExpressions() {
		if (gson == null)
			gson = new Gson();
		if (expresions != null) {
			// 程序运行期间,只执行一次
			return;
		}
		InputStream is = null;
		try {
			is = am.open("expressions.txt");
			Reader reader = new InputStreamReader(is);
			expresions = gson.fromJson(reader, new TypeToken<HashMap<String, String>>() {
			}.getType());
			// testExpressions();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	private void testExpressions() {
		if (expresions != null) {
			Iterator<String> it = expresions.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = expresions.get(key);
				Log.i(TAG, "Expressions key:" + key + "  value:" + value);
			}
		}
	}

	@Override
	public Drawable getDrawable(String source) {
		// System.out.println(source);
		InputStream is = null;
		try {
			is = am.open(source);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (is != null) {

			Drawable d = Drawable.createFromStream(is, null);
			if (d == null) {
				System.out.println("drawable is null");
				return null;
			}
			// 获得的图片太小，放大3倍(M9上测试3倍合适)
			d.setBounds(0, 0, d.getIntrinsicWidth() * Constants.MAGNIFICATION, d.getIntrinsicHeight() * Constants.MAGNIFICATION);
			// System.out.println(d.getBounds().toShortString());
			return d;
		} else {
			return null;
		}
	}

	public String format(String text) {
		if (expresions != null) {
			Iterator<String> it = expresions.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = expresions.get(key);
				// Log.i(TAG,"Expressions key:" + key + "  value:" + value);
				text = text.replace(key, "<img src='" + value + "'/>");
			}
		}
		// System.out.println(text);
		return text;
	}
}
