package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.stream.JsonReader;

/**
 * 用来解析Json数据
 * @author sharoncn
 *
 */
public interface JsonParser {
	/**
	 * 解析一系列对象
	 * @param json  json文本
	 * @return
	 */
	ArrayList<Serializable> parse(String json);
	/**
	 * 解析单个的对象
	 * @param jr  JsonReader
	 * @return
	 */
	Serializable parseSingle(JsonReader jr);
	/**
	 * 解析一系列对象 
	 * @param jr  JsonReader
	 * @return
	 */
	ArrayList<Serializable> parse(JsonReader jr);
}
