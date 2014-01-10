/**
 *
 * @author geloin
 *
 * @date 2014-1-9 下午4:27:10
 */
package me.geloin.door.service;

import java.util.List;

import me.geloin.door.bean.DataGridVO;
import me.geloin.door.bean.ListDto;
import me.geloin.door.entity.Article;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-9 下午4:27:10
 * 
 */
public interface ArticleService {

	/**
	 * query article, conditions:<br />
	 * channelId = ${1} title like ${2}
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午4:52:02
	 * 
	 * @param channelId
	 * @param title
	 * @param grid
	 * @return
	 */
	public ListDto<Article> findAll(Long channelId, String title,
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
	public Article findOne(Long id);

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
	 * save article
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-31 下午5:36:05
	 * 
	 * @param article
	 * @return
	 */
	public Article save(Article article);

	/**
	 * update sort
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午2:50:34
	 * 
	 * @param ids
	 * @param optId
	 * @param channelId
	 */
	public void updateSort(List<Long> ids, Integer optId, Long channelId);

	/**
	 * reload sort, set sort start from 1 and then next
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午3:17:24
	 * 
	 * @param channelId
	 */
	public void reloadSort(Long channelId);
}
