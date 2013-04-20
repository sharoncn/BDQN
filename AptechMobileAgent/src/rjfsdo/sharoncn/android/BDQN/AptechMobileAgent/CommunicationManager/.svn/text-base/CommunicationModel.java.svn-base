package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.CommunicationManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.R;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Models.SmsInfo;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Constants;
import rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils.Util;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.util.Xml;

//提供短信备份还原方法
public class CommunicationModel {
	// 写入到SD的XML文件中，或许可以提供网络备份功能，如果有服务器的话
	public static final String SMS_URI = "content://sms";
	private static final String TAG = "CommunicationModel";
	private static ContentResolver mResolver;
	private static XmlPullParser mParser;
	private static XmlSerializer mSerializer;
	private static CommunicationModel me;
	public static final String ROOTTAG = "Smses";
	public static final String OBJECTTAG = "Sms";
	private static Context mContext;
	static {
		me = new CommunicationModel();
	}

	private CommunicationModel() {
	}

	public static CommunicationModel getInstance(Context context) {
		if (mContext == null)
			mContext = context;
		if (mResolver == null)
			mResolver = context.getContentResolver();
		if (mParser == null)
			mParser = Xml.newPullParser();
		if (mSerializer == null)
			mSerializer = Xml.newSerializer();
		return me;
	}

	/**
	 * 备份短信
	 * 
	 * @return
	 */
	public boolean backupSms() {
		final Cursor cursor = mResolver.query(Uri.parse(SMS_URI), null, null, null, null);
		if (!cursor.moveToFirst()) {
			return false;
		}
		final String filePath = Util.getSdCardPath() + Constants.SMS_FILE;
		File file = new File(filePath);
		if (!createFile(file)) {
			return false;
		}
		FileOutputStream writer;
		try {
			writer = new FileOutputStream(file);
			mSerializer.setOutput(writer, "utf-8");
			mSerializer.startDocument("utf-8", false);
			mSerializer.startTag("", ROOTTAG);
			String[] names = null;
			do {
				Log.v(TAG, "start------------------------");
				if (names == null)
					names = cursor.getColumnNames();
				final int count = names.length;
				mSerializer.startTag("", OBJECTTAG);
				for (int i = 0; i < count; i++) {
					final String name = names[i];
					final String value = cursor.getString(cursor.getColumnIndex(name));
					Log.i(TAG, "columnName:" + name + "  value:" + value);
					mSerializer.startTag("", name);
					mSerializer.text("" + value);
					mSerializer.endTag("", name);
				}
				mSerializer.endTag("", OBJECTTAG);
				Log.v(TAG, "end------------------------");
			} while (cursor.moveToNext());
			mSerializer.endTag("", ROOTTAG);
			mSerializer.endDocument();
			cursor.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean createFile(File file) {
		if (file.exists()) {
			final File rename = new File(file.getPath() + ".old" + new Date().getTime());
			file.renameTo(rename);// 重命名原来的备份
		}
		if (!Util.createFile(file.getPath())) {
			return false;
		}
		return true;
	}

	/**
	 * 恢复短信
	 */
	public void recoverySms() {
		System.out.println("获得备份文件");
		// 恢复对象
		ArrayList<SmsInfo> smses = parseSmsInfo();
		if (smses == null) {
			Util.showMsg(mContext, R.string.err_get_sms_bak);
			return;
		}
		System.out.println("获得备份文件成功");
		final int count = smses.size();
		ContentValues values;
		final Uri uri = Uri.parse(SMS_URI);
		final Field[] fields = SmsInfo.class.getDeclaredFields();
		final Class<?>[] getArgs = new Class<?>[] {};
		final Object[] inArgs = new Object[] {};
		for (int i = 0; i < count; i++) {
			values = new ContentValues();
			values.clear();
			SmsInfo sms = smses.get(i);
			for (Field field : fields) {
				System.out.println("DeclaredFields:" + field.getName());
				final String key = field.getName();
				try {
					final String methodName = getMethodNameFromFieldName(key);
					System.out.println("MethodName:" + methodName);
					final Method method = SmsInfo.class.getMethod(methodName, getArgs);
					String value = "" + method.invoke(sms, inArgs);
					System.out.println("Value:" + value);
					if("null".equals(value)){
						continue;
					}
					values.put(key, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mResolver.insert(uri, values);
			Util.showMsg(mContext, R.string.recovery_sms_success);
		}
	}

	/**
	 * 将fieldName方法名第一个字符大写并加上get拼装get方法
	 * 
	 * @param fieldName
	 * @return
	 */
	private String getMethodNameFromFieldName(String fieldName) {
		final StringBuffer sb = new StringBuffer();
		if (fieldName != null && !"".endsWith(fieldName)) {
			sb.append("get");
			final int count = fieldName.length();
			for (int i = 0; i < count; i++) {
				final String character = fieldName.substring(i, i + 1);
				if (i == 0) {
					sb.append(character.toUpperCase());
				} else {
					sb.append(character);
				}
			}
		}
		return sb.toString();
	}

	private ArrayList<SmsInfo> parseSmsInfo() {
		try {
			final String filePath = Util.getSdCardPath() + Constants.SMS_FILE;
			final File file = new File(filePath);
			if (!file.exists()) {
				return null;
			}
			mParser.setInput(new FileInputStream(file), "utf-8");
			int event = mParser.getEventType();
			final ArrayList<SmsInfo> smses = new ArrayList<SmsInfo>();
			SmsInfo sms = null;
			while (event != XmlPullParser.END_DOCUMENT) {
				System.out.println("EVENT:" + event);
				String name;
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					// 文档开始
					System.out.println("START_DOCUMENT");
					break;
				case XmlPullParser.START_TAG:
					name = mParser.getName();
					System.out.println("Tag Name:" + name);
					if (ROOTTAG.equals(name)) {
						break;
					}
					if (OBJECTTAG.equals(name)) {
						// 构造对象
						sms = new SmsInfo();
					} else {
						// 填充对象
						Object value = mParser.nextText();
						Method method = SmsInfo.class.getMethod(getMethodNameFromFieldName(name),new Class<?>[]{});
						Class<?> returnType = method.getReturnType();
						if(returnType == long.class){
							System.out.println("name:" + name + "   isLong");
							if("null".equals(value)){
								value = 0;
							}else{
								value = Long.parseLong("" + value);
							}
						}
						if(returnType == int.class){
							System.out.println("name:" + name + "   isInt");
							if("null".equals(value)){
								value = 0;
							}else{
								value = Integer.parseInt("" + value);
							}
						}
						Util.constructObject(sms, name, value);
					}
					break;
				case XmlPullParser.END_TAG:
					name = mParser.getName();
					if (OBJECTTAG.equals(name)) {
						smses.add(sms);
					}
					break;
				}
				event = mParser.next();
			}
			return smses;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
