package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils;

import android.view.View;

/**
 * ʵ����Setterʱ��ҪΪ�����û�ȡitemĳ��ֵ�ķ������� ����������������κβ�����
 * ֮ǰ������һ��Adapter��Ӧ������������������ơ����Ƿ��ִ������ȡ���ݣ�Ȼ����ɶ���
 * Ȼ�����÷���ΪView�������ݣ��������̫���ˡ�
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
