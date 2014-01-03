/**
 *
 * @author geloin
 *
 * @date 2014-1-3 下午2:06:08
 */
package me.geloin.door.persistence;

import java.util.List;

import javax.annotation.Resource;

import me.geloin.door.BaseTestCase;
import me.geloin.door.entity.User;

import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-3 下午2:06:08
 * 
 */
public class UserPersistenceImplTest extends BaseTestCase {

	@Resource(name = "me.geloin.door.persistence.UserPersistence")
	private UserPersistence userPersistence;

	@Test
	public void testFindByLoginNameLikeAndFullNameLike() throws Exception {

		String loginName = "%luo%";
		String firstName = "%国%";
		String lastName = "%罗%";
		Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
		Sort sort = new Sort(order);

		Pageable page = new PageRequest(0, 12, sort);

		List<User> users = userPersistence
				.findByLoginNameLikeAndFirstNameLikeAndLastNameLike(loginName,
						firstName, lastName, page);
		for (User user : users) {
			System.out.println(user.getFullName());
		}
	}
}
