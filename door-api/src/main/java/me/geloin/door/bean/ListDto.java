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
@SuppressWarnings("rawtypes")
public class ListDto {
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
	private List rows;

	/**
	 * exception message
	 */
	private String userdata;

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

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public String getUserdata() {
		return userdata;
	}

	public void setUserdata(String userdata) {
		this.userdata = userdata;
	}

}
