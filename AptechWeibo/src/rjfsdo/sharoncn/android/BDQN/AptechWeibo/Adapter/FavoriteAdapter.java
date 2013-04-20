package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Adapter;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Favorite;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.HasUser;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Status;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.User;
import android.content.Context;
import android.text.Spannable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public final class FavoriteAdapter extends HomeAdapter {

	public FavoriteAdapter(Context context) {
		super(context);
		layoutResId = R.layout.list_item_favorite;
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
			holder.auth = (ImageView) convertView.findViewById(R.id.auth);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.content = (TextView) convertView.findViewById(R.id.content);
		}

		Favorite favorite = (Favorite) data.get(position);
		Status status = favorite.getStatus();
		holder.img.setImageDrawable(dm.getImage(status.getUser().getProfile_image_url(), handler, position));
		holder.who.setText(status.getUser().getScreen_name());
		holder.auth.setImageDrawable(dm.getImage(status.getUser().getVerified(), handler, position));
		holder.time.setText(dateFilter.doFilter(status.getCreated_at()).toString());
		holder.content.setText((Spannable) expressionFilter.doFilter(status.getText()));
		convertView.setTag(holder);

		return convertView;
	}

	@Override
	protected int getIndex(String src) {
		int count = data.size();
		for (int i = 0; i < count; i++) {
			HasUser o = ((Favorite) data.get(i)).getStatus();
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
		public TextView content;
	}
}
