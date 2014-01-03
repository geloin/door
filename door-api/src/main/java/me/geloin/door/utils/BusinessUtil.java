/**
 *
 * @author geloin
 *
 * @date 2014-1-3 下午3:24:25
 */
package me.geloin.door.utils;

import me.geloin.door.bean.DataGridVO;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.Assert;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-3 下午3:24:25
 * 
 */
public final class BusinessUtil {

	private BusinessUtil() {
	}

	/**
	 * parse value to value for sql's like
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午3:25:32
	 * 
	 * @param value
	 * @return
	 */
	public static String generateLikeValue(String value) {
		if (DataUtil.isEmpty(value)) {
			value = "%%";
		} else {
			value = new StringBuilder("%").append(value).append("%").toString();
		}
		return value;
	}

	/**
	 * parse sortNames and sortDescs to Sort for Spring Data Jpa
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午3:27:11
	 * 
	 * @param sortNames
	 * @param sortDescs
	 * @return
	 * @throws Exception
	 */
	public static Sort generateSort(String sortNames, String sortDescs) {
		Sort.Order[] orders = null;
		Sort sort = null;
		if (DataUtil.isNotEmpty(sortNames)) {
			String[] sortNameArr = sortNames.split(",");
			Assert.notNull(sortDescs, "排序规则（sortorder）不得为空");
			String[] sortDescArr = sortDescs.split(",");
			Assert.isTrue(sortNameArr.length == sortDescArr.length,
					"排序字段（sortname）和排序规则（sortorder）的数量应该一致");

			orders = new Sort.Order[sortNameArr.length];

			for (int sortIndex = 0; sortIndex < sortNameArr.length; sortIndex++) {
				String sortName = sortNameArr[sortIndex];
				String sortDesc = sortDescArr[sortIndex];
				Sort.Order order = new Sort.Order(
						Direction.fromString(sortDesc), sortName);
				orders[sortIndex] = order;
			}
			sort = new Sort(orders);
		}
		return sort;
	}

	/**
	 * parse page, pageSize, sortNames and sortDescs to Pageable
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午3:34:26
	 * 
	 * @param grid
	 * @return
	 */
	public static Pageable generatePageable(DataGridVO grid) {
		Integer page = grid.getPage();
		Integer pageSize = grid.getRows();
		String sortNames = grid.getSidx();
		String sortDescs = grid.getSord();
		Sort sort = BusinessUtil.generateSort(sortNames, sortDescs);

		Integer pageNum = page - 1;
		Pageable pageRequest = new PageRequest(pageNum, pageSize, sort);
		return pageRequest;
	}

}
