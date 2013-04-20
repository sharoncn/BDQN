package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Components;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Constants;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.MD5Encoder;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Util;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

/**
 * 程序锁中使用的修改密码Dialog
 * @author sharoncn
 *
 */
public final class ModifyPasswordDialog extends Builder implements OnClickListener, OnCheckedChangeListener {
	private Context mContext;
	private static LayoutInflater inflater;
	private static SharedPreferences sp;
	private EditText oldPW, newPW, comform;
	private CheckBox cb;

	public ModifyPasswordDialog(Context context) {
		super(context);
		mContext = context;
		if (sp == null)
			sp = PreferenceManager.getDefaultSharedPreferences(context);
		if (inflater == null) {
			inflater = LayoutInflater.from(context);
		}
		initView();
	}

	private void initView() {
		final View view = inflater.inflate(R.layout.dialog_modify_pw, null);
		this.setView(view);
		this.setPositiveButton(R.string.ok, this);
		this.setNegativeButton(R.string.cancel, this);

		oldPW = (EditText) view.findViewById(R.id.dialog_modify_opw);
		newPW = (EditText) view.findViewById(R.id.dialog_modify_npw);
		comform = (EditText) view.findViewById(R.id.dialog_modify_ncomform);

		cb = (CheckBox) view.findViewById(R.id.dialog_modify_cb);
		cb.setOnCheckedChangeListener(this);
	}

	public ModifyPasswordDialog(Context context, String pw) {
		this(context);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:
			// 检查EditText
			if (checkEditText()) {
				// 提交到SP
				final Editor editor = sp.edit();
				editor.putString(Constants.PREFERENCE_PW, MD5Encoder.encode(newPW.getText().toString()));
				if (editor.commit()) {
					Util.showMsg(mContext, R.string.modify_pw_success);
				} else {
					Util.showMsg(mContext, R.string.modify_pw_fail);
				}
			}
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			dialog.dismiss();
			break;
		}
	}

	private boolean checkEditText() {
		final String pw_old = oldPW.getText().toString();
		final String pw_new = newPW.getText().toString();
		final String pw_comform = comform.getText().toString();
		if (TextUtils.isEmpty(pw_old) || TextUtils.isEmpty(pw_new) || TextUtils.isEmpty(pw_comform)) {
			Util.showMsg(mContext, R.string.input_err);
			return false;
		}
		if (!MD5Encoder.encode(pw_old).equals(sp.getString(Constants.PREFERENCE_PW, ""))) {
			Util.showMsg(mContext, R.string.pw_err);
			return false;
		}

		if (!pw_new.equals(pw_comform)) {
			Util.showMsg(mContext, R.string.comform_err);
			return false;
		}

		return true;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			oldPW.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			newPW.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			comform.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		} else {
			oldPW.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			newPW.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			comform.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
	}
}
