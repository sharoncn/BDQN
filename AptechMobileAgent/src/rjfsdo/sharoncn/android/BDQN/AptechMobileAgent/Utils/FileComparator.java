package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils;

import java.io.File;
import java.text.Collator;
import java.util.Comparator;

public class FileComparator<T> implements Comparator<T>{
	private static Collator cp = Collator.getInstance(java.util.Locale.CHINA);
	@Override
	public int compare(T o1, T o2) {
		if(o1 instanceof File){
			return compareFile((File)o1,(File)o2);
		}
	
		if(o1 instanceof Integer){
			return compareInteger((Integer)o1,(Integer)o2);
		}
		
		return cp.compare(o1,o2);
	}
	
	private int compareFile(File o1,File o2){
		return cp.compare(o1.getName(), o2.getName());
	}
	
	private int compareInteger(Integer i1,Integer i2){
		return cp.compare("" + i1, "" + i2);
	}
	
}
