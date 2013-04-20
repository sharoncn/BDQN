package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils;

import java.util.Comparator;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.HasTime;

/**
 * 微博发布时间的Comparator
 * @author sharoncn
 *
 */
public class TimeComparator implements Comparator<HasTime> {

	@Override
	public int compare(HasTime o1, HasTime o2) {
		if(o1 instanceof HasTime && o2 instanceof HasTime){
			return (int) (o2.getTime() - o1.getTime());
		}
		return 0;
	}
	
}
