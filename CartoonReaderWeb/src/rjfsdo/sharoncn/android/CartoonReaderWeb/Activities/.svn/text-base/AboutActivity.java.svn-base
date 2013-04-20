package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities;

import android.os.Bundle;
import android.view.View;
import rjfsdo.sharoncn.android.CartoonReaderWeb.R;
import rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Core.BaseActivity;

public class AboutActivity extends BaseActivity {
	public static final String TAG = "AboutActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_about);
		
		add(this);
		
		this.setHeaderBackgroundResources(R.drawable.top_bg_noword);
		this.setHeaderTitleVisibility(View.VISIBLE);
		this.setHeaderTitle(getString(R.string.about_header_title));
		this.setHeaderTextSize(20);
	}

	@Override
	public String getTag() {
		return TAG;
	}
}
