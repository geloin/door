/**
 *
 * @author geloin
 *
 * @date 2014-1-7 下午4:23:53
 */
package me.geloin.door.bean;

import me.geloin.door.entity.Channel;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-7 下午4:23:53
 * 
 */
public class ChannelVO {
	/**
	 * id
	 */
	private Long id;

	/**
	 * name
	 */
	private String name;

	/**
	 * sort num
	 */
	private Long sort;

	/**
	 * parent channel
	 */
	private Channel parent;

	/**
	 * parent channel's id
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

	public Channel getParent() {
		return parent;
	}

	public void setParent(Channel parent) {
		this.parent = parent;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
