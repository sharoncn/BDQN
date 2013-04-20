package rjfsdo.sharoncn.android.BDQN.LeisureLife;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.BaseHeader;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Parser;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils.Util;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * ∞Ô÷˙ΩÁ√Ê
 * 
 * @author sharoncn
 * 
 */
public class HelpActivity extends BaseActivity {
	private static final String TAG = "HelpActivity";
	private LinearLayout container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_act_help);

		container = (LinearLayout) findViewById(R.id.help_content);
		BaseHeader header = (BaseHeader) findViewById(R.id.help_header);
		header.setTitle(getString(R.string.help));

		InputStream is = null;

		try {
			is = getResources().getAssets().open("help.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (is == null) {
			Util.showMsg(this, R.string.get_res_err);
			return;
		}
		
		try {
			Parser parser = new HelpContentParser(is);
			initComponents(parser.parse());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initComponents(List<Object> content) {
		final int count = content.size();
		Log.v(TAG,"hlep content size:" + count);
		for (int i = 0; i < count; i++) {
			HelpContent h = (HelpContent) content.get(i);
			TextView tv_title = new TextView(this);
			LayoutParams params_title = new LayoutParams(
					android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			params_title.setMargins(30, 20, 30, 0);
			tv_title.setLayoutParams(params_title);
			tv_title.setTextColor(Color.BLACK);
			tv_title.setTextSize(16);
			tv_title.setText(h.getName());
			
			TextView tv_content = new TextView(this);
			LayoutParams params_content = new LayoutParams(
					android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			params_content.setMargins(30, 40, 30, 0);
			tv_content.setLayoutParams(params_content);
			tv_content.setTextColor(Color.BLACK);
			tv_content.setTextSize(16);
			tv_content.setText(h.getValue());

			container.addView(tv_title);
			container.addView(tv_content);

			if (i != count - 1) {
				ImageView img = new ImageView(this);
				img.setBackgroundResource(R.color.darker_white);
				LayoutParams params = new LayoutParams(
						android.view.ViewGroup.LayoutParams.MATCH_PARENT, Util.dp2Px(this, 1));
				params.setMargins(15, 0, 10, 0);
				img.setLayoutParams(params);
				container.addView(img);
			}
		}
	}

	class HelpContentParser extends Parser {
		private String tagName, name, value;

		public HelpContentParser(InputStream is) throws XmlPullParserException {
			super(is);
		}

		@Override
		protected void startDocument(XmlPullParser parser) {
			Log.v(TAG,"startDocument");
		}

		@Override
		protected void endDocument(XmlPullParser parser) {
			Log.v(TAG,"endDocument");
		}

		@Override
		protected void startTag(XmlPullParser parser) {
			tagName = parser.getName();
			Log.v(TAG,"startTag - tagName: " + tagName);
			if (tagName.equals("content")) {
				try {
					name = parser.getAttributeValue(null, "name");
					value = "        " + parser.nextText();
					HelpContent h = new HelpContent(name, value);
					this.data.add(h);
					Log.v(TAG,"name=" + name + " value=" + value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		protected void endTag(XmlPullParser parser) {
			Log.v(TAG,"endTag - tagName: " + tagName);
		}
	}

	class HelpContent {
		private String name, value;

		public HelpContent(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
