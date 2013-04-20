package rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Utils.Constants;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Utils.Utils;

import android.app.Activity;
import android.widget.SimpleAdapter;

public class SimpleAdapterFactory {
	private Activity activity;
	private String path;
	
	public SimpleAdapterFactory(Activity activity, String path) {
		this.activity = activity;
		this.path = path;
	}

	public SimpleAdapter getSimpleAdapter(int resource,String[] from,int[] to){
		List<Map<String,Object>> files = buildListForSimpleAdapter(path);
		
		return new SimpleAdapter(activity,files,resource,from,to);
	}

	//排序
	private List<Map<String, Object>> buildListForSimpleAdapter(String path) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		File[] files = Utils.getPicOrder(path);
		File f = new File(path);
		if(f.getParentFile().exists()){
			Map<String,Object> root = new HashMap<String,Object>();
			root.put("name", Constants.RETURNPARENTDIRECTORY );
			root.put("img", R.raw.lastdir);
			root.put("path", f.getParent());
			list.add(root);
		}
		
		if(files != null && files.length > 0){
			for(File file:files){
				/*
				 * 如果：当前文件是根目录
				 * fileParent名称设置为：@sdcard_is_root_dir
				 * fileParent设置属性为不可用
				 * 否则：
				 * fileParent名称设置为：@sdcard_parentDirector
				 * fileParent设置属性为可用
				 * 判断结束
				 * 实在不明白这fileParent是什么东西
				 */
				Map<String,Object> folder = new HashMap<String,Object>();
				if(file.isDirectory()){
					folder.put("img", R.raw.folder);
					folder.put("name", file.getName());
					folder.put("path", file.getPath());
					list.add(folder);
				}else if(Utils.isPic(file.getPath())){
					folder.put("img", R.drawable.pic);
					folder.put("name", file.getName());
					folder.put("path", file.getPath());
					list.add(folder);
				}
			}
		}
		
		return list;
	}
}