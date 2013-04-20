package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Favorite;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Status;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Tag;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Util;

import com.google.gson.stream.JsonReader;

/**
 * @see StatusParser
 * @author sharoncn
 * 
 */
public class FavoritesParser extends BaseParser {
	private final ArrayList<Serializable> favorites = new ArrayList<Serializable>();
	private static ArrayList<String> favoriteFields;
	private static boolean beSingle = false;
	private FavoritesParser() {
	}

	/**
	 * 并不是单例,只是想在第一次实例化时初始化一次模型成员的变量列表
	 * 
	 * @return
	 */
	public static FavoritesParser getInstance(boolean... isSingle) {
		if(isSingle.length > 0){
			beSingle = isSingle[0];
		}else{
			beSingle = false;
		}
		if (favoriteFields == null)
			favoriteFields = initMethod(Favorite.class);
		return new FavoritesParser();
	}

	@Override
	public ArrayList<Serializable> parse(JsonReader jr) {
		if(beSingle){
			favorites.add(parseSingle(jr));
			return favorites;
		}
		try {
			jr.setLenient(true);
			jr.beginObject();
			while (jr.hasNext()) {
				String name = jr.nextName();
				if (name.equals("favorites")) {
					jr.beginArray();
					while (jr.hasNext()) {
						favorites.add(parseSingle(jr));
					}
					jr.endArray();
				} else {
					jr.skipValue();
				}
			}
			jr.endObject();
			return favorites;
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
		Favorite favorite = new Favorite();
		try {
			jr.beginObject();
			while (jr.hasNext()) {
				String name = jr.nextName();
				String value;
				if (!favoriteFields.contains(name)) {
					jr.skipValue();
					continue;
				}
				if (name.equals("status")) {
					favorite.setStatus((Status) StatusParser.getInstance().parseSingle(jr));
				} else if (name.equals("tags")) {
					ArrayList<Tag> tags = new ArrayList<Tag>();
					ArrayList<Serializable> ss = TagParser.getInstance().parse(jr);
					if (ss != null) {
						int count = ss.size();
						for (int i = 0; i < count; i++) {
							tags.add((Tag) ss.get(i));
						}
						favorite.setTag(tags);
					}
				} else {
					value = Util.next(jr).toString();
					Util.constructObject(favorite, name, value);
				}
			}
			jr.endObject();
			return favorite;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
