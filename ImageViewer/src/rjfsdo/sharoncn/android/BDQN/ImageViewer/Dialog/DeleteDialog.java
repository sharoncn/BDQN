package rjfsdo.sharoncn.android.BDQN.ImageViewer.Dialog;

import rjfsdo.sharoncn.android.BDQN.ImageViewer.R;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * É¾³ý¶Ô»°¿ò
 * @author sharoncn
 *
 */
public class DeleteDialog extends BaseDialog {
	private Context context;
	private TextView title,message;
	private Button cancel,ok;
	private OnClickListener ok_listener;
	private OnClickListener cancel_listener;
	
	public DeleteDialog(Context context) {
		super(context);
		this.context = context;
		View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_delete, null);
		this.setView(view);
		
		title = (TextView) view.findViewById(R.id.dialog_delete_title);
		message = (TextView) view.findViewById(R.id.dialog_delete_message);
		cancel = (Button) view.findViewById(R.id.dialog_delete_cancel);
		ok = (Button) view.findViewById(R.id.dialog_delete_ok);
	}

	@Override
	public Builder setMessage(CharSequence message) {
		this.message.setText(message);
		return this;
	}

	@Override
	public Builder setMessage(int messageId) {
		return setMessage(context.getString(messageId));
	}

	@Override
	public Builder setNegativeButton(CharSequence text, OnClickListener listener) {
		this.ok.setText(text);
		this.ok_listener = listener;
		this.ok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(DeleteDialog.this.ok_listener != null){
					DeleteDialog.this.ok_listener.onClick(dialog, 1);
					dialog.dismiss();
				}
			}
		});
		return this;
	}

	@Override
	public Builder setNegativeButton(int textId, OnClickListener listener) {
		return setNegativeButton(context.getString(textId), listener);
	}

	@Override
	public Builder setPositiveButton(CharSequence text, OnClickListener listener) {
		this.cancel.setText(text);
		this.cancel_listener = listener;
		this.cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(DeleteDialog.this.cancel_listener != null){
					DeleteDialog.this.cancel_listener.onClick(dialog, 0);
				}
			}
		});
		return this;
	}

	@Override
	public Builder setPositiveButton(int textId, OnClickListener listener) {
		return setPositiveButton(context.getString(textId), listener);
	}

	@Override
	public Builder setTitle(CharSequence title) {
		this.title.setText(title);
		return this;
	}

	@Override
	public Builder setTitle(int titleId) {
		return setTitle(context.getString(titleId));
	}
	
	
}
