package rjfsdo.sharoncn.android.BDQN.LeisureLife.Data;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Constants;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LifePreferences {
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_UID = "uid";
	private static final String KEY_ISREMBERPW = "isRemberPW";
	private static final String KEY_ISAUTOLOGIN = "isAutoLogin";
	private static Context _context;
	private static SharedPreferences sp;
	private static Editor editor;
	private static LifePreferences me;

	static {
		me = new LifePreferences();
	}

	private LifePreferences() {
	}

	public static LifePreferences getPreferences(Context context) {
		_context = context;
		init();
		return me;
	}

	private static void init() {
		if (sp == null) {
			sp = _context.getSharedPreferences(Constants.PREFERENCES_NAME,
					Context.MODE_PRIVATE);
		}
		if (editor == null) {
			editor = sp.edit();
		}
	}

	/**
	 * �����û���
	 * 
	 * @param username
	 * @throws IllegalArgumentException
	 */
	public void saveName(String username) throws IllegalArgumentException {
		if (username != null && !username.equals("")) {
			editor.putString(KEY_USERNAME, username);
			editor.commit();
		} else {
			throw new IllegalArgumentException("username Is Illegal!");
		}
	}

	/**
	 * ��ȡ�û���
	 * 
	 * @return
	 */
	public String getName() {
		return sp.getString(KEY_USERNAME, "");
	}

	/**
	 * ��������
	 * 
	 * @param password
	 * @throws IllegalArgumentException
	 */
	public void savePW(String password) throws IllegalArgumentException {
		if (password != null && !password.equals("")) {
			editor.putString(KEY_PASSWORD, password);
			editor.commit();
		} else {
			throw new IllegalArgumentException("password Is Illegal!");
		}
	}

	/**
	 * ��ȡ����
	 * 
	 * @return
	 */
	public String getPW() {
		return sp.getString(KEY_PASSWORD, "");
	}

	/**
	 * ����UID
	 * 
	 * @param uid
	 */
	public void setUID(String uid) {
		if (uid != null && !uid.equals("")) {
			editor.putString(KEY_UID, uid);
			editor.commit();
		} else {
			throw new IllegalArgumentException("uid Is Illegal!");
		}
	}

	/**
	 * ��ȡUID
	 * 
	 * @return
	 */
	public String getUID() {
		return sp.getString(KEY_UID, "");
	}

	/**
	 * �����Ƿ��Զ���¼
	 * 
	 * @param isAutoLogin
	 */
	public void setAutoLogin(boolean isAutoLogin) {
		editor.putBoolean(KEY_ISAUTOLOGIN, isAutoLogin);
		editor.commit();
	}

	/**
	 * ��ȡ�Ƿ��Զ���¼
	 * 
	 * @return
	 */
	public boolean getAutoLogin() {
		return sp.getBoolean(KEY_ISAUTOLOGIN, false);
	}

	/**
	 * �����Ƿ��ס����
	 * 
	 * @param isRemberPW
	 */
	public void setRemberPW(boolean isRemberPW) {
		editor.putBoolean(KEY_ISREMBERPW, isRemberPW);
		editor.commit();
	}

	/**
	 * �õ��Ƿ��ס����
	 * 
	 * @return
	 */
	public boolean getRemberPW() {
		return sp.getBoolean(KEY_ISREMBERPW, true);
	}

	/**
	 * ������������
	 */
	public void clear() {
		editor.clear();
		editor.commit();
	}
}
