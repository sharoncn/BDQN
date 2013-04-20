package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookInfo;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Utils;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DefaultBookAdapter extends BookAdapter {
	public static final int layout_res = R.layout.layout_item_normal;
	private Context context;
	
	public DefaultBookAdapter(Context context) {
		super(context, layout_res);
		this.context = context;
	}

	@Override
	protected void initViews(View view,int position) {
		ImageView item_image = (ImageView) view.findViewById(R.id.item_image);
		TextView item_bookname = (TextView) view.findViewById(R.id.item_bookname);
		TextView item_date = (TextView) view.findViewById(R.id.item_date);
		TextView item_descreption = (TextView) view.findViewById(R.id.item_descreption);
		RatingBar item_ratingbar = (RatingBar) view.findViewById(R.id.item_ratingbar);
		
		BookInfo book = (BookInfo) data.get(position);
		//书名
		String bookName = book.getBookName();
		item_bookname.setText(bookName);
		//封面
		Bitmap coverImage;
		if(imageCache.containsKey(bookName)){
			coverImage = imageCache.get(bookName);
		}else{
			coverImage = Utils.getCoverImage(context,book.getCoverPath());
			imageCache.put(bookName, coverImage);
		}
		
		item_image.setImageBitmap(coverImage);
		//日期
		item_date.setText(book.getCreateDate());
		//评分
		item_ratingbar.setRating(Float.parseFloat(book.getGrade()));
		//描述
		item_descreption.setText(book.getBookContent());
	}
}
