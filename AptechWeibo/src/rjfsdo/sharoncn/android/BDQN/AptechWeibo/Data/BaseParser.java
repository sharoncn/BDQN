package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class BaseParser implements JsonParser {
	
	protected static ArrayList<String> initMethod(Class<?> clazz) {
		System.out.println("初始化类成员变量名");
		Field[] mfields = clazz.getDeclaredFields();
		final int count = mfields.length;
		ArrayList<String> fields = new ArrayList<String>(count);
		for (int i = 0; i < count; i++) {
			String fieldName = mfields[i].getName();
			fields.add(fieldName);
			System.out.println("成员变量：" + fieldName);
		}
		return fields;
	}

}
