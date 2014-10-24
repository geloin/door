/**
 *
 * @author geloin
 *
 * @date 2013-12-23 上午9:44:12
 */
package me.geloin.door.persistence;

import me.geloin.door.entity.Menu;
import me.geloin.door.repository.DoorRepository;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-23 上午9:44:12
 * 
 */
@Repository("me.geloin.door.persistence.MenuPersistence")
public interface MenuPersistence extends DoorRepository<Menu, Long> {

}
