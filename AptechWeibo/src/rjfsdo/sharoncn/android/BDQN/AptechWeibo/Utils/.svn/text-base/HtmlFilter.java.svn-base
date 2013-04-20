package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils;

import android.text.Html;
import android.text.Spanned;

/**
 * 其实可以在这个具体实现上抽出一层叫SpannedFilter
 * @author sharoncn
 *
 */
public class HtmlFilter implements ContentFilter {

	@Override
	public Object doFilter(Object source) {
		Spanned s =Html.fromHtml("来自:" + source.toString());
		return s;
	}

}
