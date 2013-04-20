package rjfsdo.sharoncn.android.BDQN.LeisureLife.Adapter;

import java.util.HashMap;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.ListItemHeader;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Recommend;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.Show;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Net.URLProtocol;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 演出详情的适配器
 * @author sharoncn
 *
 */
public class ShowDetailAdapter extends HasCommentDetailAdapter implements OnClickListener{
	protected static final String TAG = "ShowDetailAdapter";
	public static final int CONTENT_BTN_RESERVE = R.id.list_item_detail_show_btn_reserve;
	public static final int CONTENT_BTN_FIND = R.id.list_item_detail_show_btn_find;
	private Context context;
	private OnClickListener header_desc_onclick, header_comment_click, content_click;
	private Show data;
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

	public ShowDetailAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View initLayoutOne(View container) {
		if (container == null) {
			container = LayoutInflater.from(context).inflate(R.layout.list_item_detail_show_one, null);
		}
		ImageView img = (ImageView) container.findViewById(R.id.list_item_detail_show_img);
		TextView txt1 = (TextView) container.findViewById(R.id.list_item_detail_show_txtone);
		TextView txt2 = (TextView) container.findViewById(R.id.list_item_detail_show_txttwo);
		TextView txt3 = (TextView) container.findViewById(R.id.list_item_detail_show_txtthree);
		TextView txt4 = (TextView) container.findViewById(R.id.list_item_detail_show_txtfour);
		Button btnReserve = (Button) container.findViewById(R.id.list_item_detail_show_btn_reserve);
		Button btnFind = (Button) container.findViewById(R.id.list_item_detail_show_btn_find);
		btnReserve.setOnClickListener(this);
		btnFind.setOnClickListener(this);
		data = (Show) this.getItem(getPosition());
		img.setImageDrawable(DataManager.getInstance(context).getImage(data.getImage()));
		txt1.setText(data.getName());
		txt2.setText(context.getString(R.string.timeis) + data.getTime());
		txt3.setText(context.getString(R.string.addris) + data.getAddr());
		txt4.setText(context.getString(R.string.priceis) + data.getPrice());
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
		header_desc.setTitle(R.string.showdesc);
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
		args.put(URLProtocol.TYPE, "1");//不知道这个TYPE是标示什么的，文档没有说明
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
	
	/**
	 * 设置预定和查找按钮的监听器
	 * @param l
	 */
	public void setContentButtonOnClickListener(OnClickListener l){
		content_click = l;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.list_item_detail_show_btn_find:
			v.setId(R.id.list_item_detail_show_btn_find);
			break;
		case R.id.list_item_detail_show_btn_reserve:
			v.setId(R.id.list_item_detail_show_btn_reserve);
			break;
		}
		if(content_click != null){
			content_click.onClick(v);
		}
	}

}
