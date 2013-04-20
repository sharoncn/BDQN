package rjfsdo.sharoncn.android.BDQN.AptechWeibo.Data;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.stream.JsonReader;

/**
 * ��������Json����
 * @author sharoncn
 *
 */
public interface JsonParser {
	/**
	 * ����һϵ�ж���
	 * @param json  json�ı�
	 * @return
	 */
	ArrayList<Serializable> parse(String json);
	/**
	 * ���������Ķ���
	 * @param jr  JsonReader
	 * @return
	 */
	Serializable parseSingle(JsonReader jr);
	/**
	 * ����һϵ�ж��� 
	 * @param jr  JsonReader
	 * @return
	 */
	ArrayList<Serializable> parse(JsonReader jr);
}
