package rjfsdo.sharoncn.android.BDQN.CoolVideoPlayer;

import cn.com.jbit.coolvideoplayer.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class WelcomeActivity extends Activity implements AnimationListener {
	private Handler handler;
	private ImageView ivWelcome;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //设置无标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置无状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置布局文件
        setContentView(R.layout.layout_welcome);
        
        ivWelcome = (ImageView) findViewById(R.id.iv_welcome);
        Animation fadeinAnim = AnimationUtils.loadAnimation(this, R.anim.anim_fadein);
        fadeinAnim.setAnimationListener(this);
        ivWelcome.setAnimation(fadeinAnim);
        handler = new Handler();
    }
    
    private Runnable r = new Runnable(){

		public void run() {
			Intent intent = new Intent(WelcomeActivity.this,CubeActivity.class);
			intent.setAction(ACTIVITY_SERVICE);
			startActivity(intent);
			finish();
		}
    };

	public void onAnimationEnd(Animation animation) {
		Animation fadeoutAnim = AnimationUtils.loadAnimation(this, R.anim.anim_fadeout);
		fadeoutAnim.setFillAfter(true);
		fadeoutAnim.setAnimationListener(new AnimationListener() {
			
			public void onAnimationStart(Animation animation) {
				handler.postDelayed(r, 2000);
			}
			
			public void onAnimationRepeat(Animation animation) {}
			public void onAnimationEnd(Animation animation) {}
		});
		ivWelcome.setAnimation(fadeoutAnim);
	}

	public void onAnimationRepeat(Animation animation) {
		
	}

	public void onAnimationStart(Animation animation) {
		
	}
}