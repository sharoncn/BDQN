package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

public class ScrollableTextView extends TextView {
//	private String text;
//	private float textLength;
//	private int color;
//	private int viewWidth;
//	private float x = 0,y = 0;
//	private Context context;
//	private Paint paint;
//	private boolean autoScroll = false;
//	
//	public ScrollableTextView(Context context) {
//		this(context,null);
//	}
//
//	public ScrollableTextView(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		this.context = context;
//	}
//
//	public ScrollableTextView(Context context, AttributeSet attrs) {
//		this(context, attrs, 0);
//	}
//
//	@Override
//	protected void onDraw(Canvas canvas) {
//		//Log.w("ScrollableTextView","onDraw");
//		if(autoScroll){
//			paint.setColor(color);
//			x -= 0.4;
//			if(x < -textLength){
//				x = viewWidth + textLength;
//			}
//			canvas.drawText(text, x, y, paint);
//			this.invalidate();
//			Log.w("ScrollableTextView","x:" + x);
//			Log.w("ScrollableTextView","TextLength:" + textLength);
//		}
//		super.onDraw(canvas);
//	}
//
//	@Override
//	public void setText(CharSequence text, BufferType type) {
//		this.text = text.toString();
//		init();
//		super.setText(text, type);
//	}
//	
//	@Override
//	public void setTextColor(int color) {
//		this.color = color;
//		init();
//		super.setTextColor(color);
//	}
//
//	private void init(){
//		this.text = getText().toString();
//		this.color = super.getTextColors().getDefaultColor();
//		paint = this.getPaint();
//	}
//	
//	private void prepareDraw(){
//		paint = this.getPaint();
//		textLength = paint.measureText(text);
//		viewWidth = getWidth();
//		if(viewWidth == 0){
//			WindowManager mwm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
//			if(mwm != null){
//				viewWidth = mwm.getDefaultDisplay().getWidth();
//			}
//		}
//		x = 0;
//		y = this.getTextSize()+ this.getPaddingTop();
//	}
//	
//	public boolean isAutoScroll() {
//		return autoScroll;
//	}
//
//	public void setAutoScroll(boolean autoScroll) {
//		this.autoScroll = autoScroll;
//	}
//	
//	public void startScroll(){
//		Log.w("ScrollableTextView","开始滚动");
//		prepareDraw();
//		autoScroll = true;
//		this.invalidate();
//	}
	
	private float textLength = 0f;//文本长度
	private float viewWidth = 0f; //滚动条宽度
	private float tx = 0f; //文本的x方向坐标
	private float ty = 0f;//文本的y方向坐标
	private float temp_tx1 = 0.0f; //文本当前长度
	private float temp_tx2 = 0x0f; //文本当前变换的长度
	private boolean isStarting = false; //文本滚动开关
	private Paint paint = null; //画笔对象
	private String text = ""; //文本内容
	private static Bitmap bmp = null;
	private Rect src = null;
	private Rect dst = null;
	
	public ScrollableTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.forceLayout();
	}
	//初始化自动滚动条，每次更改文字内容的时候，都需要重新初始化一次
	public void initScrollTextView(WindowManager windowManage,String text){
		paint = this.getPaint();  //获取父类的textPaint
		this.text = text; 
		textLength = paint.measureText(text); //获取字符串长度
		viewWidth = this.getWidth(); //获取宽度 return mRight - mLeft;
		if(viewWidth == 0){
			if(windowManage!=null){
				Display display = windowManage.getDefaultDisplay(); //获取屏幕
				viewWidth = display.getWidth(); //获取屏幕宽度
			}
		}
		tx = textLength;
		temp_tx1 = viewWidth+textLength;
		temp_tx2 = viewWidth+textLength*2;
		ty = this.getTextSize()+this.getPaddingTop();
	}
	
	//开始滚动
	public void startScroll(){
		isStarting = true;
		this.invalidate(); //刷新屏幕
	}
	
	//停止滚动
	public void stopScroll(){
		isStarting = false;
		this.invalidate();//刷新屏幕
	}
	
	//重写onDraw()方法
	@Override
	protected void onDraw(Canvas canvas) {
		if(isStarting){
			paint.setARGB(255, 0, 0, 0);//A-Alpha透明度 ，R-Red红色，G-Green绿色，B-Blue蓝色
			canvas.drawText(text, temp_tx1 -tx, ty, paint);
			//Log.w("ScrollableTextView","x:" + (temp_tx1 -tx) + "  y:" + ty);
			tx+=1;
			if(tx>temp_tx2){
				tx = temp_tx1-viewWidth;
			}
			
		}else{
			if(paint == null){
				paint = getPaint();
			}
			paint.setARGB(255, 200, 200, 200);//A-Alpha透明度 ，R-Red红色，G-Green绿色，B-Blue蓝色
			canvas.drawText(text, 0.0f, this.getTextSize()+this.getPaddingTop(), paint);
			//Log.w("ScrollableTextView","x:" + 0.0f + "  y:" + (this.getTextSize()+this.getPaddingTop()));
		}
		if(bmp == null) bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.fade)).getBitmap();
		if(src == null) src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
		if(dst == null) dst = new Rect(0, 0, this.getWidth(), this.getHeight());
		canvas.drawBitmap(bmp, src, dst, null);
		this.invalidate();//刷新屏幕
	}
}
