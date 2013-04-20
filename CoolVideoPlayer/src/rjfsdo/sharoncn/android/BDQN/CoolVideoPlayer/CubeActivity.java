package rjfsdo.sharoncn.android.BDQN.CoolVideoPlayer;

import rjfsdo.sharoncn.android.BDQN.CoolVideoPlayer.Render.Render;
import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

public class CubeActivity extends Activity {
	private GLSurfaceView surface;
	private Render render;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        surface = new GLSurfaceView(this);
        render = new Render(this);
        surface.setRenderer(render);
        setContentView(surface);
    }
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			//render.setLight(!render.getLight());
			Intent intent = new Intent(this,VideoListActivity.class);
			this.startActivity(intent);
			this.finish();
		}
		return super.onTouchEvent(event);
	}
}
