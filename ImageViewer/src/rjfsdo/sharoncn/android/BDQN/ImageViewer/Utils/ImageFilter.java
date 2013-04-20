package rjfsdo.sharoncn.android.BDQN.ImageViewer.Utils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 *  Í¼Æ¬¹ýÂËÆ÷
 * @author sharoncn
 *
 */
public class ImageFilter implements FileFilter {
	private static List<String> exts = new ArrayList<String>();
	private static ImageFilter me = new ImageFilter();
	
	private ImageFilter(){
		exts.add("jpg");
		exts.add("png");
		exts.add("bmp");
		exts.add("gif");
		exts.add("jpeg");
		exts.add("ai");
	}
	
	public static ImageFilter getInstance(){
		return me;
	}
	
	public boolean accept(File file) {
		if(file.isFile()){
			String extName = Util.getFileExtName(file);
			if(exts.contains(extName.toLowerCase())){
				return true;
			}
		}
		return false;
	}
}
