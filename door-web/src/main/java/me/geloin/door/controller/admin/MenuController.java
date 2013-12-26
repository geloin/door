/**
 *
 * @author geloin
 *
 * @date 2013-12-23 下午12:55:43
 */
package me.geloin.door.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import me.geloin.door.bean.ListDto;
import me.geloin.door.controller.BaseController;
import me.geloin.door.service.MenuService;
import me.geloin.door.utils.DataUtil;
import me.geloin.door.utils.PageBean;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-23 下午12:55:43
 * 
 */
@Controller
@RequestMapping("admin/menu")
public class MenuController extends BaseController {

	@Resource(name = "me.geloin.door.service.MenuService")
	private MenuService menuService;

	/**
	 * 列表页面
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-26 下午4:21:14
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String list() throws Exception {
		return "admin/menu/list";
	}

	/**
	 * query menu list by conditions, and then return JSON value.
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-26 下午4:55:15
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listJson")
	@ResponseBody
	public String listJson(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);

		String name = request.getParameter("name");
		String url = request.getParameter("url");
		String parentIdStr = request.getParameter("parentId");
		Long parentId = null;
		if (DataUtil.isNotEmpty(parentIdStr)) {
			parentId = Long.parseLong(parentIdStr);
		}

		String pageNumStr = request.getParameter("pageNum");
		Integer pageNum = 0;
		if (DataUtil.isNotEmpty(pageNumStr)) {
			pageNum = Integer.parseInt(pageNumStr);
		}

		PageBean page = new PageBean();
		page.setPage(pageNum);

		ListDto menuListDto = menuService.findAll(name, url, parentId, page);
		map.put("data", menuListDto);
		return writeValueAsString(map);
	}
}
