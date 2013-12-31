/**
 *
 * @author geloin
 *
 * @date 2013-12-23 下午12:55:43
 */
package me.geloin.door.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import me.geloin.door.bean.MenuVO;
import me.geloin.door.controller.BaseController;
import me.geloin.door.entity.Menu;
import me.geloin.door.service.MenuService;
import me.geloin.door.utils.BeanUtil;
import me.geloin.door.utils.DataUtil;
import me.geloin.door.utils.PageBean;

import org.jboss.logging.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	 * to list grid
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-31 下午5:52:25
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request) throws Exception {
		request.setAttribute("parentId", 1L);
		return "admin/menu/list";
	}

	/**
	 * query menu list by conditions, and then return JSON value.
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-27 下午1:58:41
	 * 
	 * @param request
	 * @param name
	 * @param url
	 * @param parentId
	 * @param pageNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listJson")
	@ResponseBody
	public String listJson(HttpServletRequest request, @Param String name,
			@Param String url, @Param Long parentId, @Param Integer pageNum)
			throws Exception {

		if (DataUtil.isEmpty(parentId)) {
			parentId = 1L;
		}

		PageBean page = new PageBean();
		if (DataUtil.isEmpty(pageNum)) {
			pageNum = 1;
		}
		page.setPage(pageNum);

		List<Menu> menus = menuService.findAll(name, url, parentId, page);

		request.setAttribute("parentId", parentId);

		return writeValueAsString(menus);
	}

	/**
	 * find by id
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-31 下午1:18:34
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("findById")
	@ResponseBody
	public String findByIdJson(@Param Long id) throws Exception {
		Menu menu = menuService.findOne(id);
		return writeValueAsString(menu);
	}

	/**
	 * delete in batch
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-31 下午4:06:48
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("delete")
	@ResponseBody
	public void delete(@RequestParam List<Long> ids) throws Exception {
		System.out.println(ids);
		menuService.delete(ids);
	}

	/**
	 * save
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-31 下午5:29:20
	 * 
	 * @param vo
	 * @throws Exception
	 */
	@RequestMapping("save")
	@ResponseBody
	public void save(MenuVO vo) throws Exception {

		Menu menu = null;
		if (DataUtil.isNotEmpty(vo.getId()) && vo.getId() > 0) {
			menu = menuService.findOne(vo.getId());
			menu.setName(vo.getName());
			menu.setUrl(vo.getUrl());
		} else {
			menu = BeanUtil.convertBean(Menu.class, vo);
			Menu parent = menuService.findOne(vo.getParentId());
			menu.setParent(parent);
		}
		menuService.save(menu);
	}
}
