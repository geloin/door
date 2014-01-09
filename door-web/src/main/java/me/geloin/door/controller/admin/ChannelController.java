/**
 *
 * @author geloin
 *
 * @date 2014-1-7 下午3:59:45
 */
package me.geloin.door.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import me.geloin.door.bean.ChannelVO;
import me.geloin.door.bean.DataGridVO;
import me.geloin.door.bean.ListDto;
import me.geloin.door.controller.BaseController;
import me.geloin.door.entity.Channel;
import me.geloin.door.service.ChannelService;
import me.geloin.door.utils.BeanUtil;
import me.geloin.door.utils.DataUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-7 下午3:59:45
 * 
 */
@Controller
@RequestMapping("admin/channel")
public class ChannelController extends BaseController {

	@Resource(name = "me.geloin.door.service.ChannelService")
	private ChannelService channelService;

	/**
	 * to channel list
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午1:05:21
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Long parentId)
			throws Exception {
		if (DataUtil.isNotEmpty(parentId)) {
			request.setAttribute("parentId", parentId);
		}

		return "admin/channel/list";
	}

	/**
	 * find channel list json
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午4:03:32
	 * 
	 * @param loginName
	 * @param firstName
	 * @param lastName
	 * @param grid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listJson")
	@ResponseBody
	public String listJson(String name, Long parentId, DataGridVO grid)
			throws Exception {

		ListDto<Channel> channels = channelService
				.findAll(name, parentId, grid);

		return writeValueAsString(channels);
	}

	/**
	 * find by id
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午4:08:23
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("findById")
	@ResponseBody
	public String findById(Long id) throws Exception {
		Channel channel = channelService.findOne(id);
		return writeValueAsString(channel);
	}

	/**
	 * delete channels
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午4:09:21
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("delete")
	@ResponseBody
	public void delete(@RequestParam List<Long> ids) throws Exception {
		channelService.delete(ids);
	}

	/**
	 * save channel
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午4:09:58
	 * 
	 * @param channel
	 * @throws Exception
	 */
	@RequestMapping("save")
	@ResponseBody
	public void save(ChannelVO vo) throws Exception {

		Channel channel = null;
		if (DataUtil.isNotEmpty(vo.getId())) {
			// 修改，仅可修改firstName和lastName
			channel = channelService.findOne(vo.getId());
			channel.setName(vo.getName());
		} else {
			channel = BeanUtil.convertBean(Channel.class, vo);
		}

		if (DataUtil.isNotEmpty(vo.getParentId())) {
			Channel parent = channelService.findOne(vo.getParentId());
			channel.setParent(parent);
		}

		channelService.save(channel);
	}

	/**
	 * update sort
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 上午10:35:32
	 * 
	 * @param id
	 * @param sort
	 * @throws Exception
	 */
	@RequestMapping("updateSort")
	@ResponseBody
	public void updateSort(@RequestParam List<Long> ids, Integer optId,
			Long parentId) throws Exception {
		channelService.updateSort(ids, optId, parentId);
	}

	/**
	 * reload sort, set sort start from 1 and then next
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午3:20:09
	 * 
	 * @param parentId
	 * @throws Exception
	 */
	@RequestMapping("reloadSort")
	@ResponseBody
	public void reloadSort(Long parentId) throws Exception {
		channelService.reloadSort(parentId);
	}
}
