/**
 * 
 * @author Geloin
 * @date Oct 22, 2014 2:18:41 PM
 */
package me.geloin.door.controller;

import javax.servlet.http.HttpServletRequest;

import me.geloin.door.controller.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Geloin
 * @date Oct 22, 2014 2:18:41 PM
 */
@Controller
@RequestMapping("")
public class IndexController extends BaseController {

	@RequestMapping("index")
	public ModelAndView login(HttpServletRequest request) throws Exception {
		return new ModelAndView("index");
	}
}
