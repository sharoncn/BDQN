package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class WeiboPreference {
	public static final String KEY_ACCESSTOKEN = "accessToken";
	public static final String KEY_REFRESHTOKEN = "refreshToken";
	public static final String KEY_EXPIRES = "expires";
	public static final String KEY_ISPLAYSOUND = "isPlaySound";
	private static WeiboPreference me;
	private static SharedPreferences sp;
	private static Editor editor;
	static{
		me = new WeiboPreference();
	}
	
	private WeiboPreference(){}
	
	public static WeiboPreference getInstance(Context context){
		if(sp == null){
			sp = PreferenceManager.getDefaultSharedPreferences(context);
		}
		if(editor == null){
			editor = sp.edit();
		}
		return me;
	}
	
	/**
	 * 保存AccessToken
	 * @param token 要保存的AccessToken
	 * @return 成功返回true，失败返回false
	 */
	public boolean saveAccessToken(String token){
		return saveString(KEY_ACCESSTOKEN, token);
	}
	
	/**
	 * @return String 成功获取返回AccessToken，否则null
	 */
	public String getAccessToken(){
		return getString(KEY_ACCESSTOKEN);
	}
	
	/**
	 * 保存RefreshToken
	 * @param token 要保存的RefreshToken
	 * @return 成功返回true，失败返回false
	 */
	public boolean saveRefreshToken(String token){
		return saveString(KEY_REFRESHTOKEN, token);
	}
	
	/**
	 * @return String 成功获取返回RefreshToken，否则null
	 */
	public String getRefreshToken(){
		return getString(KEY_REFRESHTOKEN);
	}
	
	/**
	 * 保存过期时间
	 * @param expires 过期时间
	 * @return 成功返回true，失败返回false
	 */
	public boolean saveExpiresTime(String expires){
		return saveString(KEY_EXPIRES, expires);
	}
	
	/**
	 * @return String 成功获取返回过期时间，否则null
	 */
	public String getExpiresTime(){
		return getString(KEY_EXPIRES);
	}
	
	/**
	 * 保存是否播放音效
	 * @param isPlaySound 是否播放音效
	 * @return 成功返回true，失败返回false
	 */
	public boolean saveIsPlaySound(boolean isPlaySound){
		return saveBoolean(KEY_ISPLAYSOUND, isPlaySound);
	}
	
	/**
	 * @return boolean 是否播放音效
	 */
	public boolean getIsPlaySound(){
		return getBoolean(KEY_ISPLAYSOUND);
	}
	
	
	private boolean saveString(String key, String value){
		editor.putString(key, value);
		return editor.commit();
	}
	
	private String getString(String key){
		if(sp.contains(key)){
			return sp.getString(key, null);
		}
		return null;
	}
	
	private boolean saveBoolean(String key, boolean value){
		editor.putBoolean(key, value);
		return editor.commit();
	}
	
	private boolean getBoolean(String key){
		if(sp.contains(key)){
			return sp.getBoolean(key, false);
		}
		return false;
	}
}
