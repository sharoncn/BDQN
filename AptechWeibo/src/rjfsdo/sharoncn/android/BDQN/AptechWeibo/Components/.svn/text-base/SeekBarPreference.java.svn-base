package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 系统中本身有VolumnPreference提供了声音设置的功能，但是是以Dialog的形式进行展示的.
 * 我期望的是直接展示在PreferenceActivity中，所以自定义了SeekBarPreference，并自定了设计
 * 属性isPlaySample。自定设计属性 是不错的选择。自定义控件使用自定义设计属性，可以解决原来很多的问题。
 * 
 * @author sharoncn
 * 
 */
public class SeekBarPreference extends Preference implements OnSeekBarChangeListener {
	private SeekBar mSeekBar;
	private static SharedPreferences sp;
	private static Editor editor;
	private String key;
	private PreferenceActivity parent;
	private static AudioManager mAudioManager;
	private static Ringtone mRingtone;
	private Context mContext;
	private static boolean isPlaySample = true;
	private static final int streamType = AudioManager.STREAM_MUSIC;
	protected static final String TAG = "SeekBarPreference";

	public SeekBarPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.setLayoutResource(R.layout.seekbar_preference);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.current);
		int indexCount = a.getIndexCount();
		for (int i = 0; i < indexCount; i++) {
			int index = a.getIndex(i);
			switch (index) {
			case R.styleable.current_isPlaySample:
				isPlaySample = a.getBoolean(index, true);
				break;
			}
		}
		a.recycle();

		key = this.getKey();
		parent = (PreferenceActivity) this.getContext();
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		mSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
		mSeekBar.setOnSeekBarChangeListener(this);
		if (sp == null)
			sp = this.getSharedPreferences();
		if (mAudioManager == null)
			mAudioManager = (AudioManager) mContext.getSystemService(Service.AUDIO_SERVICE);
		mSeekBar.setMax(mAudioManager.getStreamMaxVolume(streamType));
		mSeekBar.setProgress(mAudioManager.getStreamVolume(streamType));

		Uri defaultUri = Settings.System.DEFAULT_NOTIFICATION_URI;

		if (mRingtone == null) {
			mRingtone = RingtoneManager.getRingtone(mContext, defaultUri);
		}
		if (mRingtone != null) {
			mRingtone.setStreamType(streamType);
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		callChangeListener(progress);
		if (sp == null)
			sp = this.getSharedPreferences();
		if (editor == null) {
			editor = sp.edit();
		}

		editor.putInt(key, progress);
		editor.commit();

		if (parent != null)
			parent.onPreferenceTreeClick(null, this);
		else
			System.out.println("parent is null");
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		int progress = seekBar.getProgress();
		mAudioManager.setStreamVolume(streamType, progress, 0);
		if (isPlaySample) {
			int notificationVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
			mAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, progress, 0);
			Log.v(TAG, "setStreamVolume:" + progress);
			playSample();//播放示例声音
			mAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, notificationVolume, 0);// 还原现场
		}
	}

	private void playSample() {
		Log.v(TAG, "playSample");
		if (mRingtone != null && !mRingtone.isPlaying()) {
			mRingtone.play();
		}
	}

}
