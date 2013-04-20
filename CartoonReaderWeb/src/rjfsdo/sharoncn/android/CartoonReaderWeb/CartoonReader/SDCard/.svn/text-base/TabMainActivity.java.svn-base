package rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.SDCard;

import java.io.File;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Utils.Constants;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.Utils.Utils;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


public class TabMainActivity extends TabActivity {
	private String picPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TabHost tabHost = this.getTabHost();
		LayoutInflater.from(this).inflate(R.layout.layout_tabmain_cartoonreader, tabHost.getTabContentView());
		Intent intent1,intent2;
		intent1 = new Intent(this,SDcardActivity.class);
		intent2 = new Intent(this,SDcardActivity.class);
		
		//获取到MainAcitivity中当前的图片路径信息
		picPath = getPicPath();
				
		Bundle sdcard_bundle,history_bundle;
		sdcard_bundle = new Bundle();
		history_bundle = new Bundle();
		if(picPath != null){
			sdcard_bundle.putString("picPath",picPath);
			intent1.putExtras(sdcard_bundle);
			history_bundle.putString("picPath", picPath);
		}
		//读取历史记录
		String history_path = Utils.getFileRead(Constants.HISTORY);
		if(history_path != null){
			File image_file = new File(history_path);
			if(image_file.exists()){
				if(!image_file.isDirectory()){
					history_path = image_file.getParent();
				}
				history_bundle.putString("history_path", history_path);
			}
			image_file = null;
		}
		intent2.putExtras(history_bundle);
		
		TabSpec spec1 = tabHost.newTabSpec("sd");
		spec1.setIndicator(getString(R.string.sdcard_filesName),this.getResources().getDrawable(R.raw.fileopen));
		spec1.setContent(intent1);
		
		TabSpec spec2 = tabHost.newTabSpec("history");
		spec2.setIndicator(getString(R.string.menu_history),this.getResources().getDrawable(R.raw.history));
		spec2.setContent(intent2);
		
		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		
	}

	private String getPicPath(){
		Bundle bundle = this.getIntent().getExtras();
		if(bundle != null){
			return bundle.getString("picPath");
		}
		return null;
	}
}
