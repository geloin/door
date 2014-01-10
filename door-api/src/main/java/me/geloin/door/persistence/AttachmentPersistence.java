/**
 *
 * @author geloin
 *
 * @date 2014-1-9 下午4:28:04
 */
package me.geloin.door.persistence;

import org.springframework.stereotype.Repository;

import me.geloin.door.entity.Attachment;
import me.geloin.door.repository.DoorRepository;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-9 下午4:28:04
 * 
 */
@Repository("me.geloin.door.persistence.AttachmentPersistence")
public interface AttachmentPersistence extends DoorRepository<Attachment, Long> {

}
