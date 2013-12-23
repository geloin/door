/**
 *
 * @author geloin
 *
 * @date 2013-12-23 下午12:55:43
 */
package me.geloin.door.controller;

import java.util.List;

import javax.annotation.Resource;

import me.geloin.door.entity.Menu;
import me.geloin.door.service.MenuService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-23 下午12:55:43
 * 
 */
@Controller
@RequestMapping("admin")
public class MenuController {

	@Resource(name = "me.geloin.door.service.MenuService")
	private MenuService menuService;

	private ObjectMapper mapper = new ObjectMapper();

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
		return mapper.writeValueAsString(menus);
	}
}
