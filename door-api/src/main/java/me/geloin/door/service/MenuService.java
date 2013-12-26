/**
 *
 * @author geloin
 *
 * @date 2013-12-23 上午9:41:34
 */
package me.geloin.door.service;

import java.util.List;

import me.geloin.door.bean.ListDto;
import me.geloin.door.entity.Menu;
import me.geloin.door.utils.PageBean;

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

	/**
	 * find by queries
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-26 下午4:23:03
	 * 
	 * @param name
	 *            name
	 * @param url
	 *            url, controller
	 * @param parentId
	 *            parent menu's id
	 * @param page
	 *            page bean for pageable
	 * @return
	 */
	public ListDto findAll(String name, String url, Long parentId,
			PageBean page);
}
