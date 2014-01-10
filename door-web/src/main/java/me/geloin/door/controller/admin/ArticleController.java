/**
 *
 * @author geloin
 *
 * @date 2014-1-9 下午4:30:08
 */
package me.geloin.door.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import me.geloin.door.bean.ArticleVO;
import me.geloin.door.bean.DataGridVO;
import me.geloin.door.bean.ListDto;
import me.geloin.door.controller.BaseController;
import me.geloin.door.entity.Article;
import me.geloin.door.entity.Channel;
import me.geloin.door.service.ArticleService;
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
 * @date 2014-1-9 下午4:30:08
 * 
 */
@Controller
@RequestMapping("admin/article")
public class ArticleController extends BaseController {

	@Resource(name = "me.geloin.door.service.ArticleService")
	private ArticleService articleService;

	@Resource(name = "me.geloin.door.service.ChannelService")
	private ChannelService channelService;

	/**
	 * to article list page
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午4:47:14
	 * 
	 * @param request
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Long channelId)
			throws Exception {

		if (DataUtil.isEmpty(channelId)) {
			DataGridVO grid = new DataGridVO();
			grid.setPage(1);
			grid.setRows(1);
			grid.setSidx("sort");
			grid.setSord("asc");
			List<Channel> channels = channelService.findAll(null, null, grid)
					.getRows();
			if (DataUtil.isNotEmpty(channels)) {
				channelId = channels.get(0).getId();
			}
		}

		request.setAttribute("channelId", channelId);
		return "admin/article/list";
	}

	/**
	 * list page's json value
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午5:00:13
	 * 
	 * @param channelId
	 * @param title
	 * @param grid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listJson")
	@ResponseBody
	public String listJson(Long channelId, String title, DataGridVO grid)
			throws Exception {

		ListDto<Article> articles = articleService.findAll(channelId, title,
				grid);

		return writeValueAsString(articles);
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
		Article article = articleService.findOne(id);
		return writeValueAsString(article);
	}

	/**
	 * delete articles
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
		articleService.delete(ids);
	}

	/**
	 * save article
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午4:09:58
	 * 
	 * @param article
	 * @throws Exception
	 */
	@RequestMapping("save")
	@ResponseBody
	public void save(ArticleVO vo) throws Exception {

		Article article = null;
		if (DataUtil.isNotEmpty(vo.getId())) {
			// 修改，仅可修改title、content、attachments、channel
			article = articleService.findOne(vo.getId());
			article.setTitle(vo.getTitle());
			article.setAttachments(vo.getAttachments());
			article.setContent(vo.getContent());
		} else {
			article = BeanUtil.convertBean(Article.class, vo);
			Channel channel = channelService.findOne(vo.getChannelId());
			article.setChannel(channel);
		}
		articleService.save(article);
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
		articleService.updateSort(ids, optId, parentId);
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
		articleService.reloadSort(parentId);
	}

	/**
	 * to channel list page
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 下午6:08:19
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toChannelList")
	public String toChannelList() throws Exception {
		return "redirect:/admin/channel/list.html";
	}

	/**
	 * to edit page
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-10 下午2:08:04
	 * 
	 * @param request
	 * @param channelId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toEdit")
	public String toEdit(HttpServletRequest request, Long channelId, Long id)
			throws Exception {
		if (DataUtil.isNotEmpty(id)) {
			Article article = articleService.findOne(id);
			ArticleVO vo = BeanUtil.convertBean(ArticleVO.class, article);
			channelId = article.getChannel().getId();
			vo.setChannel(null);
			vo.setChannelName(channelService.findAllChannelNameByID(channelId));
			request.setAttribute("article", vo);
		}
		
		request.setAttribute("channelId", channelId);

		return "admin/article/edit";
	}
}
