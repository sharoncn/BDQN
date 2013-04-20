package rjfsdo.sharoncn.android.BDQN.LeisureLife.Net;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @author sharoncn
 * 
 */
public class URLParam {
	private HashMap<String, String> params = new HashMap<String, String>();

	public URLParam() {
		params.clear();
	}

	public void addParam(String name, String value) {
		params.put(name, value);
	}

	public void addParam(String name, int value) {
		params.put(name, "" + value);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Set<String> keys = params.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (sb.length() > 0) {
				sb.append("&" + key + "=" + params.get(key));
			} else {
				sb.append("?" + key + "=" + params.get(key));
			}
		}
		return sb.toString();
	}

	public void clear() {
		params.clear();
	}
}
