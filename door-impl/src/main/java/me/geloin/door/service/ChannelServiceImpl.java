/**
 *
 * @author geloin
 *
 * @date 2014-1-7 下午4:16:03
 */
package me.geloin.door.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import me.geloin.door.bean.DataGridVO;
import me.geloin.door.bean.ListDto;
import me.geloin.door.constant.Constants;
import me.geloin.door.entity.Channel;
import me.geloin.door.persistence.ChannelPersistence;
import me.geloin.door.utils.BusinessUtil;
import me.geloin.door.utils.DataUtil;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-7 下午4:16:03
 * 
 */
@Repository("me.geloin.door.service.ChannelService")
public class ChannelServiceImpl implements ChannelService {

	@Resource(name = "me.geloin.door.persistence.ChannelPersistence")
	private ChannelPersistence channelPersistence;

	@Override
	public ListDto<Channel> findAll(String name, Long parentId, DataGridVO grid) {

		name = BusinessUtil.generateLikeValue(name);

		Pageable pageable = BusinessUtil.generatePageable(grid);

		List<Channel> channels = new ArrayList<Channel>();
		Long count = 0L;

		if (DataUtil.isEmpty(parentId)) {
			channels = channelPersistence.findByNameLikeAndParentIsNull(name,
					pageable);
			count = channelPersistence.countByNameLikeAndParentIsNull(name)
					.get(0);
		} else {
			channels = channelPersistence.findByNameLikeAndParentId(name,
					parentId, pageable);
			count = channelPersistence.countByNameLikeAndParentId(name,
					parentId).get(0);
		}

		ListDto<Channel> result = new ListDto<Channel>(grid.getPage(),
				grid.getRows(), count, channels);

		return result;
	}

