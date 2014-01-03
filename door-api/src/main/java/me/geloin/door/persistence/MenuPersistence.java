/**
 *
 * @author geloin
 *
 * @date 2013-12-23 上午9:44:12
 */
package me.geloin.door.persistence;

import java.util.List;

import me.geloin.door.entity.Menu;
import me.geloin.door.repository.DoorRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-23 上午9:44:12
 * 
 */
@Repository("me.geloin.door.persistence.MenuPersistence")
public interface MenuPersistence extends DoorRepository<Menu, Long> {

	/**
	 * find by parentId, and name like input, and url like input
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午2:38:43
	 * 
	 * @param parentId
	 * @param name
	 * @param url
	 * @param page
	 * @return
	 */
	public List<Menu> findByParentIdAndNameLikeAndUrlLike(Long parentId,
			String name, String url, Pageable page);

	/**
	 * count by parentId, and name like input, and url like input
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午2:38:43
	 * 
	 * @param parentId
	 * @param name
	 * @param url
	 * @param page
	 * @return
	 */
	public List<Long> countByParentIdAndNameLikeAndUrlLike(Long parentId,
			String name, String url, Pageable page);
}
