package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.FlashManager;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

public class FlashActivity extends Activity {
	private float brightness = 0.0f;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_flash);
		setLightBrightest();
	}

	//������Ļ����
	private void setLightBrightest() {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		brightness = lp.screenBrightness;
		lp.screenBrightness = 1.0f;
		getWindow().setAttributes(lp);
	}
	
	//��ԭ��Ļ����
	private void restoreLightBrightness(){
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.screenBrightness = brightness;
		getWindow().setAttributes(lp);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//�����ؼ������£�������Widget���͹㲥��ˢ��Widget����ʾ״̬
			Intent intent = new Intent(FlashWidget.ACTION_RESPONSE);
			this.sendBroadcast(intent);
			restoreLightBrightness();
		}
		return super.onKeyDown(keyCode, event);
	}
}
