/**
 *
 * @author geloin
 *
 * @date 2013-12-23 上午9:50:31
 */
package me.geloin.door.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-23 上午9:50:31
 * 
 */
public interface DoorRepository<T, ID extends Serializable> extends
		JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}
