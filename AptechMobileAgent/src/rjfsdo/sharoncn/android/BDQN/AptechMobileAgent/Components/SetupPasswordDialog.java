package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Components;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

/**
 * 程序锁使用的创建密码的dialog
 * @author sharoncn
 *
 */
public class SetupPasswordDialog extends Builder implements OnCheckedChangeListener, OnClickListener {
	private Context mContext;
	private EditText etNewPw, etConform;
	private CheckBox cb;
	private Button ok, cancel;
	private AlertDialog mDialog;
	private DialogInterface.OnClickListener dialogClickListener;

	public Builder setOnClickListener(DialogInterface.OnClickListener dialogClickListener) {
		this.dialogClickListener = dialogClickListener;
		return this;
	}

	public SetupPasswordDialog(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	private void initView() {
		final View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_setup_password, null);
		setView(view);
		etNewPw = (EditText) view.findViewById(R.id.dialog_setup_npw);
		etConform = (EditText) view.findViewById(R.id.dialog_setup_ncomform);
		cb = (CheckBox) view.findViewById(R.id.dialog_setup_cb);
		cb.setOnCheckedChangeListener(this);
		ok = (Button) view.findViewById(R.id.dialog_setup_btn_ok);
		cancel = (Button) view.findViewById(R.id.dialog_setup_btn_cancel);
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			etNewPw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			etConform.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		} else {
			etNewPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			etConform.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
	}

	public String getPassword() {
		return etNewPw.getText().toString();
	}

	public String getConform() {
		return etConform.getText().toString();
	}

	@Override
	public AlertDialog show() {
		mDialog = super.show();
		return mDialog;
	}

	@Override
	public void onClick(View v) {
		int flag = DialogInterface.BUTTON_POSITIVE;
		switch (v.getId()) {
		case R.id.dialog_setup_btn_ok:
			flag = DialogInterface.BUTTON_POSITIVE;
			break;
		case R.id.dialog_setup_btn_cancel:
			flag = DialogInterface.BUTTON_NEGATIVE;
			break;
		}
		if (dialogClickListener != null) {
			dialogClickListener.onClick(mDialog, flag);
		}
	}
}
