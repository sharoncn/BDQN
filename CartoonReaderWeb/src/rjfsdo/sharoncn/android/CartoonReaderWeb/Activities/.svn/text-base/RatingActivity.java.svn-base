package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities;

import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.CartoonReader.BaseActivity;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Data.BookInfoDaoImpl;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Model.BookInfo;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Utils.Utils;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingActivity extends BaseActivity {
	private TextView tv_howmany;
	private RatingBar rate;
	private Button btn_submit,btn_goback,btn_gohome;
	private String bookId = null;
	private BookInfo book;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_rating);
		allActivity.add(this);
		
		tv_howmany = (TextView) findViewById(R.id.rating_tv_howmany);
		rate = (RatingBar) findViewById(R.id.rating_rate);
		btn_submit = (Button) findViewById(R.id.rating_btn_submit);
		btn_goback = (Button) findViewById(R.id.rating_btn_goback);
		btn_gohome = (Button) findViewById(R.id.rating_btn_gohome);
		
		btn_submit.setOnClickListener(btn_click);
		btn_goback.setOnClickListener(btn_click);
		btn_gohome.setOnClickListener(btn_click);
		
		Bundle data = this.getIntent().getExtras();
		if(data.containsKey("bookId")){
			bookId = data.getString("bookId");
			book = BookInfoDaoImpl.getInstance(this).getBookInfo(bookId);
		}
		String gradeNums = book.getGradeNums();
		float grade = Float.parseFloat(book.getGrade());
		rate.setRating(grade);
		tv_howmany.setText(getString(R.string.rate_had) + gradeNums + getString(R.string.rate_graded));
	}
	
	private OnClickListener btn_click = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.rating_btn_submit:
				//提交评分到服务器,文档上没有写明调用方式，这里仅修改本地数据库
				rating();
				btn_submit.setEnabled(false);
				break;
			case R.id.rating_btn_goback:
				finish();
				break;
			case R.id.rating_btn_gohome:
				promptExit(RatingActivity.this);
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		finish();
		return true;
	}

	private void rating() {
		float value = rate.getRating();
		if(BookInfoDaoImpl.getInstance(this).saveRatingValue(bookId, "" + value)){
			Utils.showMsg(this, getString(R.string.rate_success));
		}else{
			Utils.showMsg(this, getString(R.string.rate_fail));
		}
	}
}
