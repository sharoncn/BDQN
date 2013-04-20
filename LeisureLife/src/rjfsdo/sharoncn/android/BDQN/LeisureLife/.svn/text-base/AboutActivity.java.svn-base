package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.BaseHeader;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;


/**
 * 关于界面
 * @author sharoncn
 *
 */
public class AboutActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_about);
		BaseHeader header = (BaseHeader) findViewById(R.id.about_header);
		header.setTitle(getString(R.string.about));
		
		TextView tv_ver = (TextView) findViewById(R.id.version_name);
		
		PackageManager pm = this.getApplicationContext().getPackageManager();
		try{
			PackageInfo ai = pm.getPackageInfo(this.getPackageName(),
					PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
			tv_ver.setText(ai.versionName);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
