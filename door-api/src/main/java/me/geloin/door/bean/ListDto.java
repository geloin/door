/**
 *
 * @author geloin
 *
 * @date 2014-1-2 下午1:37:06
 */
package me.geloin.door.bean;

import java.util.List;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-2 下午1:37:06
 * 
 */
public class ListDto<T> {
	/**
	 * current page
	 */
	private Integer page;

	/**
	 * total page
	 */
	private Integer total;

	/**
	 * total num
	 */
	private Long records;

	/**
	 * current rows
	 */
	private List<T> rows;

	/**
	 * exception message
	 */
	private String userdata;

	public ListDto() {

	}

	public ListDto(Integer page, Integer pageSize, Long count, List<T> list) {
		this.page = page;
		this.records = count;
		this.rows = list;

		Integer total = 0;
		if (records % pageSize == 0) {
			total = (int) (records / pageSize);
		} else {
			total = (int) (records / pageSize) + 1;
		}
		this.total = total;

	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Long getRecords() {
		return records;
	}

	public void setRecords(Long records) {
		this.records = records;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public String getUserdata() {
		return userdata;
	}

	public void setUserdata(String userdata) {
		this.userdata = userdata;
	}

}
