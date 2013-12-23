/**
 *
 * @author geloin
 *
 * @date 2013-12-23 上午9:41:34
 */
package me.geloin.door.service;

import java.util.List;

import me.geloin.door.entity.Menu;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-23 上午9:41:34
 * 
 */
public interface MenuService {

	/**
	 * 查询全部，按sort升序排序
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-23 上午9:41:51
	 * 
	 * @return
	 */
	public List<Menu> findAll();
}
