/**
 *
 * @author geloin
 *
 * @date 2013-12-26 下午3:48:50
 */
package me.geloin.door.controller;

import org.springframework.util.Assert;

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

	/**
	 * parse params from jqgrid to StringBuilder
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-2 下午1:50:20
	 * 
	 * @param sidx
	 *            sort names
	 * @param sord
	 *            sort describes
	 * @return
	 */
	public StringBuilder parseToOrder(String sidx, String sord) {
		StringBuilder orderBuilder = new StringBuilder();
		String[] sortNames = sidx.split(",");
		Assert.notNull(sord, "排序规则（sortorder）不得为空");
		String[] sortDescs = sord.split(",");
		Assert.isTrue(sortNames.length == sortDescs.length,
				"排序字段（sortname）和排序规则（sortorder）的数量应该一致");
		for (int sortIndex = 0; sortIndex < sortNames.length; sortIndex++) {
			String sortName = sortNames[sortIndex];
			String sortDesc = sortDescs[sortIndex];
			orderBuilder.append(" ").append(sortName).append(" ")
					.append(sortDesc).append(", ");
		}
		String splieStr = ", ";
		if (orderBuilder.toString().endsWith(splieStr)) {
			orderBuilder.delete(orderBuilder.length() - splieStr.length(),
					orderBuilder.length());
		}
		return orderBuilder;
	}
}
