/**
 *
 * @author geloin
 *
 * @date 2013-12-23 上午9:30:17
 */
package me.geloin.door.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-23 上午9:30:17
 * 
 */
@Entity
@Table(name = "DOOR_MENU")
public class Menu {

	/**
	 * 主键
	 */
	@Id
	@SequenceGenerator(name = "MENU_SEQ", sequenceName = "MENU_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENU_SEQ")
	private Long id;

	/**
	 * 名称
	 */
	@Column(name = "C_NAME")
	private String name;

	/**
	 * 地址
	 */
	@Column(name = "C_URL")
	private String url;

	/**
	 * 排序
	 */
	@Column(name = "C_SORT")
	private Long sort;

	/**
	 * 父节点
	 */
	@Column(name = "C_PARENT_ID")
	private Long parentId;

	@Column(name = "C_CREATE_TIME", columnDefinition = "TIMESTAMP(6)")
	private Date createTime;

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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
