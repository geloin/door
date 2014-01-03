/**
 *
 * @author geloin
 *
 * @date 2013-12-25 上午10:30:51
 */
package me.geloin.door.utils;

import java.security.MessageDigest;
import java.util.Collection;
import java.util.Map;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-25 上午10:30:51
 * 
 */
public class DataUtil {
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || ("".equals(str));
	}

	/**
	 * 判断字符串是否不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if (obj instanceof String) {
			return obj == null || ("".equals(obj));
		} else if (obj instanceof Collection) {
			return obj == null || ((Collection) obj).isEmpty();
		} else if (obj instanceof Map) {
			return obj == null || ((Map) obj).isEmpty();
		} else if (obj instanceof Object[]) {
			return obj == null || ((Object[]) obj).length == 0;
		} else {
			return obj == null;
		}
	}

	/**
	 * 判断对象是否不为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * 首字母大写
	 * 
	 * @author Geloin
	 * @param s
	 * @return
	 */
	public static String firstLetterUpcase(String s) {

		int point = s.codePointAt(0);
		if (point >= 65 && point <= 90) {
			return s;
		}

		return s.replaceFirst(s.charAt(0) + "", ((char) (s.charAt(0) - 32))
				+ "");
	}

	/**
	 * 首字母小写
	 * 
	 * @author Geloin
	 * @param s
	 * @return
	 */
	public static String firstLetterLowercase(String s) {

		int point = s.codePointAt(0);
		if (point >= 97 && point <= 122) {
			return s;
		}

		return s.replaceFirst(s.charAt(0) + "", ((char) (s.charAt(0) + 32))
				+ "");
	}

	/**
	 * digest String
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午4:18:43
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String digest(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA");
			return new String(digest.digest(password.getBytes("UTF-8")));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
