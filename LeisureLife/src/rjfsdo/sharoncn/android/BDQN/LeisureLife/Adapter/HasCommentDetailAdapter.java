package rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter;

import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Recommend;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Listener.OnCommentLoadedListener;

import android.content.Context;

/**
 * �����۵�������
 * @author sharoncn
 *
 */
public abstract class HasCommentDetailAdapter extends DetailBaseAdapter {
	protected CommentAdapter adapter;
	protected OnCommentLoadedListener comment_loaded;
	
	public HasCommentDetailAdapter(Context context) {
		super(context);
	}

	public void setOnCommentLoadedListener(OnCommentLoadedListener l){
		this.comment_loaded = l;
	}
	
	/**
	 * ��ȡ��������
	 * @return
	 */
	public List<Recommend> getComment(){
		return adapter.getData();
	}

}
