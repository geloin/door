/**
 *
 * @author geloin
 *
 * @date 2014-1-7 下午4:07:32
 */
package me.geloin.door.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-7 下午4:07:32
 * 
 */
@Entity
@Table(name = "DOOR_CHANNEL")
public class Channel {

	/**
	 * id
	 */
	@Id
	@SequenceGenerator(name = "CHANNEL_SEQ", sequenceName = "CHANNEL_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHANNEL_SEQ")
	private Long id;

	/**
	 * name
	 */
	@Column(name = "C_NAME")
	private String name;

	/**
	 * sort num
	 */
	@Column(name = "C_SORT")
	private Long sort;

	/**
	 * parent channel
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "C_PARENT_ID")
	private Channel parent;

	/**
	 * articles
	 */
	@OneToMany(mappedBy = "channel", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Article> articles;

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

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

}
