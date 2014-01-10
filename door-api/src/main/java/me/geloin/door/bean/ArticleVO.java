/**
 *
 * @author geloin
 *
 * @date 2014-1-9 下午5:17:23
 */
package me.geloin.door.bean;

import me.geloin.door.entity.Channel;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-9 下午5:17:23
 * 
 */
public class ArticleVO {
	/**
	 * id
	 */
	private Long id;

	/**
	 * title
	 */
	private String title;

	/**
	 * sort num
	 */
	private Long sort;

	private String content;

	/**
	 * Attachment List Json value
	 */
	private String attachments;

	/**
	 * channel, means this article is this channel's article
	 */
	private Channel channel;

	/**
	 * channel's id
	 */
	private Long channelId;

	/**
	 * channel name
	 */
	private String channelName;

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

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

}
