package rjfsdo.sharoncn.android.BDQN.ImageViewer.Dialog;

import rjfsdo.sharoncn.android.BDQN.ImageViewer.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 文件信息显示对话框
 * @author sharoncn
 *
 */
public class InfoDialog extends BaseDialog {
	private TextView title,name,path,time,size;
	private Context context;
	
	public InfoDialog(Context context) {
		super(context);
		this.context = context;
		View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_info, null);
		this.setView(view);
		title = (TextView) view.findViewById(R.id.dialog_info_title);
		name = (TextView) view.findViewById(R.id.dialog_info_name);
		path = (TextView) view.findViewById(R.id.dialog_info_path);
		time = (TextView) view.findViewById(R.id.dialog_info_time);
		size = (TextView) view.findViewById(R.id.dialog_info_size);
	}
	
	/**
	 * 设置显示的文件名
	 * @param text
	 * @return
	 */
	public InfoDialog setName(CharSequence text){
		name.setText(text);
		return this;
	}
	
	public InfoDialog setName(int resid){
		return setName(context.getString(resid));
	}
	
	/**
	 * 设置显示的路径
	 * @param text
	 * @return
	 */
	public InfoDialog setPath(CharSequence text){
		path.setText(text);
		return this;
	}
	
	public InfoDialog setPath(int resid){
		return setPath(context.getString(resid));
	}
	
	/**
	 * 设置显示的文件最后修改时间
	 * @param text
	 * @return
	 */
	public InfoDialog setTime(CharSequence text){
		time.setText(text);
		return this;
	}
	
	public InfoDialog setTime(int resid){
		return setTime(context.getString(resid));
	}
	
	/**
	 * 设置显示的文件大小
	 * @param text
	 * @return
	 */
	public InfoDialog setSize(CharSequence text){
		size.setText(text);
		return this;
	}
	
	public InfoDialog setSize(int resid){
		return setSize(context.getString(resid));
	}
	
	//设置标题
	@Override
	public InfoDialog setTitle(CharSequence title) {
		this.title.setText(title);
		return this;
	}

	@Override
	public InfoDialog setTitle(int titleId) {
		return setTitle(context.getString(titleId));
	}

}
