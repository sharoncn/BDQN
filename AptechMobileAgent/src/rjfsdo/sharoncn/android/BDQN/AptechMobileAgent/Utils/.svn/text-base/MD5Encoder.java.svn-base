package rjfsdo.sharoncn.android.BDQN.AptechMobileAgent.Utils;

import java.security.MessageDigest;

public class MD5Encoder {
	private static MessageDigest md;

	public static String encode(String text) {
		try {
			if (md == null)
				md = MessageDigest.getInstance("MD5");
			return encrypt(text);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private static String encrypt(String words){
		md.update(words.getBytes());
		final byte[] result = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < result.length; offset++) {
			i = result[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}
}
