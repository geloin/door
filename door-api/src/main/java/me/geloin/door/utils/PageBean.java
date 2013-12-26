/**
 * @author Geloin
 */
package me.geloin.door.utils;

import java.io.Serializable;

/**
 * 分页Dto
 * 
 * @author Geloin
 * 
 */
@SuppressWarnings("serial")
public class PageBean implements Serializable {

	/**
	 * 第几页，从1开始，1表示第一页，依次递增
	 */
	private Integer page = 1;

	/**
	 * 每页显示第几条，Integer
	 */
	private Integer pageSize = 10;

	/**
	 * 起始位置
	 */
	private Integer start;

	public Integer getPage() {
		if (page < 1) {
			page = 1;
		}
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStart() {
		if (page > 1) {
			start = (page - 1) * pageSize;
		} else {
			start = 0;
		}
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

}
