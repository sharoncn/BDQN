package rjfsdo.sharoncn.android.BDQN.LeisureLife.Components;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;

/**
 * Ĭ�ϲ˵����
 * @author sharoncn
 *
 */
public class DefaultMenu extends LinearLayout implements OnClickListener {
	private View view;
	private LinearLayout previous, comment, share, collection, next;
	private OnItemClickListener listener;

	public DefaultMenu(Context context, AttributeSet attrs) {
		super(context, attrs);

		view = LayoutInflater.from(context).inflate(R.layout.menu_default, null);
		initItems(view);

	}

	private void initItems(View view) {
		previous = (LinearLayout) view.findViewById(R.id.menu_previous);
		comment = (LinearLayout) view.findViewById(R.id.menu_comment);
		share = (LinearLayout) view.findViewById(R.id.menu_share);
		collection = (LinearLayout) view.findViewById(R.id.menu_collection);
		next = (LinearLayout) view.findViewById(R.id.menu_next);
		previous.setOnClickListener(this);
		comment.setOnClickListener(this);
		share.setOnClickListener(this);
		collection.setOnClickListener(this);
		next.setOnClickListener(this);

		view.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		this.addView(view);
	}

	/**
	 * ���������ղصĿ�����
	 * @param enabled
	 */
	public void setCollectionEnabled(boolean enabled){
		//���۵�����������һ���̼߳��صģ�����������δ������ɵ���ղذ�ť���������
		//���ṩ�˹���
		collection.setEnabled(enabled);
	}
	
	@Override
	public void onClick(View v) {
		int position = -1;
		int itemId = -1;
		View view = null;
		switch (v.getId()) {
		case R.id.menu_previous:
			position = 0;
			view = previous;
			itemId = R.id.menu_previous;
			break;
		case R.id.menu_comment:
			position = 1;
			view = comment;
			itemId = R.id.menu_comment;
			break;
		case R.id.menu_share:
			position = 2;
			view = share;
			itemId = R.id.menu_share;
			break;
		case R.id.menu_collection:
			position = 3;
			view = collection;
			itemId = R.id.menu_collection;
			break;
		case R.id.menu_next:
			position = 4;
			view = next;
			itemId = R.id.menu_next;
			break;
		}
		if(listener != null){
			listener.onItemClick(null, view, position, itemId);
		}
	}

	/**
	 * ���ò˵���OnItemClickListener,��������AdapterViewʼ��Ϊnull
	 * @param listener
	 */
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.listener = listener;
	}
}
