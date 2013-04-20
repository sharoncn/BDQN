package rjfsdo.sharoncn.android.BDQN.LeisureLife.Test;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.FileUtil;
import android.os.Environment;
import android.test.AndroidTestCase;

public class TestUtils extends AndroidTestCase {
	
	public void testCreateFile(){
		String path = Environment.getExternalStorageDirectory().getPath() + "/test2/testnext/log.txt";
		assertTrue(FileUtil.createFile(path));
		
		path = Environment.getExternalStorageDirectory().getPath() + "/test2/testnext/log2.txt";
		assertTrue(FileUtil.createFile(path));
	}
}
