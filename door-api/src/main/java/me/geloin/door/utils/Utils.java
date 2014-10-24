/**
 * 
 * @author Geloin
 * @date Oct 22, 2014 5:13:34 PM
 */
package me.geloin.door.utils;

import java.util.Collection;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

/**
 * 
 * @author Geloin
 * @date Oct 22, 2014 5:13:34 PM
 */
public final class Utils {

	private static Properties props = null;

	private static ApplicationContext context;

	/**
	 * 判断字符串是否为空.
	 * 
	 * @param str
	 * @return Boolean
	 */
	public static boolean isEmpty(String str) {
		return str == null || ("".equals(str));
	}

	/**
	 * 判断对象是否为空.
	 * 
	 * @param obj
	 * @return Boolean
	 */
	public static boolean isEmpty(Object obj) {
		if (obj instanceof String) {
			return obj == null || ("".equals(obj));
		} else if (obj instanceof Collection) {
			return obj == null || ((Collection<?>) obj).isEmpty();
		} else if (obj instanceof Object[]) {
			return obj == null || ((Object[]) obj).length == 0;
		} else {
			return obj == null;
		}
	}

	/**
	 * 判断字符串不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断对象不为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * 获取对象，如果为空则使用默认值
	 * 
	 * @param obj
	 * @param defualt
	 * @return
	 */
	public static <T> T getValue(T obj, T defualt) {
		if (isEmpty(obj)) {
			return defualt;
		}
		return obj;
	}

	/**
	 * 获取日志对象
	 * 
	 * @param clazz
	 * @return
	 */
	public static Logger getLogger(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

	/**
	 * 获取文件的后缀，
	 * 
	 * @param fileName
	 * @deprecated 请使用getFilenameExtension方法替代
	 * @return
	 */
	public static String getSuffix(String fileName) {
		return getFilenameExtension(fileName);
	}

	/**
	 * 获取文件的后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFilenameExtension(String fileName) {
		return StringUtils.getFilenameExtension(fileName);
	}

	/**
	 * 获取Crystal上下文
	 * 
	 * @return
	 */
	public static ApplicationContext getCrystalContext() {
		return context;
	}

	/**
	 * 设置Crystal上下文
	 * 
	 * @param springContext
	 */
	public static void setCrystalContext(ApplicationContext crystalContext) {
		context = crystalContext;
	}

	/**
	 * 获取系统配置参数
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		if (props != null) {
			return props.getProperty(key);
		}
		return null;
	}

	/**
	 * 获取系统配置参数
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getProperty(String key, String defaultValue) {
		if (props != null) {
			return props.getProperty(key, defaultValue);
		}
		return null;
	}

	/**
	 * 记录Spring初始化的参数表
	 * 
	 * @param p
	 */
	public static void initProperties(Properties p) {
		props = p;
	}
}
