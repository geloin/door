/**
 *
 * @author geloin
 *
 * @date 2014-1-3 下午4:10:40
 */
package me.geloin.door.bean;

import me.geloin.door.utils.DataUtil;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-3 下午4:10:40
 * 
 */
public class UserVO {
	/**
	 * primary key
	 */
	private Long id;

	/**
	 * login name
	 */
	private String loginName;

	/**
	 * first name
	 */
	private String firstName;

	/**
	 * last name
	 */
	private String lastName;

	/**
	 * full name
	 */
	private String fullName;

	/**
	 * login password
	 */
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
		if (DataUtil.isEmpty(fullName)) {
			fullName = new StringBuilder().append(this.firstName).append(" ")
					.append(this.lastName).toString();
		}
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
