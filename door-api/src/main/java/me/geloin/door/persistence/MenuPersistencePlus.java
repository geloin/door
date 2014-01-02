/**
 *
 * @author geloin
 *
 * @date 2013-12-26 下午4:43:55
 */
package me.geloin.door.persistence;

import me.geloin.door.bean.ListDto;
import me.geloin.door.utils.PageBean;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-26 下午4:43:55
 * 
 */
public interface MenuPersistencePlus {

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
	 * @param orderHql
	 *            order hql
	 * @return
	 */
	public ListDto findAll(String name, String url, Long parentId,
			PageBean page, String orderHql);

}
