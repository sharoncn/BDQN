package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Adapter;

import java.util.Arrays;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.WeiboDataManager;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.HasImage;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.HasTime;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.HasUser;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Status;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.User;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.DateFilter;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.ExpressionFilter;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.HtmlFilter;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.ImageCache;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.TimeComparator;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 所有适配器有些细微差异。所以其他的adapter直接从HomeAdapter继承，重写initView方法和Holder类(如果布局不同的话)。
 * 其他的逻辑大体上可以复用。
 * @author sharoncn
 *
 */
public class HomeAdapter extends BasicAdapter {
	protected int layoutResId = R.layout.list_item_main;
	private static final String TAG = "HomeAdapter";
	protected WeiboDataManager dm;
	protected static DateFilter dateFilter;
	protected static HtmlFilter htmlFilter;
	protected static ExpressionFilter expressionFilter;
	protected Context context;
	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WeiboDataManager.MSGTYPE_IMAGEOK:
				Bundle data = msg.getData();
				if (data.getBoolean(WeiboDataManager.FLAG_ISSUCCESS)) {
					final String key = data.getString(WeiboDataManager.FLAG_IMAGEKEY);
					Log.i(TAG, "请求图片返回成功,图片路径:" + key);
					final Bitmap bmp = (Bitmap) data.getParcelable(WeiboDataManager.FLAG_DATA);
					ImageCache.getInstance(context).put(key, bmp);
					notifyDataSetChanged(key, bmp);

				}
				break;
			}
			super.handleMessage(msg);
		}

	};

	public HomeAdapter(Context context) {
		super(context);
		dm = WeiboDataManager.getInstance(context);
		dateFilter = new DateFilter();
		htmlFilter = new HtmlFilter();
		expressionFilter = ExpressionFilter.getInstance(context);
	}

	/**
	 * 为Adapter设置数据
	 * @param data
	 */
	public void setData(List<Object> data) {
		this.data = data;
	}

	/**
	 * 为Adapter添加数据
	 * @param data
	 */
	public void addData(List<Object> data) {
		this.data.addAll(data);
		HasTime[] array = this.data.toArray(new HasTime[] {});
		Arrays.sort(array, new TimeComparator());
		this.data.clear();
		int count = array.length;
		for (int i = 0; i < count; i++) {
			this.data.add(array[i]);
		}
	}

	@Override
	protected View initView(int position, View convertView) {
		if (convertView == null) {
			convertView = inflater.inflate(layoutResId, null);
		} else {
			View view = null;
			try {
				view = convertView.findViewById(R.id.tv_more);
			} catch (Exception e) {
			}
			if (view != null) {
				convertView = inflater.inflate(layoutResId, null);
			}
		}

		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.img);
			holder.who = (TextView) convertView.findViewById(R.id.who);
			holder.pic = (ImageView) convertView.findViewById(R.id.pic);
			holder.auth = (ImageView) convertView.findViewById(R.id.auth);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.reference = (TextView) convertView.findViewById(R.id.reference);
			holder.from = (TextView) convertView.findViewById(R.id.from);
			holder.referenceContainer = (RelativeLayout) convertView.findViewById(R.id.referenceContainer);
			holder.referenceWho = (TextView) convertView.findViewById(R.id.referencewho);
			holder.referecceAuth = (ImageView) convertView.findViewById(R.id.referenceauth);
		}

		// 可将需要的数据提取一个接口出来
		// 等写完了再提取
		Status status = (Status) data.get(position);
		holder.img.setImageDrawable(dm.getImage(status.getUser().getProfile_image_url(), handler, position));
		holder.who.setText(status.getUser().getScreen_name());
		final String statusPic = status.getImage();
		if(statusPic != null && !"".equals(statusPic)){
			Log.i(TAG,"statusPic is not null:" + statusPic);
			holder.pic.setVisibility(View.VISIBLE);
			holder.pic.setImageDrawable(dm.getImage(statusPic, handler, position));
		}else{
			Log.i(TAG,"statusPic is null");
			holder.pic.setVisibility(View.GONE);
		}
		holder.auth.setImageDrawable(dm.getImage(status.getUser().getVerified(), handler, position));
		holder.time.setText(dateFilter.doFilter(status.getCreated_at()).toString());
		holder.content.setText((Spannable) expressionFilter.doFilter(status.getText()));
		Status retweeted = status.getRetweeted_status();
		if (retweeted == null) {
			holder.referenceContainer.setVisibility(View.GONE);
		} else {
			holder.referenceContainer.setVisibility(View.VISIBLE);
			final User user = retweeted.getUser();
			if(user != null){
				holder.referenceWho.setText("@" + retweeted.getUser().getScreen_name());
				holder.referecceAuth.setImageDrawable(dm.getImage(retweeted.getUser().getVerified(), handler, position));
			}
			holder.reference.setText((Spannable) expressionFilter.doFilter(retweeted.getText()));
		}
		holder.from.setText((Spannable) htmlFilter.doFilter(status.getSource()));
		convertView.setTag(holder);
		return convertView;
	}

	public void notifyDataSetChanged(String src, Bitmap drawable) {
		Log.v(TAG, "notifyDataSetChanged,src:" + src);
		// listView.getFirstVisiblePosition();
		if (drawable == null) {
			Log.w(TAG, "drawable is null");
			return;
		}
		if (src == null || "".equals(src)) {
			return;
		}
		int index = getIndex(src);
		if (index == -1)
			return;
		final View view = listView.getChildAt(index);
		if (view == null)
			return;
		ViewHolder holder = (ViewHolder) view.getTag();
		if (holder == null) {
			Log.v(TAG, "viewHolder is null");
			return;
		}
		view.setTag(setImage(index, src, holder, drawable));
	}

	protected ViewHolder setImage(int index, String src, ViewHolder holder, Bitmap drawable) {
		Object s = data.get(index);
		if (s instanceof HasImage) {
			if (src.equals(((HasImage) s).getImage())) {
				holder.pic.setImageBitmap(drawable);
				return holder;
			}
		}
		holder.img.setImageBitmap(drawable);
		holder.img.invalidate();
		return holder;
	}

	protected int getIndex(String src) {
		int count = data.size();
		for (int i = 0; i < count; i++) {
			Object d = data.get(i);
			if (d instanceof HasImage) {
				if (src.equals(((HasImage) d).getImage()))
					return i;
			}
			HasUser o = (HasUser) data.get(i);
			User user = o.getUser();
			if (user.getImage().equals(src)) {
				return i;
			}
		}
		return -1;
	}

	class ViewHolder {
		public ImageView img;
		public TextView who;
		public ImageView auth;
		public TextView time;
		public ImageView pic;
		public TextView content;
		public TextView from;
		public TextView reference;
		public RelativeLayout referenceContainer;
		public TextView referenceWho;
		public ImageView referecceAuth;
	}
}
