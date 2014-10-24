/**
 * 
 * @author Geloin
 * @date Oct 22, 2014 5:14:55 PM
 */
package me.geloin.door.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Geloin
 * @date Oct 22, 2014 5:14:55 PM
 */
public final class BeanUtils extends org.springframework.beans.BeanUtils {
	/**
	 * 通过无参数实例化目标对象和复制属性，将POJO对象转换成相应的对象
	 * 
	 * @param source
	 *            原对象
	 * @param type
	 *            目标类型
	 * @return 转换后的对象
	 */
	public static <T> T convert(Object source, Class<T> type) {
		if (Utils.isEmpty(source)) {
			try {
				return type.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		T target = instantiate(type);
		copyProperties(source, target);
		return target;
	}

	/**
	 * 通过无参数实例化目标对象和复制属性，将POJO对象转换成相应的对象，只复制可编辑的属性
	 * 
	 * @param source
	 *            原对象
	 * @param type
	 *            目标类型
	 * @param editable
	 *            定义可编辑的属性的类（目标类、其接口或父类）
	 * @return 转换后的对象
	 */
	public static <T> T convert(Object source, Class<T> type, Class<?> editable) {
		if (Utils.isEmpty(source)) {
			try {
				return type.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		T target = instantiate(type);
		copyProperties(source, target, editable);
		return target;
	}

	/**
	 * 通过无参数实例化目标对象和复制属性，将POJO对象转换成相应的对象，可忽略部分属性
	 * 
	 * @param source
	 *            原对象
	 * @param type
	 *            目标类型
	 * @param ignoreProperties
	 *            忽略的属性列表
	 * @return 转换后的对象
	 */
	public static <T> T convert(Object source, Class<T> type,
			String[] ignoreProperties) {
		if (Utils.isEmpty(source)) {
			try {
				return type.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		T target = instantiate(type);
		copyProperties(source, target, ignoreProperties);
		return target;
	}

	/**
	 * 通过无参数实例化目标对象和复制属性，将POJO对象批量转换成指定类型的对象
	 * 
	 * @param sources
	 *            List 原对象列表
	 * @param type
	 *            目标类型
	 * @return List 目标类型对象列表
	 */
	public static <T, E> List<T> convert(List<E> sources, Class<T> type) {
		List<T> items = new ArrayList<T>();
		if (sources == null) {
			return items;
		}
		Iterator<E> iter = sources.iterator();
		while (iter.hasNext()) {
			items.add(convert(iter.next(), type));
		}
		return items;
	}

	/**
	 * 通过无参数实例化目标对象和复制属性，将POJO对象批量转换成指定类型的对象
	 * 
	 * @param sources
	 *            List 原对象列表
	 * @param type
	 *            目标类型
	 * @param editable
	 *            定义可编辑的属性的类（目标类、其接口或父类）
	 * @return List 目标类型对象列表
	 */
	public static <T, E> List<T> convert(List<E> sources, Class<T> type,
			Class<?> editable) {
		List<T> items = new ArrayList<T>();
		if (sources == null) {
			return items;
		}
		Iterator<E> iter = sources.iterator();
		while (iter.hasNext()) {
			items.add(convert(iter.next(), type, editable));
		}
		return items;
	}

	/**
	 * 通过无参数实例化目标对象和复制属性，将POJO对象批量转换成指定类型的对象
	 * 
	 * @param sources
	 *            List 原对象列表
	 * @param type
	 *            目标类型
	 * @param ignoreProperties
	 *            忽略的属性列表
	 * @return List 目标类型对象列表
	 */
	public static <T, E> List<T> convert(List<E> sources, Class<T> type,
			Class<?> editable, String[] ignoreProperties) {
		List<T> items = new ArrayList<T>();
		if (sources == null) {
			return items;
		}
		Iterator<E> iter = sources.iterator();
		while (iter.hasNext()) {
			items.add(convert(iter.next(), type, ignoreProperties));
		}
		return items;
	}
}
