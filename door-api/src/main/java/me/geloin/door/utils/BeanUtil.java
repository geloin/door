/**
 * 
 */
package me.geloin.door.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Geloin
 * 
 */
public class BeanUtil {

	/**
	 * 将fromObjs的各属性内容合并成一个，放置到一个toClass对象里面
	 * 
	 * @param toClass
	 *            结果对象类型
	 * @param fromObjs
	 *            数据来源
	 * @return toClass对象
	 */
	@SuppressWarnings({ "rawtypes" })
	public static <T> T convertBean(Class<T> toClass, Object... fromObjs) {
		if (DataUtil.isEmpty(fromObjs)) {
			return null;
		} else {
			try {
				T result = toClass.newInstance();

				// 将fromObj的值转化为需要的结果
				List<String> accessType = new ArrayList<String>();
				accessType.add("java.lang.Integer");
				accessType.add("java.lang.String");
				accessType.add("java.lang.Boolean");
				accessType.add("java.lang.Double");
				accessType.add("java.util.Date");
				accessType.add("java.lang.Long");

				for (int i = 0; i < fromObjs.length; i++) {
					Object fromObj = fromObjs[i];
					Class fromClass = fromObj.getClass();
					Method[] fromMethods = fromClass.getMethods();
					for (Method method : fromMethods) {
						String name = method.getName().trim();
						Class returnType = method.getReturnType();
						String type = returnType.getName();
						if (name.startsWith("get") && accessType.contains(type)
								&& !name.equals("getProperties")) {
							name = name.substring(3);
							name = DataUtil.firstLetterLowercase(name);

							Object value = method.invoke(fromObj);

							if (null != value) {
								name = "set" + DataUtil.firstLetterUpcase(name);

								try {
									Method newM = toClass.getMethod(name,
											returnType);
									newM.invoke(result, value);
								} catch (Exception e) {
									// System.err.println(toClass.getName()
									// + "不存在方法：" + name);
								}
							}

						}
					}
				}

				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 将fromList的所有对象转化成toClass的对象，返回toClass的列表
	 * 
	 * @param fromList
	 *            来源
	 * @param toClass
	 *            结果的类型
	 * @return 返回指定类型的对象列表
	 */
	public static <T, E> List<T> convertBeans(List<E> fromList, Class<T> toClass) {
		List<T> list = null;
		if (null != fromList && fromList.size() > 0) {
			list = new ArrayList<T>();
			for (E e : fromList) {
				list.add(convertBean(toClass, e));
			}
		}
		return list;

	}

	/**
	 * 将一个map集合里面的值赋值到一个bean中对应的属性上
	 * 
	 * @param map
	 *            需要转换的集合
	 * @param toClass
	 *            需要赋值的Bean的类型
	 * @return 赋值后的Bean
	 */
	@SuppressWarnings("unchecked")
	public static <T> T converMapToBean(Map<String, Object> infoMap,
			Class<T> toClass) {

		T df;
		try {
			df = (T) Class.forName(toClass.getName()).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("您的类传入的有问题");
		}
		// 获得t下面的所有方法
		Set<String> keys = infoMap.keySet();
		Method[] methods = df.getClass().getMethods();
		Map<String, Method> map = new HashMap<String, Method>();

		// 遍历所有获得的方法，取出set方法，取出set方法后的字段，将其和方法封装到map中
		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				String name = method.getName().substring(3);
				name = DataUtil.firstLetterLowercase(name);
				map.put(name, method);
			}

		}

		// 遍历传入的参数，如果有不符合规则来保存的，我们直接抛出异常
		for (String key : keys) {

			// 如果此key在我们的实体类中存在则添加其到方法中
			if (map.containsKey(key)) {
				Method method = map.get(key);
				try {
					method.invoke(df, infoMap.get(key));
				} catch (Exception e) {
					e.printStackTrace();
					// throw new GcrException("反射获得参数类型异常");
				}

			} else {
				// throw new GcrException("业务异常");
			}
		}

		return df;
	}
}
