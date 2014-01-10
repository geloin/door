/**
 *
 * @author geloin
 *
 * @date 2014-1-9 下午4:04:52
 */
package me.geloin.door.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-9 下午4:04:52
 * 
 */
@Entity
@Table(name = "DOOR_ARTICLE")
public class Article {

	/**
	 * id
	 */
	@Id
	@SequenceGenerator(name = "ARTICLE_SEQ", sequenceName = "ARTICLE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARTICLE_SEQ")
	private Long id;

	/**
	 * title
	 */
	@Column(name = "C_TITLE", length = 1000)
	private String title;

	/**
	 * sort num
	 */
	@Column(name = "C_SORT")
	private Long sort;

	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "C_CONTENT", columnDefinition = "CLOB", nullable = true)
	private String content;

	/**
	 * Attachment List Json value
	 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "C_ATTACHMENTS", columnDefinition = "CLOB", nullable = true)
	private String attachments;

	/**
	 * channel, means this article is this channel's article
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "C_CHANNEL_ID")
	private Channel channel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

}
