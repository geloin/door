/**
 *
 * @author geloin
 *
 * @date 2014-1-9 下午4:27:35
 */
package me.geloin.door.persistence;

import java.util.List;

import me.geloin.door.entity.Article;
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
 * @date 2014-1-9 下午4:27:35
 * 
 */
@Repository("me.geloin.door.persistence.ArticlePersistence")
public interface ArticlePersistence extends DoorRepository<Article, Long> {

	/**
	 * find by channelId miss ${1} and title like ${2}
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午4:54:20
	 * 
	 * @param channelId
	 * @param title
	 * @param pageable
	 * @return
	 */
	public List<Article> findByChannelIdAndTitleLike(Long channelId,
			String title, Pageable pageable);

	/**
	 * count by channelId miss ${1} and title like ${2}
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午4:55:11
	 * 
	 * @param channelId
	 * @param title
	 * @return
	 */
	public List<Long> countByChannelIdAndTitleLike(Long channelId, String title);

	/**
	 * update all article' sort miss sort plus value
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午12:00:10
	 * 
	 * @param value
	 * @param channelId
	 */
	@Query("update Article set sort = (sort + ?1) where channel.id = ?2")
	@Modifying
	@Transactional
	public void updateAllSortPlus(Long value, Long channelId);

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
	public List<Article> findByIdIn(List<Long> ids, Sort sort);

	/**
	 * find articles by channelId whose sort less than input
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午1:50:19
	 * 
	 * @param sort
	 * @param pageable
	 * @return
	 */
	public List<Article> findByChannelIdAndSortLessThan(Long channelId,
			Long sort, Pageable pageable);

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
	@Query("update Article set sort = ?2 where id = ?1")
	@Modifying
	@Transactional
	public void updateSort(Long id, Long sort);

	/**
	 * find articles by channelId whose sort greater than input
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午2:04:02
	 * 
	 * @param channelId
	 * @param sort
	 * @param pageable
	 * @return
	 */
	public List<Article> findByChannelIdAndSortGreaterThan(Long channelId,
			Long sort, Pageable pageable);

	/**
	 * update all articles' sort miss sort minus value
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午2:14:56
	 * 
	 * @param value
	 * @param channelId
	 */
	@Query("update Article set sort = (sort - ?1) where channel.id = ?2")
	@Modifying
	@Transactional
	public void updateAllSortMinus(Long value, Long channelId);

	/**
	 * find max sort where channel's id miss input
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午2:20:16
	 * 
	 * @param channelId
	 * @return
	 */
	@Query("select max(sort) from Article where channel.id = ?1")
	public Long findMaxSort(Long channelId);
}
