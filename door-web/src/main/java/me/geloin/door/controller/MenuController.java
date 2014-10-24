/**
 *
 * @author geloin
 *
 * @date 2013-12-23 下午12:55:43
 */
package me.geloin.door.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import me.geloin.door.bean.CollVO;
import me.geloin.door.entity.Menu;
import me.geloin.door.service.MenuService;
import me.geloin.door.utils.Utils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-23 下午12:55:43
 * 
 */
@Controller
@RequestMapping("menu")
public class MenuController extends BaseController {

	@Resource(name = "me.geloin.door.service.MenuService")
	private MenuService menuService;

	/**
	 * to list grid
	 * 
	 * @author Geloin
	 * @date Oct 24, 2014 4:19:14 PM
	 * @param request
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Long parentId)
			throws Exception {

		CollVO<Menu> result = menuService.findAll(null, null, parentId, 1, 12);
		request.setAttribute("data", writeValueAsString(result));

		return "menu/list";
	}

	/**
	 * query json list
	 * 
	 * @author Geloin
	 * @date Oct 24, 2014 4:19:02 PM
	 * @param request
	 * @param name
	 * @param url
	 * @param parentId
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listJson")
	public ModelAndView listJson(HttpServletRequest request, String name,
			String url, Long parentId, Integer page, Integer rows)
			throws Exception {

		if (Utils.isEmpty(page)) {
			page = 1;
		}
		if (Utils.isEmpty(rows)) {
			rows = 12;
		}

		CollVO<Menu> data = menuService
				.findAll(name, url, parentId, page, rows);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("data", data);

		return ajax(result);
	}

	/**
	 * edit menu
	 * 
	 * @author Geloin
	 * @date Oct 24, 2014 4:23:11 PM
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public ModelAndView edit(Menu menu) throws Exception {

		menuService.save(menu);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);

		return ajax(result);
	}

	/**
	 * 页面跳转
	 * 
	 * @author Geloin
	 * @date Oct 24, 2014 2:21:23 PM
	 * @param request
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dispatch")
	public String dispatch(HttpServletRequest request, String page)
			throws Exception {
		return "menu/" + page;
	}

}
