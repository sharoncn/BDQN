package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookInfo;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Utils;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class BookDetailAdapter extends BookAdapter {
	private static final int layout_res = R.layout.layout_item_bookdetail;
	private Context context;
	private OnClickListener collection;
	private OnClickListener read;
	
	public BookDetailAdapter(Context context) {
		super(context, layout_res);
		this.context = context;
	}

	@Override
	protected void initViews(View view, int position) {
		ImageView item_image = (ImageView) view.findViewById(R.id.bookdetail_item_image);
		TextView item_bookname = (TextView) view.findViewById(R.id.bookdetail_item_bookname);
		TextView item_writer = (TextView) view.findViewById(R.id.bookdetail_item_writer);
		TextView item_description = (TextView) view.findViewById(R.id.bookdetail_item_descreption);
		RatingBar item_ratingbar = (RatingBar) view.findViewById(R.id.bookdetail_item_ratingbar);
		
		ImageButton btn_collection = (ImageButton) view.findViewById(R.id.bookdetail_item_btn_collection);
		btn_collection.setTag(position);
		btn_collection.setOnClickListener(collection);
		
		ImageButton btn_read = (ImageButton) view.findViewById(R.id.bookdetail_item_btn_read);
		btn_read.setTag(position);
		btn_read.setOnClickListener(read);
		
		BookInfo book = (BookInfo) data.get(position);
		
		//image
		item_image.setImageBitmap(Utils.getCoverImage(context,book.getCoverPath()));
		//name
		item_bookname.setText(context.getString(R.string.bookInfo_bookTitle) + "    " + book.getBookName());
		//date
		item_description.setText(book.getBookContent());
		//page
		item_writer.setText(context.getString(R.string.bookInfo_writer) + "    " + book.getWriter());
		//rating
		item_ratingbar.setRating(Float.parseFloat(book.getGrade()));
	}
	
	/**
	 * 为收藏按钮添加监听器，调用时可通过View的getTag方法获得item的position
	 * @param l
	 */
	public void setCollectionOnClickListener(OnClickListener l){
		collection = l;
	}
	
	/**
	 * 为阅读按钮添加监听器，调用时可通过View的getTag方法获得item的position
	 * @param l
	 */
	public void setReadOnClickListener(OnClickListener l){
		read = l;
	}
}
