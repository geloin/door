/**
 *
 * @author geloin
 *
 * @date 2013-12-26 下午3:43:56
 */
package me.geloin.door.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import me.geloin.door.controller.BaseController;
import me.geloin.door.entity.Menu;
import me.geloin.door.service.MenuService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-26 下午3:43:56
 * 
 */
@Controller
@RequestMapping("admin/index")
public class IndexController extends BaseController {

	@Resource(name = "me.geloin.door.service.MenuService")
	private MenuService menuService;

	/**
	 * 菜单页面
	 *
	 * @author geloin
	 *
	 * @date 2013-12-26 下午4:20:53
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("menu")
	public String menu() throws Exception {
		return "admin/menu";
	}

	/**
	 * 菜单树
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-23 下午12:58:46
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "menuTree")
	@ResponseBody
	public String menuTree() throws Exception {
		List<Menu> menus = menuService.findAll();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("data", menus);
		return writeValueAsString(map);
	}
}
