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
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

	/**
	 * update all menus' sort miss sort plus value
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午12:00:10
	 * 
	 * @param value
	 * @param parentId
	 */
	@Query("update Menu set sort = (sort + ?1) where parent.id = ?2")
	@Modifying
	@Transactional
	public void updateAllSortPlus(Long value, Long parentId);

	/**
	 * update all menus' sort miss sort minus value
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午2:14:56
	 * 
	 * @param value
	 * @param parentId
	 */
	@Query("update Menu set sort = (sort - ?1) where parent.id = ?2")
	@Modifying
	@Transactional
	public void updateAllSortMinus(Long value, Long parentId);

	/**
	 * find max sort where parent's id miss input
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午2:20:16
	 * 
	 * @param parentId
	 * @return
	 */
	@Query("select max(sort) from Menu where parent.id = ?1")
	public Long findMaxSort(Long parentId);

	/**
	 * update sort
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午12:02:29
	 * 
	 * @param id
	 * @param sort
	 */
	@Query("update Menu set sort = ?2 where id = ?1")
	@Modifying
	@Transactional
	public void updateSort(Long id, Long sort);

	/**
	 * find Menus by parentId whose sort less than input
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午1:50:19
	 * 
	 * @param sort
	 * @param pageable
	 * @return
	 */
	public List<Menu> findByParentIdAndSortLessThan(Long parentId, Long sort,
			Pageable pageable);

	/**
	 * find Menus by parentId whose sort greater than input
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午2:04:02
	 * 
	 * @param parentId
	 * @param sort
	 * @param pageable
	 * @return
	 */
	public List<Menu> findByParentIdAndSortGreaterThan(Long parentId,
			Long sort, Pageable pageable);

	/**
	 * find by id in input list, the sort
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午1:13:21
	 * 
	 * @param ids
	 * @param sort
	 * @return
	 */
	public List<Menu> findByIdIn(List<Long> ids, Sort sort);
}
