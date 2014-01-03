/**
 *
 * @author geloin
 *
 * @date 2014-1-3 下午2:01:42
 */
package me.geloin.door.persistence;

import java.util.List;

import me.geloin.door.entity.User;
import me.geloin.door.repository.DoorRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-3 下午2:01:42
 * 
 */
@Repository("me.geloin.door.persistence.UserPersistence")
public interface UserPersistence extends DoorRepository<User, Long> {

	/**
	 * find like loginName, like firstName and like lastName
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午2:05:11
	 * 
	 * @param loginName
	 * @param firstName
	 * @param lastName
	 * @param page
	 * @return
	 */
	public List<User> findByLoginNameLikeAndFirstNameLikeAndLastNameLike(
			String loginName, String firstName, String lastName, Pageable page);

	/**
	 * count like loginName, like firstName and like lastName
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-3 下午3:52:56
	 * 
	 * @param loginName
	 * @param firstName
	 * @param lastName
	 * @param page
	 * @return
	 */
	public List<Long> countByLoginNameLikeAndFirstNameLikeAndLastNameLike(
			String loginName, String firstName, String lastName, Pageable page);
}
