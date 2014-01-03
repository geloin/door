/**
 *
 * @author geloin
 *
 * @date 2013-12-23 上午9:41:34
 */
package me.geloin.door.service;

import java.util.List;

import me.geloin.door.bean.DataGridVO;
import me.geloin.door.bean.ListDto;
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
	 * @param grid
	 *            view object, include page, pageSize, sortNames and
	 *            sortDescribes
	 * @return
	 */
	public ListDto<Menu> findAll(String name, String url, Long parentId,
			DataGridVO grid);

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
	public Menu findOne(Long id);

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
	 * save menu
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-31 下午5:36:05
	 * 
	 * @param menus
	 * @return
	 */
	public Menu save(Menu menu);
}
