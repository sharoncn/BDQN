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
//		Log.w("ScrollableTextView","��ʼ����");
//		prepareDraw();
//		autoScroll = true;
//		this.invalidate();
//	}
	
	private float textLength = 0f;//�ı�����
	private float viewWidth = 0f; //���������
	private float tx = 0f; //�ı���x��������
	private float ty = 0f;//�ı���y��������
	private float temp_tx1 = 0.0f; //�ı���ǰ����
	private float temp_tx2 = 0x0f; //�ı���ǰ�任�ĳ���
	private boolean isStarting = false; //�ı���������
	private Paint paint = null; //���ʶ���
	private String text = ""; //�ı�����
	private static Bitmap bmp = null;
	private Rect src = null;
	private Rect dst = null;
	
	public ScrollableTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.forceLayout();
	}
	//��ʼ���Զ���������ÿ�θ����������ݵ�ʱ�򣬶���Ҫ���³�ʼ��һ��
	public void initScrollTextView(WindowManager windowManage,String text){
		paint = this.getPaint();  //��ȡ�����textPaint
		this.text = text; 
		textLength = paint.measureText(text); //��ȡ�ַ�������
		viewWidth = this.getWidth(); //��ȡ��� return mRight - mLeft;
		if(viewWidth == 0){
			if(windowManage!=null){
				Display display = windowManage.getDefaultDisplay(); //��ȡ��Ļ
				viewWidth = display.getWidth(); //��ȡ��Ļ���
			}
		}
		tx = textLength;
		temp_tx1 = viewWidth+textLength;
		temp_tx2 = viewWidth+textLength*2;
		ty = this.getTextSize()+this.getPaddingTop();
	}
	
	//��ʼ����
	public void startScroll(){
		isStarting = true;
		this.invalidate(); //ˢ����Ļ
	}
	
	//ֹͣ����
	public void stopScroll(){
		isStarting = false;
		this.invalidate();//ˢ����Ļ
	}
	
	//��дonDraw()����
	@Override
	protected void onDraw(Canvas canvas) {
		if(isStarting){
			paint.setARGB(255, 0, 0, 0);//A-Alpha͸���� ��R-Red��ɫ��G-Green��ɫ��B-Blue��ɫ
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
			paint.setARGB(255, 200, 200, 200);//A-Alpha͸���� ��R-Red��ɫ��G-Green��ɫ��B-Blue��ɫ
			canvas.drawText(text, 0.0f, this.getTextSize()+this.getPaddingTop(), paint);
			//Log.w("ScrollableTextView","x:" + 0.0f + "  y:" + (this.getTextSize()+this.getPaddingTop()));
		}
		if(bmp == null) bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.fade)).getBitmap();
		if(src == null) src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
		if(dst == null) dst = new Rect(0, 0, this.getWidth(), this.getHeight());
		canvas.drawBitmap(bmp, src, dst, null);
		this.invalidate();//ˢ����Ļ
	}
}