	@Override
	public Channel findOne(Long id) {
		return channelPersistence.findOne(id);
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			channelPersistence.delete(id);
		}
	}

	@Override
	public Channel save(Channel channel) {
		return channelPersistence.saveAndFlush(channel);
	}

	@Override
	public void updateSort(List<Long> ids, Integer optId, Long parentId) {
		Constants.UpdateSortOpt opt = Constants.convertIntToOpt(optId);
		Integer length = ids.size();
		switch (opt) {
		case TO_TOP: {
			// update all channels' sort miss sort + length
			if (DataUtil.isEmpty(parentId)) {
				channelPersistence.updateAllSortPlusByNullParent(new Long(
						length));
			} else {
				channelPersistence
						.updateAllSortPlus(new Long(length), parentId);
			}

			Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
			Sort sort = new Sort(order);
			List<Channel> toUpdateChannels = channelPersistence.findByIdIn(ids,
					sort);
			for (int channelIndex = 0; channelIndex < toUpdateChannels.size(); channelIndex++) {
				Channel channel = toUpdateChannels.get(channelIndex);
				channel.setSort(new Long(channelIndex + 1));
				toUpdateChannels.set(channelIndex, channel);
			}

			channelPersistence.save(toUpdateChannels);

			break;
		}
		case TO_UP: {

			// find all in input
			Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
			Sort sort = new Sort(order);
			List<Channel> toUpdateChannels = channelPersistence.findByIdIn(ids,
					sort);

			// get the first, then cath it's sort
			Long firstSort = toUpdateChannels.get(0).getSort();

			// update firtSort channel's previous sort
			order = new Sort.Order(Sort.Direction.DESC, "sort");
			sort = new Sort(order);
			Pageable pageable = new PageRequest(0, 1, sort);
			List<Channel> previousChannels = new ArrayList<Channel>();

			if (DataUtil.isEmpty(parentId)) {
				previousChannels = channelPersistence
						.findByParentIsNullAndSortLessThan(firstSort, pageable);
			} else {
				previousChannels = channelPersistence
						.findByParentIdAndSortLessThan(parentId, firstSort,
								pageable);
			}

			if (DataUtil.isEmpty(previousChannels)) {
				break;
			}
			Channel previousChannel = previousChannels.get(0);
			// update previous channel's sort miss sort plus length
			channelPersistence.updateSort(previousChannel.getId(),
					previousChannel.getSort() + length);

			// update to update channels' sort miss sort minus 1
			for (int channelIndex = 0; channelIndex < toUpdateChannels.size(); channelIndex++) {
				Channel toUpdateChannel = toUpdateChannels.get(channelIndex);
				toUpdateChannel.setSort(toUpdateChannel.getSort() - 1);
				toUpdateChannels.set(channelIndex, toUpdateChannel);
			}
			channelPersistence.save(toUpdateChannels);

			break;
		}
		case TO_DOWN: {
			// find all in input
			Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
			Sort sort = new Sort(order);
			List<Channel> toUpdateChannels = channelPersistence.findByIdIn(ids,
					sort);

			// get the last, then cath it's sort
			Long lastSort = toUpdateChannels.get(toUpdateChannels.size() - 1)
					.getSort();

			// update lastSort channel's next sort
			order = new Sort.Order(Sort.Direction.ASC, "sort");
			sort = new Sort(order);
			Pageable pageable = new PageRequest(0, 1, sort);
			List<Channel> nextChannels = new ArrayList<Channel>();
			if (DataUtil.isEmpty(parentId)) {
				nextChannels = channelPersistence
						.findByParentIsNullAndSortGreaterThan(lastSort,
								pageable);
			} else {
				nextChannels = channelPersistence
						.findByParentIdAndSortGreaterThan(parentId, lastSort,
								pageable);
			}

			if (DataUtil.isEmpty(nextChannels)) {
				break;
			}
			Channel nextChannel = nextChannels.get(0);
			// update next channel's sort miss sort minus length
			channelPersistence.updateSort(nextChannel.getId(),
					nextChannel.getSort() - length);

			// update to update channels' sort miss sort plus 1
			for (int channelIndex = 0; channelIndex < toUpdateChannels.size(); channelIndex++) {
				Channel toUpdateChannel = toUpdateChannels.get(channelIndex);
				toUpdateChannel.setSort(toUpdateChannel.getSort() + 1);
				toUpdateChannels.set(channelIndex, toUpdateChannel);
			}
			channelPersistence.save(toUpdateChannels);

			break;
		}
		case TO_BOTTOM: {
			// update all channels' sort miss sort - length
			Long maxSort = 0L;
			if (DataUtil.isEmpty(parentId)) {
				channelPersistence.updateAllSortMinusByNullParent(new Long(
						length));

				// find max sort
				maxSort = channelPersistence.findMaxSortByNullParent();
			} else {
				channelPersistence.updateAllSortMinus(new Long(length),
						parentId);

				// find max sort
				maxSort = channelPersistence.findMaxSort(parentId);
			}

			Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
			Sort sort = new Sort(order);
			List<Channel> toUpdateChannels = channelPersistence.findByIdIn(ids,
					sort);
			for (int channelIndex = 0; channelIndex < toUpdateChannels.size(); channelIndex++) {
				Channel channel = toUpdateChannels.get(channelIndex);
				channel.setSort(new Long(channelIndex + 1 + maxSort));
				toUpdateChannels.set(channelIndex, channel);
			}

			channelPersistence.save(toUpdateChannels);

			break;
		}
		default: {
			break;
		}
		}

		reloadSort(parentId);
	}

	@Override
	public void reloadSort(Long parentId) {
		Sort.Order order = new Sort.Order(Direction.ASC, "sort");
		Sort sort = new Sort(order);
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, sort);

		List<Channel> channels = new ArrayList<Channel>();

		if (DataUtil.isEmpty(parentId)) {
			channels = channelPersistence.findByNameLikeAndParentIsNull("%%",
					pageable);
		} else {
			channels = channelPersistence.findByNameLikeAndParentId("%%",
					parentId, pageable);
		}

		for (int channelIndex = 0; channelIndex < channels.size(); channelIndex++) {
			Channel channel = channels.get(channelIndex);
			channel.setSort(new Long(channelIndex + 1));
			channels.set(channelIndex, channel);
		}
		channelPersistence.save(channels);
	}

}
