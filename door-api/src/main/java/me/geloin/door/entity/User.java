/**
 *
 * @author geloin
 *
 * @date 2013-12-24 下午4:53:07
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
 * user bean of this system
 * 
 * @author geloin
 * 
 * @date 2013-12-24 下午4:53:07
 * 
 */
@Entity
@Table(name = "DOOR_USER")
public class User {
	/**
	 * primary key
	 */
	@Id
	@SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
	private Long id;

	/**
	 * login name
	 */
	@Column(name = "C_LOGIN_NAME")
	private String loginName;

	/**
	 * first name
	 */
	@Column(name = "C_FIRST_NAME")
	private String firstName;

	/**
	 * last name
	 */
	@Column(name = "C_LAST_NAME")
	private String lastName;

	/**
	 * full name
	 */
	@Column(name = "C_FULL_NAME")
	private String fullName;

	/**
	 * login password
	 */
	@Column(name = "C_PASSWORD")
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
