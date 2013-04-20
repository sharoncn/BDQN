package rjfsdo.sharoncn.android.BDQN.AptechWeibo;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboPreference;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Constants;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

/**
 * 设置界面
 * @author sharoncn
 *
 */
public class SetupAct extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.layout.layout_act_setup);
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
		System.out.println("onPreferenceTreeClick");
		//当设置的Key是WeiboPreference.KEY_ISPLAYSOUND,如果需要让音效播放立即生效
		//则需要改变Constance.ISPLAYSOUND的值
		if(preference.hasKey()){
			final String key = preference.getKey();
			if(key.equals(WeiboPreference.KEY_ISPLAYSOUND)){
				Constants.ISPLAYSOUND = preference.getSharedPreferences().getBoolean(key, false);
			}
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

}
