package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Adapter;

import java.util.List;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookType;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class BookTypeAdapter extends BookAdapter {
	private static final String TAG = null;
	private Context context;
	private OnClickListener lst = null;
	private static int layout_res = R.layout.layout_item_type;
	
	public BookTypeAdapter(Context context) {
		super(context, layout_res);
		this.context = context;
		setPlayAnimaion(false);
	}

	@Override
	protected void initViews(View view, int position) {
		TextView tv = (TextView) view.findViewById(R.id.type_item_tv_name);
		TableLayout table = (TableLayout) view.findViewById(R.id.type_item_table);
		BookType bookType = (BookType) data.get(position);
		tv.setText(bookType.getTypeName() + " :");
		
		List<BookType> childType = bookType.getChildType();
		table.setStretchAllColumns(true);
		table.removeAllViews();
		TableRow tr = null;
		for(int i = 0; i < childType.size(); i ++){
			Log.v(TAG,"i-value：" + i);
			if(i % 3 == 0){
				Log.v(TAG,"new TableRow");
				tr = new TableRow(context);
				TableLayout.LayoutParams row_params = new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				tr.setLayoutParams(row_params);
			}
			Button btn = new Button(context);
			btn.setText(childType.get(i).getTypeName());
			if(lst != null){
				btn.setOnClickListener(lst);
			}
			btn.setTag(childType.get(i).getTypeCode());
			//btn.setBackgroundResource(R.drawable.button_selector);
			
			Log.v(TAG,"button文字:" + btn.getText());
			btn.setVisibility(View.VISIBLE);
			tr.addView(btn);
			if(i % 3 == 2){
				Log.v(TAG,"addView");
				table.addView(tr);
			}else if(i == childType.size() - 1){
				Log.v(TAG,"addView");
				table.addView(tr);
			}
		}
	}
	
	/**
	 * 为Table中的Button设置监听器
	 * @param l
	 */
	public void setButtonOnClickListener(View.OnClickListener l){
		this.lst = l;
	}
}
