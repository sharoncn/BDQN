package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Components;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 程序锁中使用的检查密码的dialog
 * @author sharoncn
 *
 */
public class CheckPasswordDialog extends Builder implements OnClickListener {
	private Context mContext;
	private EditText et_pw;
	private Button ok, cancel;
	private AlertDialog mDialog;
	private DialogInterface.OnClickListener dialogClickListener;

	public Builder setOnClickListener(DialogInterface.OnClickListener dialogClickListener) {
		this.dialogClickListener = dialogClickListener;
		return this;
	}

	public CheckPasswordDialog(Context context) {
		super(context);
		this.mContext = context;
		initView();
	}

	public String getEditTextContent() {
		return et_pw.getText().toString();
	}

	private void initView() {
		final View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_check_password, null);
		setView(view);
		et_pw = (EditText) view.findViewById(R.id.dialog_check_pw);
		ok = (Button) view.findViewById(R.id.dialog_check_btn_ok);
		cancel = (Button) view.findViewById(R.id.dialog_check_btn_cancel);
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}
	
	@Override
	public AlertDialog show() {
		mDialog = super.show();
		return mDialog;
	}

	@Override
	public void onClick(View v) {
		int flag = DialogInterface.BUTTON_POSITIVE;
		switch(v.getId()){
		case R.id.dialog_check_btn_ok:
			flag = DialogInterface.BUTTON_POSITIVE;
			break;
		case R.id.dialog_check_btn_cancel:
			flag = DialogInterface.BUTTON_NEGATIVE;
			break;
		}
		if(dialogClickListener != null){
			dialogClickListener.onClick(mDialog, flag);
		}
	}
}
