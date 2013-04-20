package rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.SDCard;

import java.io.File;
import java.util.Map;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.BaseActivity;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.MainActivity;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Adapter.SimpleAdapterFactory;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Listener.GobackListener;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Utils.Constants;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SDcardActivity extends BaseActivity {
	private static final String TAG = "SDcardActivity";
	private Button sdcard,fileParent,browse;
	private ListView fileslist;
	private String fileParentPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.layout_sdcard_directory_list);
		allActivity.add(this);
		
		GobackListener goback = new GobackListener(this);
		//获取各个组件
		sdcard = (Button) findViewById(R.id.sdcard);
		fileParent = (Button) findViewById(R.id.fileParent);
		browse = (Button) findViewById(R.id.browse);
		
		sdcard.setOnClickListener(sdcardButton);
		fileParent.setOnClickListener(parentDirectoryButton);
		browse.setOnClickListener(goback);
		
		fileslist = (ListView) findViewById(R.id.filesList);
		
		//打开sdcard目录
		//toSDCard();
		//将filesList和adapter进行绑定
		refreshListItems(getShowPath());
	}
	
	/**
	 * 将filesList和adapter进行绑定
	 * @param showPath
	 */
	private void refreshListItems(String path) {
		Log.v(TAG,path);
		fileParentPath = path;
		SimpleAdapterFactory saf = new SimpleAdapterFactory(this,path);
		SimpleAdapter spAdapter = saf.getSimpleAdapter(R.layout.layout_sdcard_directory, 
				new String[]{"img","name","path"}, new int[]{R.id.img,R.id.name,R.id.path});
		fileslist.setAdapter(spAdapter);
		fileslist.setOnItemClickListener(openChildFolder);
	}

	private String getShowPath() {
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		String history_path = null;
		if(bundle != null){
			history_path = bundle.getString("history_path");
		}
		
		return history_path == null?Utils.getSdCardPath():history_path;
	}

	private void toSDCard() {
		//获取sdcard卡的绝对路径
		String sdCard_path = Utils.getSdCardPath();
		if(sdCard_path != null){
			File sdCard_dir = new File(sdCard_path);
			//获得sdcard卡目录的上一级目录
			fileParentPath = sdCard_dir.getParent();
			//打开sdcard目录
			goToChild(sdCard_path);
		}else{
			Utils.showMsg(SDcardActivity.this,R.string.sdcard_no_sdcard);
		}
	}

	private void goToChild(String path) {
		refreshListItems(path);
	}
	
	private void goToParent(String path) {
		//我在M9上测试 无法返回"/"目录，固加此限制
		if(path.equals(Utils.getSdCardPath())){
			Utils.showMsg(this, R.string.sdcard_root_dir);
			return;
		}
		File f = new File(path);
		if(f.exists() && f.isDirectory()){
			refreshListItems(f.getParent());
		}
	}
	
	public OnClickListener sdcardButton = new OnClickListener() {
		@Override
		public void onClick(View v) {
			toSDCard();
		}
	};
	
	public OnClickListener parentDirectoryButton = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(fileParentPath != null && !fileParentPath.equals("/")){
				Log.v(TAG,"返回的上级目录是:" + fileParentPath);
				goToParent(fileParentPath);
			}else{
				Utils.showMsg(SDcardActivity.this,R.string.sdcard_root_dir);
			}
		}
	};
	
	public OnItemClickListener openChildFolder = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String,Object>) parent.getItemAtPosition(position);
			String path = (String)map.get("path");
			File file = new File(path);
			if(Utils.isPic(file.getPath())){
				//获得某一张图片的绝对路径
				getPicPath(file.getPath());
			}else if(file.isDirectory()){
				goToChild(path);
			}else{
				goToParent(path);
			}
		}
	};
	
	private void getPicPath(String picPath){
		if(picPath != null && picPath.length() > 0){
			File file = new File(picPath);
			String fileParentPath = file.getParent();
			File[] files = (new File(fileParentPath)).listFiles();
			if(files != null && files.length > 0){
				savePicPath(picPath);
			}
			Intent intent = new Intent(this,MainActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("picPath", picPath);
			intent.putExtras(bundle);
			Log.v(TAG,"传递到MainActivity的picPath:" + picPath);
			this.startActivity(intent);
			this.finish();
		}
		
	}
	
	public void savePicPath(String picsPath){
		try{
			Utils.saveFile(Constants.HISTORY,picsPath,false);
		}catch(Exception e){
			Log.i(TAG,"getFilePathHistory Exception:" + e.getMessage());
		}
	}
}
