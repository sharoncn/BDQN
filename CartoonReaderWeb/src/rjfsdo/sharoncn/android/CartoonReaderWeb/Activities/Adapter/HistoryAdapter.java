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

public class HistoryAdapter extends BookAdapter {
	private static final int layout_res = R.layout.layout_item_readed;
	private Context context;
	
	public HistoryAdapter(Context context) {
		super(context, layout_res);
		this.context = context;
	}

	@Override
	protected void initViews(View view, int position) {
		ImageView item_image = (ImageView) view.findViewById(R.id.item_collect_image);
		TextView item_bookname = (TextView) view.findViewById(R.id.item_collect_bookname);
		TextView item_date = (TextView) view.findViewById(R.id.item_collect_readdate);
		TextView item_page = (TextView) view.findViewById(R.id.item_collect_readpage);
		RatingBar item_ratingbar = (RatingBar) view.findViewById(R.id.item_collect_ratingbar);
		BookInfo book = (BookInfo) data.get(position);
		
		
		//name
		String bookName = book.getBookName();
		item_bookname.setText(bookName);
		//image
		Bitmap coverImage;
		if(imageCache.containsKey(bookName)){
			coverImage = imageCache.get(bookName);
		}else{
			coverImage = Utils.getCoverImage(context,book.getCoverPath());
			imageCache.put(bookName, coverImage);
		}
		item_image.setImageBitmap(Utils.getCoverImage(context,book.getCoverPath()));
		//date
		item_date.setText(book.getLastReadDate());
		//page
		item_page.setText(context.getString(R.string.read_date_part1) + (Integer.parseInt(book.getLastReadedPage()) + 1) + 
				context.getString(R.string.read_date_part2) + book.getPageTotal() + context.getString(R.string.read_date_part3));
		//rating
		item_ratingbar.setRating(Float.parseFloat(book.getGrade()));
	}
}
