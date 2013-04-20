package rjfsdo.sharoncn.android.BDQN.ImageViewer.Dialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Dialog的基类，重写了AlertDialog.Builder的Create和show方法，设置AlertDialog的Root容器的背景为transparent
 * 这样，可以无干扰的自定义自己风格的Dialog
 * @author sharoncn
 *
 */
public class BaseDialog extends Builder {
	//private static final String TAG = "BaseDialog";
	protected AlertDialog dialog = null;

	public BaseDialog(Context context) {
		super(context);
	}

	//得到android.R.id.custom的父容器并将其设置为透明
	@Override
	public AlertDialog create() {
		if(dialog == null){
			dialog = super.create();
		}
		//dialog上如果没有文本框，则需要手动设置WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM标志，让dialog处于
		//输入法之上
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM, WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		dialog.show();
		
		//这里将custom容器的父容器设置为透明，这样就没有了讨厌的边框了
		FrameLayout fl = (FrameLayout) dialog.findViewById(android.R.id.custom); 
		if(fl == null){
			//Log.i(TAG,"FrameLayout is NULL");
		}else{
			((FrameLayout)fl.getParent()).setBackgroundResource(android.R.color.transparent);
			//Log.i(TAG,"FrameLayout父容器" + fl.getParent().getClass().toString());
		}
		
		return dialog;
	}

	@Override
	public AlertDialog show() {
		return create();
	}
}
