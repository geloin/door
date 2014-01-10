/**
 *
 * @author geloin
 *
 * @date 2014-1-3 下午1:56:50
 */
package me.geloin.door.service;

import java.util.List;

import me.geloin.door.bean.DataGridVO;
import me.geloin.door.bean.ListDto;
import me.geloin.door.entity.Channel;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-3 下午1:56:50
 * 
 */
public interface ChannelService {

	/**
	 * find by queries
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-26 下午4:23:03
	 * 
	 * @param name
	 *            name
	 * @param parentId
	 *            parent channel's id
	 * @param grid
	 *            view object, include page, pageSize, sortNames and
	 *            sortDescribes
	 * @return
	 */
	public ListDto<Channel> findAll(String name, Long parentId, DataGridVO grid);

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
	public Channel findOne(Long id);

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
	 * save channel
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-31 下午5:36:05
	 * 
	 * @param channel
	 * @return
	 */
	public Channel save(Channel channel);

	/**
	 * update sort
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午2:50:34
	 * 
	 * @param ids
	 * @param optId
	 * @param parentId
	 */
	public void updateSort(List<Long> ids, Integer optId, Long parentId);

	/**
	 * reload sort, set sort start from 1 and then next
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午3:17:24
	 * 
	 * @param parentId
	 */
	public void reloadSort(Long parentId);

	/**
	 * find input's name and its all parent name
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-10 下午5:18:17
	 * 
	 * @param id
	 * @return
	 */
	public String findAllChannelNameByID(Long id);
}
