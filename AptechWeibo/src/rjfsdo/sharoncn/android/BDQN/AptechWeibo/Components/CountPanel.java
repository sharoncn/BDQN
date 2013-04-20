package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Components;

import java.util.ArrayList;
import java.util.HashMap;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 统计信息Panel
 * @author sharoncn
 *
 */
public class CountPanel extends MultiColumnPanel {
	private HashMap<Integer,ArrayList<TextView>> allowSetContentViews = new HashMap<Integer, ArrayList<TextView>>();
	protected String[][] titles;
	private int rowCount,columnCount;

	public CountPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		prepare();
		initViews();
	}

	protected void prepare(){
		titles = new String[2][2];
		titles[0][0] = mContext.getString(R.string.friends_count);
		titles[0][1] = mContext.getString(R.string.statuses_count);
		titles[1][0] = mContext.getString(R.string.followers_count);
		titles[1][1] = mContext.getString(R.string.favourites_count);
	}
	
	private void initViews() {
		if(titles == null){
			return;
		}
		
		View view;
		
		rowCount = titles.length;
		columnCount = titles[0].length;
		for (int i = 0; i < rowCount; i++) {
			int rowNum = this.newRow();
			ArrayList<TextView> row = new ArrayList<TextView>();
			for (int k = 0; k < columnCount; k++) {
				view = inflater.inflate(R.layout.panel_simple_info_v, null);
				TextView tv_title = (TextView) view.findViewById(R.id.panel_title);
				TextView tv_content = (TextView) view.findViewById(R.id.panel_content);
				row.add(tv_content);
				tv_title.setText(titles[i][k]);
				tv_content.setText("0");
				this.addItem(view);
			}
			allowSetContentViews.put(rowNum, row);
		}

	}

	/**
	 * 为Panel的item设置内容
	 * @param row   行索引，从0开始
	 * @param column 列索引，从0开始
	 * @param resId  要设置的内容
	 */
	public void setContentText(int row,int column,int resId){
		setContentText(row,column,mContext.getString(resId));
	}
	
	/**
	 * 为Panel的item设置内容
	 * @param row   行索引，从0开始
	 * @param column 列索引，从0开始
	 * @param text  要设置的内容
	 */
	public void setContentText(int row,int column, String text){
		if(check(row, column)){
			TextView tv = allowSetContentViews.get(row).get(column);
			if(tv != null){
				tv.setText(text);
			}
		}
	}
	
	private boolean check(int row,int column){
		if(row > rowCount || row < 0 || column > columnCount || column < 0){
			return false;
		}else{
			return true;
		}
	}
}
