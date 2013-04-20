package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils;

import android.view.View;

/**
 * 实例化Setter时需要为它设置获取item某个值的方法名， 并且这个方法名无任何参数。
 * 之前是想用一个Adapter适应所有需求，所以这样设计。但是发现从网络获取数据，然后反射成对象，
 * 然后再用反射为View设置内容，这个过程太慢了。
 * @author sharoncn
 * @deprecated
 */
@Deprecated
public interface Setter {
	void set(View v, Object item, int position);

	void setMethodName(String methodName);

	String getMethodName();

	Setter setFilter(ContentFilter f);
}
