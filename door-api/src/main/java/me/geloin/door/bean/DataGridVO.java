/**
 *
 * @author geloin
 *
 * @date 2014-1-3 下午3:43:11
 */
package me.geloin.door.bean;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-3 下午3:43:11
 * 
 */
public class DataGridVO {

	/**
	 * current page number, default 1
	 */
	private Integer page = 1;

	/**
	 * page size, default 10
	 */
	private Integer rows = 10;

	/**
	 * sort names
	 */
	private String sidx;

	/**
	 * sort describes
	 */
	private String sord;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

}
