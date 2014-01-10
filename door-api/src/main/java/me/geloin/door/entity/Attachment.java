/**
 *
 * @author geloin
 *
 * @date 2014-1-9 下午4:07:37
 */
package me.geloin.door.entity;

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
 * @date 2014-1-9 下午4:07:37
 * 
 */
@Entity
@Table(name = "DOOR_ATTACHMENT")
public class Attachment {

	/**
	 * id
	 */
	@Id
	@SequenceGenerator(name = "ATTACHMENT_SEQ", sequenceName = "ATTACHMENT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTACHMENT_SEQ")
	private Long id;

	/**
	 * name
	 */
	@Column(name = "C_NAME")
	private String name;

	/**
	 * size
	 */
	@Column(name = "C_SIZE")
	private Long size;

	/**
	 * mime type
	 */
	@Column(name = "C_MIME_TYPE")
	private String mimeType;

	/**
	 * store path
	 */
	@Column(name = "C_PATH")
	private String path;

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

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
