package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Uid;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

/**
 * @see StatusParser
 * @author sharoncn
 * 
 */
public class UserIdParser extends BaseParser {
	private static ArrayList<String> userIdFields;

	private UserIdParser() {
	}

	/**
	 * 并不是单例,只是想在第一次实例化时初始化一次模型成员的变量列表
	 * 
	 * @return
	 */
	public static UserIdParser getInstance(boolean... isSingle) {
		if (userIdFields == null)
			userIdFields = initMethod(Uid.class);
		return new UserIdParser();
	}

	@Override
	public ArrayList<Serializable> parse(JsonReader jr) {
		ArrayList<Serializable> uid = new ArrayList<Serializable>();
		uid.add(parseSingle(jr));
		return uid;
	}

	@Override
	public ArrayList<Serializable> parse(String json) {
		JsonReader jr = new JsonReader(new StringReader(json));
		return parse(jr);
	}

	@Override
	public Serializable parseSingle(JsonReader jr) {
		Gson gson = new Gson();
		return gson.fromJson(jr, new TypeToken<Uid>() {
		}.getType());
	}

}
