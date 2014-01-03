/**
 *
 * @author geloin
 *
 * @date 2014-1-3 下午1:03:58
 */
package me.geloin.door.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import me.geloin.door.bean.DataGridVO;
import me.geloin.door.bean.ListDto;
import me.geloin.door.bean.UserVO;
import me.geloin.door.controller.BaseController;
import me.geloin.door.entity.User;
import me.geloin.door.service.UserService;
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
 * @date 2014-1-3 下午1:03:58
 * 
 */
@Controller
@RequestMapping("admin/user")
public class UserController extends BaseController {

	@Resource(name = "me.geloin.door.service.UserService")
	private UserService userService;

	/**
	 * to user list
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午1:05:21
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String list() throws Exception {
		return "admin/user/list";
	}

	/**
	 * find user list json
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
	public String listJson(String loginName, String firstName, String lastName,
			DataGridVO grid) throws Exception {

		ListDto<User> users = userService.findAll(loginName, firstName,
				lastName, grid);

		return writeValueAsString(users);
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
		User user = userService.findOne(id);
		return writeValueAsString(user);
	}

	/**
	 * delete users
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
		userService.delete(ids);
	}

	/**
	 * save user
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午4:09:58
	 * 
	 * @param user
	 * @throws Exception
	 */
	@RequestMapping("save")
	@ResponseBody
	public void save(UserVO vo) throws Exception {

		User user = null;
		if (DataUtil.isNotEmpty(vo.getId())) {
			// 修改，仅可修改firstName和lastName
			user = userService.findOne(vo.getId());
			user.setFirstName(vo.getFirstName());
			user.setLastName(vo.getLastName());
			user.setFullName(vo.getFullName());
		} else {
			vo.setPassword(DataUtil.digest(vo.getPassword()));
			user = BeanUtil.convertBean(User.class, vo);
		}

		userService.save(user);
	}
}
