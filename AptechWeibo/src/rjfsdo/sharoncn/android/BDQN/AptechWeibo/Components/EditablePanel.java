package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * �б༭���Panel
 * @author sharoncn
 *
 */
public class EditablePanel extends OneColumnPanel {
	private ArrayList<EditText> allowEditContentViews = new ArrayList<EditText>();
	private EditText et_content;
	
	public EditablePanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews();
	}

	private void initViews() {
		View view = inflater.inflate(R.layout.panel_simple_info_edit, null);
		TextView tv_title = (TextView) view.findViewById(R.id.panel_title);
		et_content = (EditText) view.findViewById(R.id.panel_content);
		allowEditContentViews.add(et_content);
		tv_title.setText(mContext.getString(R.string.nickname));
		this.addItem(view);

		view = inflater.inflate(R.layout.panel_simple_info_edit, null);
		tv_title = (TextView) view.findViewById(R.id.panel_title);
		et_content = (EditText) view.findViewById(R.id.panel_content);
		allowEditContentViews.add(et_content);
		tv_title.setText(mContext.getString(R.string.description));
		this.addItem(view);
	}
	
	/**
	 * ��ñ༭�������
	 * @param index  �༭�������
	 * @return
	 */
	public String getEditContent(int index){
		if(index > 1 || index < 0){
			throw new ArrayIndexOutOfBoundsException("index");
		}
		return allowEditContentViews.get(index).getText().toString();
	}
	
	/**
	 * ���ñ༭�������
	 * @param index   �༭�������
	 * @param content ����
	 */
	public void setEditContent(int index,String content){
		if(index > 1 || index < 0){
			throw new ArrayIndexOutOfBoundsException("index");
		}
		allowEditContentViews.get(index).setText(content);
	}
}
