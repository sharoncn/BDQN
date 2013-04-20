package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.util.Xml;

/**
 * SPN信息,包括:SPN代码,名称,查流量短信号码,查流量发送的内容
 * @author sharoncn
 *
 */
public class SpnProvider {
	private static SpnProvider me;
	private static Context mContext;
	private static ArrayList<SpnInfo> spns;
	
	static {
		me = new SpnProvider();
	}

	private SpnProvider() {
	}

	public static SpnProvider getInstance(Context context) {
		mContext = context;
		if(spns == null){
			initSPN();
		}
		return me;
	}
	
	private static void initSPN() {
		try {
			spns = new ArrayList<SpnProvider.SpnInfo>();
			final InputStream is = mContext.getAssets().open("spn.xml");
			final XmlPullParser parser = Xml.newPullParser();
			parser.setInput(is, "utf-8");
			int event = parser.getEventType();
			SpnInfo spn = null;
			while(XmlPullParser.END_DOCUMENT != event){
				final String name = parser.getName();
				switch(event){
				case XmlPullParser.START_TAG:
					if("CODE".equals(name)){
						spn.setCode(parser.nextText());
					}else if("NAME".equals(name)){
						spn.setName(parser.nextText());
					}else if("NUMBER".equals(name)){
						spn.setNumber(parser.nextText());
					}else if("TEXT".equals(name)){
						spn.setText(parser.nextText());
					}else if("SPN".equals(name)){
						spn = new SpnInfo();
					}
					break;
				case XmlPullParser.END_TAG:
					if("SPN".equals(name)){
						spns.add(spn);
					}
					break;
				}
				event = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
			spns = null;
		}
	}

	public List<SpnInfo> getAll(){
		return spns;
	}
	
	public SpnInfo getSpnInfoByCode(String code){
		SpnInfo info = null;
		final int count = spns.size();
		for(int i = 0; i < count; i++){
			SpnInfo tmp = spns.get(i);
			if(tmp.getCode().equals(code)){
				info = tmp;
				break;
			}
		}
		return info;
	}
	

	public static class SpnInfo {
		private String code, name, text, number;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}
	}
}
