/**
 *
 * @author geloin
 *
 * @date 2013-12-26 下午5:03:35
 */
package me.geloin.door.bean;

import java.util.ArrayList;
import java.util.List;

import me.geloin.door.utils.DataUtil;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-26 下午5:03:35
 * 
 */
@SuppressWarnings("rawtypes")
public class ListDto {

	/**
	 * list
	 */
	private List list;

	/**
	 * total page num
	 */
	private Integer totalPageNum = 0;

	/**
	 * total size
	 */
	private Integer totalSize;

	public ListDto(List list, Integer totalSize, Integer pageSize) {
		if (DataUtil.isEmpty(list)) {
			list = new ArrayList();
		}
		this.list = list;
		this.totalSize = totalSize;
		if (totalSize % pageSize == 0) {
			totalPageNum = totalSize / pageSize;
		} else {
			totalPageNum = totalSize / pageSize + 1;
		}
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Integer getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(Integer totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}

}
