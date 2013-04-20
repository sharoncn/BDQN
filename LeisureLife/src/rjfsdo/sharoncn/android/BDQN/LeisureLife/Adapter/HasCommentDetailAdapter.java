package rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter;

import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Recommend;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Listener.OnCommentLoadedListener;

import android.content.Context;

/**
 * 有评论的适配器
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
	 * 获取评论数据
	 * @return
	 */
	public List<Recommend> getComment(){
		return adapter.getData();
	}

}
