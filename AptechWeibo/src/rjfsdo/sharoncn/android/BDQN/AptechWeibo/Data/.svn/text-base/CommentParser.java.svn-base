package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Comment;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.User;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Util;

import com.google.gson.stream.JsonReader;

/**
 * @see StatusParser
 * @author sharoncn
 * 
 */
public class CommentParser extends BaseParser {
	// private static final String TAG = "CommentParser";
	private final ArrayList<Serializable> comments = new ArrayList<Serializable>();
	private static boolean beSingle = false;
	private static ArrayList<String> commentFields;

	private CommentParser() {
	}

	/**
	 * 并不是单例,只是想在第一次实例化时初始化一次模型成员的变量列表
	 * 
	 * @return
	 */
	public static CommentParser getInstance(boolean... isSingle) {
		if(isSingle.length > 0){
			beSingle = isSingle[0];
		}else{
			beSingle = false;
		}
		if (commentFields == null)
			commentFields = initMethod(Comment.class);
		return new CommentParser();
	}

	@Override
	public ArrayList<Serializable> parse(JsonReader jr) {
		if(beSingle){
			comments.add(parseSingle(jr));
			return comments;
		}
		try {
			jr.setLenient(true);
			jr.beginObject();
			while (jr.hasNext()) {
				String name = jr.nextName();
				if (name.equals("comments")) {
					jr.beginArray();
					while (jr.hasNext()) {
						comments.add(parseSingle(jr));
					}
					jr.endArray();
				} else {
					jr.skipValue();
				}
			}
			jr.endObject();
			return comments;
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
		Comment comment = new Comment();
		parseComment(jr, comment);
		return comment;
	}

	private void parseComment(JsonReader jr, Comment comment) {
		try {
			jr.beginObject();
			while (jr.hasNext()) {
				String name = jr.nextName();
				String value;
				if (name.equals("user")) {
					comment.setUser((User) UserParser.getInstance().parseSingle(jr));
				} else if (name.equals("status")) {
					comment.setStatus(StatusParser.getInstance().parseSingle(jr));
				} else if (name.equals("reply_comment")) {
					jr.skipValue();
				} else {
					value = Util.next(jr).toString();
					Util.constructObject(comment, name, value);
				}
			}
			jr.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
