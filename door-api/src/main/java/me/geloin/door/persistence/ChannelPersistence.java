/**
 *
 * @author geloin
 *
 * @date 2014-1-7 下午4:15:06
 */
package me.geloin.door.persistence;

import java.util.List;

import me.geloin.door.entity.Channel;
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
 * @date 2014-1-7 下午4:15:06
 * 
 */
@Repository("me.geloin.door.persistence.ChannelPersistence")
public interface ChannelPersistence extends DoorRepository<Channel, Long> {

	/**
	 * query channels<br />
	 * conditions:<br />
	 * name like {0}<br />
	 * parentId = {2}
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-7 下午4:18:15
	 * 
	 * @param name
	 * @param parentId
	 * @param pageable
	 * @return
	 */
	public List<Channel> findByNameLikeAndParentId(String name, Long parentId,
			Pageable pageable);

	/**
	 * query channels<br />
	 * conditions:<br />
	 * name like {0}<br />
	 * parentId is null
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午3:40:50
	 * 
	 * @param name
	 * @param pageable
	 * @return
	 */
	public List<Channel> findByNameLikeAndParentIsNull(String name,
			Pageable pageable);

	/**
	 * count channels<br />
	 * conditions:<br />
	 * name like {0}<br />
	 * parentId = {2}
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-7 下午4:20:24
	 * 
	 * @param name
	 * @param parentId
	 * @return
	 */
	public List<Long> countByNameLikeAndParentId(String name, Long parentId);

	/**
	 * count channels<br />
	 * conditions:<br />
	 * name like {0}<br />
	 * parent is null
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-7 下午4:20:24
	 * 
	 * @param name
	 * @return
	 */
	public List<Long> countByNameLikeAndParentIsNull(String name);

	/**
	 * update all channel' sort miss sort plus value
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午12:00:10
	 * 
	 * @param value
	 * @param parentId
	 */
	@Query("update Channel set sort = (sort + ?1) where parent.id = ?2")
	@Modifying
	@Transactional
	public void updateAllSortPlus(Long value, Long parentId);

	/**
	 * update all channel' sort miss sort plus value
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午12:00:10
	 * 
	 * @param value
	 */
	@Query("update Channel set sort = (sort + ?1) where parent is null")
	@Modifying
	@Transactional
	public void updateAllSortPlusByNullParent(Long value);

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
	public List<Channel> findByIdIn(List<Long> ids, Sort sort);

	/**
	 * update all Channels' sort miss sort minus value
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午2:14:56
	 * 
	 * @param value
	 * @param parentId
	 */
	@Query("update Channel set sort = (sort - ?1) where parent.id = ?2")
	@Modifying
	@Transactional
	public void updateAllSortMinus(Long value, Long parentId);

	/**
	 * update all Channels' sort miss sort minus value
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午2:14:56
	 * 
	 * @param value
	 */
	@Query("update Channel set sort = (sort - ?1) where parent is null")
	@Modifying
	@Transactional
	public void updateAllSortMinusByNullParent(Long value);

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
	@Query("select max(sort) from Channel where parent.id = ?1")
	public Long findMaxSort(Long parentId);

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
	@Query("select max(sort) from Channel where parent is null")
	public Long findMaxSortByNullParent();

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
	@Query("update Channel set sort = ?2 where id = ?1")
	@Modifying
	@Transactional
	public void updateSort(Long id, Long sort);

	/**
	 * find Channels by parentId whose sort less than input
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午1:50:19
	 * 
	 * @param sort
	 * @param pageable
	 * @return
	 */
	public List<Channel> findByParentIdAndSortLessThan(Long parentId,
			Long sort, Pageable pageable);

	/**
	 * find Channels by parentId whose sort less than input
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午1:50:19
	 * 
	 * @param sort
	 * @param pageable
	 * @return
	 */
	public List<Channel> findByParentIsNullAndSortLessThan(Long sort,
			Pageable pageable);

	/**
	 * find Channels by parentId whose sort greater than input
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
	public List<Channel> findByParentIdAndSortGreaterThan(Long parentId,
			Long sort, Pageable pageable);

	/**
	 * find Channels by parentId whose sort greater than input
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
	public List<Channel> findByParentIsNullAndSortGreaterThan(Long sort,
			Pageable pageable);
}
