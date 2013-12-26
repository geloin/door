/**
 *
 * @author geloin
 *
 * @date 2013-12-24 下午4:39:59
 */
package me.geloin.door.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import me.geloin.door.controller.BaseController;
import me.geloin.door.entity.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * controller for login
 * 
 * @author geloin
 * 
 * @date 2013-12-24 下午4:39:59
 * 
 */
@Controller
@RequestMapping("")
public class LoginController extends BaseController {

	/**
	 * login controller
	 * 
	 * @author geloin
	 * 
	 * @date 2013-12-24 下午4:58:10
	 * 
	 * @param request
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("login")
	public ModelAndView login(HttpServletRequest request, User user)
			throws Exception {

		// set context
		String ctx = "http://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/";
		HttpSession session = request.getSession();
		session.setAttribute("ctx", ctx);

		return new ModelAndView("admin/index");
	}
}
