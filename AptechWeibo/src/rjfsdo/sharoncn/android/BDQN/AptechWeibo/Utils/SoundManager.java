package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * ��Ч������
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
	 * ���󲥷�����
	 * @param resId  ������ԴId
	 * @param isLoop  �Ƿ�ѭ��
	 * @return ����ܹ���ȷ������Ч����true,���򷵻�false(�����޷��ҵ������ļ��Ͳ���ʧ��)
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
	 * ����������Դ
	 * @param context 
	 * @param resId ������ԴID
	 * @return  ����ID
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
	 * ������ԴID�������ID
	 * @param resId ��ԴID
	 * @return ����ID,���ʧ�ܷ���-1
	 */
	public int getSoundId(int resId){
		if(sounds.containsKey(resId)){
			return sounds.get(resId);
		}
		return -1;
	}
	
}
