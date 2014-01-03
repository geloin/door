/**
 *
 * @author geloin
 *
 * @date 2014-1-3 下午2:00:49
 */
package me.geloin.door.service;

import java.util.List;

import javax.annotation.Resource;

import me.geloin.door.bean.DataGridVO;
import me.geloin.door.bean.ListDto;
import me.geloin.door.entity.User;
import me.geloin.door.persistence.UserPersistence;
import me.geloin.door.utils.BusinessUtil;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-3 下午2:00:49
 * 
 */
@Service("me.geloin.door.service.UserService")
public class UserServiceImpl implements UserService {

	@Resource(name = "me.geloin.door.persistence.UserPersistence")
	private UserPersistence userPersistence;

	@Override
	public ListDto<User> findAll(String loginName, String firstName,
			String lastName, DataGridVO grid) {

		loginName = BusinessUtil.generateLikeValue(loginName);
		firstName = BusinessUtil.generateLikeValue(firstName);
		lastName = BusinessUtil.generateLikeValue(lastName);

		Pageable page = BusinessUtil.generatePageable(grid);

		List<User> users = userPersistence
				.findByLoginNameLikeAndFirstNameLikeAndLastNameLike(loginName,
						firstName, lastName, page);
		Long count = userPersistence
				.countByLoginNameLikeAndFirstNameLikeAndLastNameLike(loginName,
						firstName, lastName, page).get(0);

		ListDto<User> result = new ListDto<User>(grid.getPage(),
				grid.getRows(), count, users);

		return result;
	}

	@Override
	public User findOne(Long id) {
		return userPersistence.findOne(id);
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			userPersistence.delete(id);
		}
	}

	@Override
	public User save(User user) {
		return userPersistence.save(user);
	}

}
