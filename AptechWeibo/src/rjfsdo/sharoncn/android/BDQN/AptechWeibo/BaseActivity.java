package rjfsdo.sharoncn.android.BDQN.AptechWeibo;

import android.app.Activity;
import android.view.KeyEvent;

public class BaseActivity extends Activity {

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
