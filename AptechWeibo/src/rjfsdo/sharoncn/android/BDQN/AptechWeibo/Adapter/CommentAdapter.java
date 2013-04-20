package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Adapter;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Comment;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Status;
import android.content.Context;
import android.text.Spannable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CommentAdapter extends HomeAdapter {

	public CommentAdapter(Context context) {
		super(context);
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
			holder.reference = (TextView) convertView.findViewById(R.id.reference);
			holder.from = (TextView) convertView.findViewById(R.id.from);
			holder.referenceContainer = (RelativeLayout) convertView.findViewById(R.id.referenceContainer);
			holder.referenceWho = (TextView) convertView.findViewById(R.id.referencewho);
			holder.referecceAuth = (ImageView) convertView.findViewById(R.id.referenceauth);
		}
		Comment comment = (Comment) data.get(position);
		holder.img.setImageDrawable(dm.getImage(comment.getUser().getProfile_image_url(), handler, position));
		holder.who.setText(comment.getUser().getScreen_name());
		holder.auth.setImageDrawable(dm.getImage(comment.getUser().getVerified(), handler, position));
		holder.time.setText(dateFilter.doFilter(comment.getCreated_at()).toString());
		holder.content.setText((Spannable)expressionFilter.doFilter(comment.getText()));
		Status retweeted = comment.getStatus();
		if (retweeted == null) {
			holder.referenceContainer.setVisibility(View.GONE);
		} else {
			holder.referenceContainer.setVisibility(View.VISIBLE);
			holder.referenceWho.setText("@" + retweeted.getUser().getScreen_name());
			holder.referecceAuth.setImageDrawable(dm.getImage(retweeted.getUser().getVerified(), handler, position));
			holder.reference.setText((Spannable)expressionFilter.doFilter(retweeted.getText()));
		}
		holder.from.setText((Spannable) htmlFilter.doFilter(comment.getSource()));
		convertView.setTag(holder);
		return convertView;
	}

}
