package rjfsdo.sharoncn.android.BDQN.ImageViewer.Utils;

import java.io.File;
import java.io.FileFilter;

/**
 * ÎÄ¼þ¹ýÂËÆ÷
 * @author sharoncn
 *
 */
public class DirectoryFilter implements FileFilter {
	private static DirectoryFilter me;
	static{
		me = new DirectoryFilter();
	}
	
	private DirectoryFilter(){};
	
	public static DirectoryFilter getInstance(){
		return me;
	}
	
	public boolean accept(File file) {
		return file.isDirectory();
	}

}
