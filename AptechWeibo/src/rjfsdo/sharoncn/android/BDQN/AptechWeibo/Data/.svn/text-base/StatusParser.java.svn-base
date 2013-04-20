package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;

import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Geo;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Status;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.User;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data.Models.Visible;
import rjfsdo.sharoncn.android.BDQN.AptechWeibo.Utils.Util;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

/**
 * 最近发现新浪服务器返回的数据老是发生变化,模型类的成员变量也得跟着变。
 * 前一段时间测试返回的json数据并没有pic_ids，现在又有了，那么模型成员也得跟着变。
 * 但是我根本不知道这个pic_ids是干什么的，也并不需要这个数据。所以我不想跟着变，变下去没个头了。
 * 所以我在初始化每种对象的json解析器时将，将这种模型对象的成员变量获取出来，用于匹配json的name。 如果没有这个成员变量，直接skipValue
 * 
 * @author sharoncn
 * 
 */
public class StatusParser extends BaseParser {
	// private static final String TAG = "StatusParser";
	private final ArrayList<Serializable> statuses = new ArrayList<Serializable>();
	private static ArrayList<String> statusFields;
	private static boolean beSingle = false;
	private StatusParser() {
	}

	/**
	 * 并不是单例,只是想在第一次实例化时初始化一次模型成员的变量列表
	 * 
	 * @return
	 */
	public static StatusParser getInstance(boolean... isSingle) {
		if(isSingle.length > 0){
			beSingle = isSingle[0];
		}else{
			beSingle = false;
		}
		if (statusFields == null)
			statusFields = initMethod(Status.class);
		return new StatusParser();
	}

	@Override
	public ArrayList<Serializable> parse(JsonReader jr) {
		if(beSingle){
			statuses.add(parseSingle(jr));
			return statuses;
		}
		try {
			jr.setLenient(true);
			jr.beginObject();
			while (jr.hasNext()) {
				String name = jr.nextName();
				if (name.equals("statuses")) {
					jr.beginArray();
					while (jr.hasNext()) {
						statuses.add(parseSingle(jr));
					}
					jr.endArray();
				} else {
					jr.skipValue();
				}
			}
			jr.endObject();
			return statuses;
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
	public Status parseSingle(JsonReader jr) {
		try {
			Status status = new Status();
			parseStatus(jr, status);
			return status;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void parseRetweeted(JsonReader jr, Status status) {
		Status _status = new Status();
		parseStatus(jr, _status);
		status.setRetweeted_status(_status);
	}

	private void parseStatus(JsonReader jr, Status status) {
		try {
			jr.beginObject();
			while (jr.hasNext()) {
				String name = jr.nextName();
				String value;
				if (!statusFields.contains(name)) {
					jr.skipValue();
					continue;
				}
				if (name.equals("user")) {
					status.setUser((User) UserParser.getInstance().parseSingle(jr));
				} else if (name.equals("retweeted_status")) {
					parseRetweeted(jr, status);
				} else if (name.equals("visible")) {
					parseVisible(jr, status);
				} else if (name.equals("geo")) {
					parseGeo(jr, status);
				} else if (name.equals("annotations")) {
					parseAnnotations(jr, status);
				} else {
					value = Util.next(jr).toString();
					Util.constructObject(status, name, value);
				}
			}
			jr.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseAnnotations(JsonReader jr, Status status) {
		try {
			jr.beginArray();
			while (jr.hasNext()) {
				jr.skipValue();
			}
			jr.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseGeo(JsonReader jr, Status status) {
		try {
			Geo geo = new Geo();
			if (jr.peek() == JsonToken.NULL) {
				jr.nextNull();
				status.setGeo(null);
				return;
			}
			jr.beginObject();
			while (jr.hasNext()) {
				String name = jr.nextName();
				if (name.equals("coordinates")) {
					double[] coordinates = new double[2];
					jr.beginArray();
					int i = 0;
					while (jr.hasNext()) {
						coordinates[i] = jr.nextDouble();
						i++;
					}
					jr.endArray();
					Util.constructObject(geo, name, coordinates);
				} else {
					String value = Util.next(jr).toString();
					Util.constructObject(geo, name, value);
				}
			}
			jr.endObject();
			status.setGeo(geo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseVisible(JsonReader jr, Status status) {
		try {
			Visible visible = new Visible();
			jr.beginObject();
			while (jr.hasNext()) {
				String name = jr.nextName();
				String value = Util.next(jr).toString();
				Util.constructObject(visible, name, value);
			}
			jr.endObject();
			status.setVisible(visible);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
