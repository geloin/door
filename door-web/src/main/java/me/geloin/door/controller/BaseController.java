/**
 *
 * @author geloin
 *
 * @date 2013-12-26 下午3:48:50
 */
package me.geloin.door.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-26 下午3:48:50
 * 
 */
public class BaseController {

	private ObjectMapper mapper = new ObjectMapper();
	{
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
	}

	/**
	 * 将对象转化为String对象
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-26 下午4:18:12
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String writeValueAsString(Object obj) throws Exception {
		return mapper.writeValueAsString(obj);
	}
}
