package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 默认地步菜单
 * @author sharoncn
 *
 */
public class DefaultMenu extends LinearLayout implements OnClickListener {
	private static LayoutInflater inflater;
	
	public static final int ID_REFRESH = R.id.container_refresh;
	public static final int ID_COMMENT = R.id.container_comment;
	public static final int ID_COLLECTION = R.id.container_collection;
	public static final int ID_REPOST = R.id.container_repost;
	public static final int ID_MORE = R.id.container_more;

	private static final String TAG = null;
	
	
	private ImageView refresh, comment, collection, repost, more;
	private LinearLayout cRefresh, cComment, cCollection, cRepost, cMore;

	private OnClickListener listener;

	public DefaultMenu(Context context) {
		this(context, null);
	}

	public DefaultMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflater = LayoutInflater.from(context);
		intiContent();
	}

	private void intiContent() {
		View view = inflater.inflate(R.layout.menu_default, null);
		refresh = (ImageView) view.findViewById(R.id.menu_refresh);
		comment = (ImageView) view.findViewById(R.id.menu_comment);
		collection = (ImageView) view.findViewById(R.id.menu_collection);
		repost = (ImageView) view.findViewById(R.id.menu_repost);
		more = (ImageView) view.findViewById(R.id.menu_more);

		cRefresh = (LinearLayout) view.findViewById(R.id.container_refresh);
		cComment = (LinearLayout) view.findViewById(R.id.container_comment);
		cCollection = (LinearLayout) view.findViewById(R.id.container_collection);
		cRepost = (LinearLayout) view.findViewById(R.id.container_repost);
		cMore = (LinearLayout) view.findViewById(R.id.container_more);

		cRefresh.setOnClickListener(this);
		cComment.setOnClickListener(this);
		cCollection.setOnClickListener(this);
		cRepost.setOnClickListener(this);
		cMore.setOnClickListener(this);
		
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(params);
		this.addView(view);
	}
	
	

	@Override
	public void setOnClickListener(OnClickListener l) {
		listener = l;
	}

	@Override
	public void onClick(View v) {
		Log.v(TAG,"onClick");
		final int id = v.getId();
		switch (id) {
		case R.id.container_refresh:
			Log.v(TAG,"container_refresh onClick");
			refresh.requestFocus();
			if(refresh.hasFocus()){
				Log.v(TAG,"refresh has focus");
			}else{
				Log.v(TAG,"refresh has nofocus");
			}
			break;
		case R.id.container_comment:
			Log.v(TAG,"container_comment onClick");
			comment.requestFocus();
			break;
		case R.id.container_collection:
			Log.v(TAG,"container_collection onClick");
			collection.requestFocus();
			break;
		case R.id.container_repost:
			Log.v(TAG,"container_repost onClick");
			repost.requestFocus();
			break;
		case R.id.container_more:
			Log.v(TAG,"container_more onClick");
			more.requestFocus();
			break;
		}
		if(listener != null){
			listener.onClick(v);
		}
	}

}
