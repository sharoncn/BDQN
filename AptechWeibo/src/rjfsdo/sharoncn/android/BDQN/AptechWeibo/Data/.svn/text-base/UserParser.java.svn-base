package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Status;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.User;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Util;

import com.google.gson.stream.JsonReader;

/**
 * @see StatusParser
 * @author sharoncn
 * 
 */
public class UserParser extends BaseParser {
	// private static final String TAG = "UserParser";
	private final ArrayList<Serializable> users = new ArrayList<Serializable>();
	private static ArrayList<String> userFields;

	private UserParser() {
	}

	/**
	 * 并不是单例,只是想在第一次实例化时初始化一次模型成员的变量列表
	 * 
	 * @return
	 */
	public static UserParser getInstance(boolean... isSingle) {
		if (userFields == null)
			userFields = initMethod(User.class);
		return new UserParser();
	}

	@Override
	public ArrayList<Serializable> parse(JsonReader jr) {
		jr.setLenient(true);
		users.add(parseSingle(jr));
		return users;
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
		User user;
		try {
			jr.beginObject();
			user = new User();
			while (jr.hasNext()) {
				String name = jr.nextName();
				if (name.equals("status")) {
					Util.constructObject(user, Status.class.getSimpleName(), StatusParser.getInstance().parseSingle(jr));
					// parseStatuse(user, jr);
				} else {
					Util.constructObject(user, name, Util.next(jr).toString());
				}
			}
			jr.endObject();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
