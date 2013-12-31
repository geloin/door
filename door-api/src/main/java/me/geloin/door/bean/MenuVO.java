/**
 *
 * @author geloin
 *
 * @date 2013-12-31 下午5:28:26
 */
package me.geloin.door.bean;

import me.geloin.door.entity.Menu;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-31 下午5:28:26
 * 
 */
public class MenuVO {
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 地址
	 */
	private String url;

	/**
	 * 排序
	 */
	private Long sort;

	/**
	 * 父节点
	 */
	private Menu parent;

	/**
	 * parent id
	 */
	private Long parentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
