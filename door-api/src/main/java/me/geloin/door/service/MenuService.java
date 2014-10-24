/**
 *
 * @author geloin
 *
 * @date 2013-12-23 上午9:41:34
 */
package me.geloin.door.service;

import java.util.List;

import me.geloin.door.bean.CollVO;
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

	/**
	 * 分页获取菜单
	 * 
	 * @author Geloin
	 * @date Oct 22, 2014 5:17:14 PM
	 * @param name
	 * @param url
	 * @param parentId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public CollVO<Menu> findAll(String name, String url, Long parentId,
			Integer pageNum, Integer pageSize);
	
	/**
	 * save or update menu
	 * 
	 * @author Geloin
	 * @date Oct 24, 2014 4:23:43 PM
	 * @param entity
	 * @return
	 */
	public Menu save(Menu entity);

}
