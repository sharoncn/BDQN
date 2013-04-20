package rjfsdo.sharoncn.android.BDQN.CoolVideoPlayer;

import cn.com.jbit.coolvideoplayer.R;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends Activity implements SurfaceHolder.Callback{
	private static final String VIDEO_PATH = "/mnt/sdcard/mulanxing.3gp";
	protected static final String TAG = "MainActivity";
	private MediaPlayer _mediaPlayer;
	private SurfaceView _surfaceView;
	private SurfaceHolder _surfaceHolder;
	private String videoPath = null;
	private ImageButton ib_main_play,ib_main_stop,ib_main_openlist;
	private View button_container;
	private boolean isPause = false;//��ǰ�Ƿ�����ͣ״̬
	private boolean isStop = false;
	private int position = 0;
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.layout_main);
		
		handler = new Handler();
		//�õ�����·��
		Intent intent = getIntent();
		videoPath = intent.getStringExtra("path");
		//�õ����п��ư�ť
		button_container = findViewById(R.id.button_container);
		ib_main_play = (ImageButton) findViewById(R.id.ib_main_play);

		ib_main_stop = (ImageButton) findViewById(R.id.ib_main_stop);
		ib_main_openlist = (ImageButton) findViewById(R.id.ib_main_openlist);
		//Ϊ���ſ��ư�ť���Ӽ�����
		ib_main_play.setOnClickListener(playListener);
		ib_main_stop.setOnClickListener(stopListener);
		ib_main_openlist.setOnClickListener(listListener);
		//�õ�SurfaceView
		_surfaceView = (SurfaceView) findViewById(R.id.sv_main_screen);
		_surfaceHolder = _surfaceView.getHolder();
		_surfaceHolder.addCallback(this);
		//����ʹ�÷�ԭ�����ݣ���ʹ�û�����
		_surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	/**
	 * ������״̬(���ź���ͣ)�ı�ʱ����������ı䲥�Ű�ť��״̬
	 */
	private void setPlayButtonState(){
		if(_mediaPlayer != null && _mediaPlayer.isPlaying()){
			ib_main_play.setImageResource(R.raw.btn_mp_pause);
			isPause = false;
		}else{
			ib_main_play.setImageResource(R.raw.btn_mp_play);
			isPause = true;
		}
	}
	
	/**
	 * ������Ƶ
	 */
	private void playVideo(){
		if(_mediaPlayer == null){
			_mediaPlayer = new MediaPlayer();
		}
		
		try{
			if(!isPause){
				Log.v(TAG,videoPath + "    " + _mediaPlayer.getSubtitleStreamName(0));
				_mediaPlayer.reset();
				//���ò���·��
				_mediaPlayer.setDataSource(videoPath == null ? VIDEO_PATH : videoPath);
				//����������������
				_mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				//������ʾ��λ��
				_mediaPlayer.setDisplay(_surfaceHolder);
				//���׼��
				_mediaPlayer.prepare();
				//��ת��ָ��λ��
				_mediaPlayer.seekTo(position);
			}else{
				if(isStop){
					Log.v(TAG,"ִ��stop");
					//���׼��
					_mediaPlayer.prepare();
					//��ת��ָ��λ��
					_mediaPlayer.seekTo(position);
					isStop = false;
				}
			}
			//��ʼ
			_mediaPlayer.start();
			//�л�������ͣ״̬
			setPlayButtonState();
			position = 0;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * ������ͣ����
	 */
	private void pause(){
		_mediaPlayer.pause();
		setPlayButtonState();
	}
	
	//�ӳ����ؿ��ư�ť������
	private Runnable task = new Runnable(){
		public void run() {
			hideButton();
		}
	};
	
	/**
	 * ��ʾ����ֹͣ�ȿ���ͼ��
	 */
	private void showButton(){
		button_container.setVisibility(View.VISIBLE);
		handler.removeCallbacks(task);
		handler.postDelayed(task, 3000);
	}
	
	/**
	 * ���ز���ֹͣ�ȿ���ͼ��
	 */
	private void hideButton(){
		button_container.setVisibility(View.GONE);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		showButton();
		return super.onTouchEvent(event);
	}

	@Override
	protected void onPause() {
		if(_mediaPlayer != null && _mediaPlayer.isPlaying()){
			_mediaPlayer.stop();
		}
		super.onPause();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		//���沥��λ��֮��Ķ�����
	}

	public void surfaceCreated(SurfaceHolder holder) {
		playVideo();
		showButton();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		_mediaPlayer = null;
		_surfaceHolder = null;
		_surfaceView = null;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("position", _mediaPlayer.getCurrentPosition());
		super.onSaveInstanceState(outState);
	}

	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if(savedInstanceState.containsKey("position")){
			position = savedInstanceState.getInt("position");
		}else{
			position = 0;
		}
		super.onRestoreInstanceState(savedInstanceState);
	}

	//���Ű�ť����������ͣ���ܵĴ���Ҳ��һ��
	private OnClickListener playListener = new OnClickListener() {
		
		public void onClick(View v) {

			if(_mediaPlayer != null){
				if(!_mediaPlayer.isPlaying()){
					playVideo();
				}else{
					pause();
				}
			}
		}
	};
	
	private OnClickListener stopListener = new OnClickListener() {
		
		public void onClick(View v) {
			if(_mediaPlayer != null){
				_mediaPlayer.stop();
				setPlayButtonState();
				//isPause = false;
				isStop = true;
			}
		}
	};
	
	//������Ƶ�б��ļ�����
	private OnClickListener listListener = new OnClickListener() {
		
		public void onClick(View v) {
			if(_mediaPlayer != null){
				_mediaPlayer.stop();
				_mediaPlayer = null;
			}
			Intent intent = new Intent(MainActivity.this,VideoListActivity.class);
			startActivity(intent);
			finish();
		}
	};
}