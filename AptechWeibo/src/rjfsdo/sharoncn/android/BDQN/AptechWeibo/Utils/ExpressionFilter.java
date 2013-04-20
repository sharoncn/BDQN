package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils;

import android.content.Context;
import android.text.Html;

/**
 * ±íÇé¹ýÂËÆ÷
 * @author sharoncn
 *
 */
public class ExpressionFilter implements ContentFilter {
	private static ExpressionGetter imageGetter;
	private static ExpressionFilter me;
	
	static{
		me = new ExpressionFilter();
	}
	
	private ExpressionFilter(){}
	
	public static ExpressionFilter getInstance(Context context){
		if(imageGetter == null)imageGetter = ExpressionGetter.getInstance(context);
		return me;
	}
	
	@Override
	public Object doFilter(Object source) {
		//System.out.println(source);
		String text = source.toString();
		text = imageGetter.format(text);
		//System.out.println(text);
		return Html.fromHtml(text, imageGetter, null);
	}

}
