package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.app.Activity;
import android.content.ServiceConnection;
import android.os.Message;

/**
 * 某些功能界面的基类，提供返回方法
 * @author sharoncn
 *
 */
public class BaseActivity extends Activity {
	protected ServiceConnection conn;

	protected void handleMessage(Message msg) {
		switch(msg.what){
		case DataManager.FLAG_TOAST_MSG:
			Util.showMsg(BaseActivity.this, msg.getData()
					.getInt(DataManager.FLAG_MSG));
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		if(conn != null){
			this.unbindService(conn);
		}
		super.onDestroy();
	}

}
