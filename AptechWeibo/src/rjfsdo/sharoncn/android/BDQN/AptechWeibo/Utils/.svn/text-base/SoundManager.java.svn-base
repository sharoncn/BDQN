package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * 音效管理器
 * @author sharoncn
 *
 */
public class SoundManager {
	private static SoundPool soundPool;
	private static SoundManager me;
	private static Map<Integer,Integer> sounds = new HashMap<Integer,Integer>();
	
	static {
		me = new SoundManager();
	}

	private SoundManager() {
		soundPool = new SoundPool(Constants.SOUNDPOOL_MAX, AudioManager.STREAM_MUSIC, 0);
	}

	public static SoundManager getInstance() {
		return me;
	}

	/**
	 * 请求播放声音
	 * @param resId  声音资源Id
	 * @param isLoop  是否循环
	 * @return 如果能够正确播放音效返回true,否则返回false(包括无法找到声音文件和播放失败)
	 */
	public boolean applyPlaySound(int resId,boolean isLoop){
		int loop = 0;
		int soundId = -1;
		
		if(isLoop){
			loop = -1;
		}
		
		if(sounds.containsKey(resId)){
			soundId = sounds.get(resId);
		}
		
		if(soundId == -1){
			return false;
		}
		
		final int result = soundPool.play(soundId, 0.05f, 0.05f, 0, loop, 1.0f);
		if(result == 0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 载入声音资源
	 * @param context 
	 * @param resId 声音资源ID
	 * @return  声音ID
	 */
	public int addSound(Context context,int resId){
		if(sounds.containsKey(resId)){
			return sounds.get(resId);
		}else{
			int soundId = soundPool.load(context, resId, 1);
			sounds.put(resId, soundId);
			return soundId;
		}
	}

	/**
	 * 根据资源ID获得声音ID
	 * @param resId 资源ID
	 * @return 声音ID,如果失败返回-1
	 */
	public int getSoundId(int resId){
		if(sounds.containsKey(resId)){
			return sounds.get(resId);
		}
		return -1;
	}
	
}
