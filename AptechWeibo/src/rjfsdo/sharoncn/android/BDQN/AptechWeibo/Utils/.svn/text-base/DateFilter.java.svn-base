package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期过滤器
 * @author sharoncn
 *
 */
public class DateFilter implements ContentFilter {
	private static final long ONEDAY = 86400000;
	private static final long ANHOUR = 3600000;
	private static final long ONEMINUTE = 60000;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public Object doFilter(Object source) {
		Date date = new Date(source.toString());
		Date now = new Date();
		long sub = Math.abs(now.getTime() - date.getTime());
		if(sub > ONEDAY){
			return sdf.format(date);
		}else if(sub > ANHOUR){
			return ((int)sub / ANHOUR) + "小时之前";
		}else if(sub > ONEMINUTE){
			return ((int)sub / ONEMINUTE) + "分钟之前";
		}else{
			return "刚刚";
		}
	}

}
