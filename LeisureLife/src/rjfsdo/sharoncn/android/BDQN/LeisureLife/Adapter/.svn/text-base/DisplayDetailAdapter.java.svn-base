package rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter;

import java.util.HashMap;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.ListItemHeader;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Display;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Recommend;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLProtocol;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 展览详情的适配器
 * @author sharoncn
 *
 */
public class DisplayDetailAdapter extends HasCommentDetailAdapter {
	protected static final String TAG = "DisplayDetailAdapter";
	private Context context;
	private OnClickListener header_desc_onclick, header_comment_click;
	private Display data;
	private ListView comment_list;
	private ListItemHeader header_comment;
	private TextView description;

	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			Log.v(TAG, "handle a message");
			if (msg.getData().getBoolean(DataManager.FLAG_ISSUCCESS)) {
				List<Recommend> data = (List<Recommend>) msg.getData().getSerializable(DataManager.FLAG_DATA);
				if (data != null) {
					if (header_comment != null) {
						header_comment.setTitle(header_comment.getTitle() + ":(" + data.size() + ")");
					}
					if (comment_list != null) {
						adapter = new CommentAdapter(context, data);
						comment_list.setAdapter(adapter);
						Util.resizeListView(comment_list);
						if(comment_loaded != null)comment_loaded.onCommentLoaded();
					}
				}
			}
			super.handleMessage(msg);
		}
	};

	public DisplayDetailAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View initLayoutOne(View container) {
		if (container == null) {
			container = LayoutInflater.from(context).inflate(R.layout.list_item_detail_display_one, null);
		}
		ImageView img = (ImageView) container.findViewById(R.id.list_item_detail_display_img);
		TextView txt1 = (TextView) container.findViewById(R.id.list_item_detail_display_txtone);
		TextView txt2 = (TextView) container.findViewById(R.id.list_item_detail_display_txttwo);
		TextView txt3 = (TextView) container.findViewById(R.id.list_item_detail_display_txtthree);
		TextView txt4 = (TextView) container.findViewById(R.id.list_item_detail_display_txtfour);

		data = (Display) this.getItem(getPosition());
		img.setImageDrawable(DataManager.getInstance(context).getImage(data.getImage()));
		txt1.setText(data.getName());
		txt2.setText(data.getHost());
		txt3.setText(data.getTime());
		txt4.setText(data.getCall());
		return container;
	}

	@Override
	public View initLayoutTwo(View container) {
		if (container == null) {
			container = LayoutInflater.from(context).inflate(R.layout.list_item_detail_movie_two, null);
		}
		ListItemHeader header_desc = (ListItemHeader) container.findViewById(R.id.list_item_detail_movie_two_header);
		if (header_desc_onclick != null) {
			header_desc.setButtonOnClickListener(header_desc_onclick);
		}
		header_desc.setTitle(R.string.displaydesc);
		description = (TextView) container.findViewById(R.id.list_item_detail_movie_two_description);
		description.setVisibility(View.GONE);
		description.setText(data.getDesc());
		return container;
	}

	@Override
	public View initLayoutThree(View container) {
		if (container == null) {
			container = LayoutInflater.from(context).inflate(R.layout.list_item_detail_movie_three, null);
		}
		header_comment = (ListItemHeader) container.findViewById(R.id.list_item_detail_movie_three_header);
		if (header_comment_click != null) {
			header_comment.setButtonOnClickListener(header_comment_click);
		}
		header_comment.setTitle(R.string.comment);
		comment_list = (ListView) container.findViewById(R.id.list_item_detail_movie_three_comment);
		comment_list.setVisibility(View.GONE);
		initRecommend();
		return container;
	}

	private void initRecommend() {
		HashMap<String, String> args = new HashMap<String, String>();
		args.put(URLProtocol.CMD, URLProtocol.GET_COMMENT_CMD_VALUE);
		args.put(URLProtocol.TYPE, "1");
		args.put(URLProtocol.TID, data.getId());
		try {
			DataManager.getInstance(context).getData(Recommend.class,DataManager.FLAG_GET_RECOMMAND, handler, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置评论内容的可见性
	 * 
	 * @param visibility
	 */
	public void setCommentVisibility(int visibility) {
		comment_list.setVisibility(visibility);
	}

	/**
	 * 设置详细描述内容的可见性
	 * 
	 * @param visibility
	 */
	public void setDescriptionVisibility(int visibility) {
		description.setVisibility(visibility);
	}

	public void setHeaderDescButtonOnClickListener(OnClickListener l) {
		this.header_desc_onclick = l;
	}

	public void setHeaderCommentButtonOnClickListener(OnClickListener l) {
		this.header_comment_click = l;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
}
