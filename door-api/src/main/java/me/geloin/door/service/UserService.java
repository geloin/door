/**
 *
 * @author geloin
 *
 * @date 2014-1-3 下午1:56:50
 */
package me.geloin.door.service;

import java.util.List;

import me.geloin.door.bean.DataGridVO;
import me.geloin.door.bean.ListDto;
import me.geloin.door.entity.User;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-3 下午1:56:50
 * 
 */
public interface UserService {

	/**
	 * find All
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午2:00:17
	 * 
	 * @param loginName
	 * @param firstName
	 * @param lastName
	 * @param grid
	 * @return
	 */
	public ListDto<User> findAll(String loginName, String firstName,
			String lastName, DataGridVO grid);

	/**
	 * find by id
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-31 下午1:17:23
	 * 
	 * @param id
	 * @return
	 */
	public User findOne(Long id);

	/**
	 * delete in batch
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-31 下午4:05:25
	 * 
	 * @param ids
	 */
	public void delete(List<Long> ids);

	/**
	 * save user
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-31 下午5:36:05
	 * 
	 * @param Users
	 * @return
	 */
	public User save(User user);
}
