package rjfsdo.sharoncn.android.BDQN.LeisureLife.Test;

import android.test.AndroidTestCase;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.LifePreferences;

public class TestPreferences extends AndroidTestCase{
	
	/**
	 * 测试LifePreferences所有功能
	 * @throws Exception
	 */
	public void testAllFunction() throws Exception{
		LifePreferences p = LifePreferences.getPreferences(getContext());
		p.saveName("张三");
		p.savePW("123456");
		p.setAutoLogin(true);
		p.setRemberPW(true);
		p.setUID("12345789");
		
		assertEquals(p.getName(), "张三");
		assertEquals(p.getPW(), "123456");
		assertEquals(p.getUID(), "12345789");
		assertEquals(p.getAutoLogin(), true);
		assertEquals(p.getRemberPW(), true);
		
		p.saveName("李四");
		p.savePW("321");
		p.setAutoLogin(false);
		p.setRemberPW(false);
		p.setUID("00000");
		
		assertEquals(p.getName(), "李四");
		assertEquals(p.getPW(), "321");
		assertEquals(p.getUID(), "00000");
		assertEquals(p.getAutoLogin(), false);
		assertEquals(p.getRemberPW(), false);
		
		p.clear();
	}
}
