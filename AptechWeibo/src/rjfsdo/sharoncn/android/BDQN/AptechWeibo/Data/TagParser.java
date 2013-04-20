package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Tag;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Util;

import com.google.gson.stream.JsonReader;

/**
 * @see StatusParser
 * @author sharoncn
 *
 */
public class TagParser extends BaseParser {
	private final ArrayList<Serializable> tags = new ArrayList<Serializable>();
	private static ArrayList<String> tagFields;
	private static boolean beSingle = false;
	private TagParser() {
	}

	/**
	 * 并不是单例,只是想在第一次实例化时初始化一次模型成员的变量列表
	 * 
	 * @return
	 */
	public static TagParser getInstance(boolean... isSingle) {
		if(isSingle.length > 0){
			beSingle = isSingle[0];
		}else{
			beSingle = false;
		}
		if (tagFields == null)
			tagFields = initMethod(Tag.class);
		return new TagParser();
	}

	@Override
	public ArrayList<Serializable> parse(JsonReader jr) {
		if(beSingle){
			tags.add(parseSingle(jr));
			return tags;
		}
		jr.setLenient(true);
		try {
			jr.beginObject();
			while (jr.hasNext()) {
				String name = jr.nextName();
				if (name.equals("tags")) {
					jr.beginArray();
					while (jr.hasNext()) {
						tags.add(parseSingle(jr));
					}
					jr.endArray();
				} else {
					jr.skipValue();
				}
			}
			jr.endObject();
			return tags;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Serializable> parse(String json) {
		Util.dumpJsonString(json);
		try {
			final JsonReader jr = new JsonReader(new StringReader(json));
			return parse(jr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Serializable parseSingle(JsonReader jr) {
		Tag tag = new Tag();
		try {
			jr.beginObject();
			while (jr.hasNext()) {
				String name = jr.nextName();
				String value;
				if (!tagFields.contains(name)) {
					jr.skipValue();
					continue;
				}

				value = Util.next(jr).toString();
				Util.constructObject(tag, name, value);
			}
			jr.endObject();
			return tag;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
