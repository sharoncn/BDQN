package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

/**
 * 有左右两个ImageButton的Header
 * 
 * @author sharoncn
 * 
 */
public class HasButtonHeader extends DefaultHeader {
	protected View btnLeft, btnRight;
	/**
	 * 左边按钮ID
	 */
	public static final int ID_LEFTBUTTON = R.id.btn_left;
	/**
	 * 右边按钮ID
	 */
	public static final int ID_RIGHTBUTTON = R.id.btn_right;
	protected static int layoutRes = R.layout.header_imagebtn;
	private boolean isRotating = false;
	private RotatingImageView rotatingView;
	private View currentView;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			//Log.v("ImageButtonHeader", "Rotate  " + msg.what);
			rotatingView.setDegress(msg.what);
			super.handleMessage(msg);
		}
	};

	public HasButtonHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void initInnerViews() {
		//这里类似于将Header的内部分层，上层覆盖下层，ImageButtonHeader覆盖了DefaultHeader的层，
		//处于DefaultHeader层的上边。这个方式也存在一些问题，比如焦点获取。需要在基类中写焦点分配
		//的代码。不过这个应用最多只有两层，并不需要。算是个思路。以后再说。
		super.initInnerViews();
		final View view = inflateLayoutRes();
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.CENTER_VERTICAL;
		view.setLayoutParams(params);

		btnLeft = view.findViewById(R.id.btn_left);
		btnRight = view.findViewById(R.id.btn_right);
		rotatingView = (RotatingImageView) view.findViewById(R.id.img);

		this.addView(view);
	}

	protected View inflateLayoutRes(){
		return inflater.inflate(layoutRes, null);
	}
	
	/**
	 * 为左边按钮设置背景
	 * 
	 * @param resId
	 *            背景资源id
	 */
	public void setLeftButtonBackgroundRes(int resId) {
		btnLeft.setBackgroundResource(resId);
	}

	/**
	 * 为右边按钮设置背景
	 * 
	 * @param resId
	 *            背景资源id
	 */
	public void setRightButtonBackgroundRes(int resId) {
		btnRight.setBackgroundResource(resId);
	}

	/**
	 * 为右边按钮设置背景
	 * 
	 * @param resId
	 *            背景资源id
	 */
	public void setRotatingBackgroundRes(int resId) {
		rotatingView.setImageResource(resId);
	}
	
	/**
	 * 为Header中的按钮设置监听器,当判断是哪个按钮被点击时, 可以使用ID_LEFTBUTTON(
	 * {@link HasButtonHeader#ID_LEFTBUTTON}) 和ID_RIGHTBUTTON(
	 * {@link HasButtonHeader#ID_RIGHTBUTTON})。
	 * 
	 * @param l
	 *            监听器
	 */
	public void setButtonOnClickListener(OnClickListener l) {
		if (l != null) {
			btnLeft.setOnClickListener(l);
			btnRight.setOnClickListener(l);
		}
	}

	/**
	 * 旋转Button
	 * @param id      要旋转的按钮id
	 * @param degress 每100毫秒旋转多少角度
	 */
	public void startButtonRotate(int id, int degress) {
		if (!isRotating) {
			if (id == ID_LEFTBUTTON) {
				currentView = btnLeft;
			} else {
				currentView = btnRight;
			}
			currentView.setVisibility(View.GONE);
			if(rotatingView != null){
				rotatingView.setLayoutParams(currentView.getLayoutParams());
				rotatingView.setVisibility(View.VISIBLE);
			}
			isRotating = true;
			new RotateThread(degress).start();
		}
	}

	/**
	 * 停止旋转
	 */
	public void stopButtonRotate() {
		isRotating = false;
		if(currentView != null)currentView.setVisibility(View.VISIBLE);
		if(rotatingView != null)rotatingView.setVisibility(View.GONE);
	}

	class RotateThread extends Thread {
		private int degress = 0;
		private int current = 0;
		
		public RotateThread(int degress){
			this.degress = degress;
		}
		
		@Override
		public void run() {
			try {
				while (isRotating) {
					Thread.sleep(100);
					current += degress;
					int mod = current % 360;
					if(mod == 0){
						current = 0;
					}
					handler.sendEmptyMessage(mod);
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
