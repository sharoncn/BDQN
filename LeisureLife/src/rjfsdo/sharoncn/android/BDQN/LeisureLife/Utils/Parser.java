package rjfsdo.sharoncn.android.BDQN.LeisureLife.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

/**
 * 解析XML的解析器
 * @author sharoncn
 *
 */
public abstract class Parser {
	protected XmlPullParser parser;
	protected List<Object> data;
	
	public Parser(String xmlPath) throws FileNotFoundException, XmlPullParserException {
		this(new FileInputStream(new File(xmlPath)));
	}
	
	public Parser(InputStream is) throws XmlPullParserException  {
		data = new ArrayList<Object>();
		XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
		parser = parserFactory.newPullParser();
		parser.setInput(is, "utf-8");
	}
	
	public final List<Object> parse() throws XmlPullParserException, IOException{
		int eventType = parser.getEventType();
		while(eventType != XmlPullParser.END_DOCUMENT){
			Log.v("Parser","loop,eventType:" + eventType);
			switch(eventType){
			case XmlPullParser.START_DOCUMENT:
				startDocument(parser);
				break;
			case XmlPullParser.START_TAG:
				startTag(parser);
				break;
			case XmlPullParser.END_TAG:
				endTag(parser);
				break;
			}
			eventType = parser.next();
		}
		endDocument(parser);
		return data;
	}
	
	//为了方便接口中直接传递了XmlPullParser，本不应该直接这么传递的。
	//这么传递有潜在的逻辑问题。
	protected abstract void startDocument(XmlPullParser parser);
	protected abstract void endDocument(XmlPullParser parser);
	protected abstract void startTag(XmlPullParser parser);
	protected abstract void endTag(XmlPullParser parser);
}
