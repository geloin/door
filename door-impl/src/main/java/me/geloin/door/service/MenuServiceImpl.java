/**
 *
 * @author geloin
 *
 * @date 2013-12-23 上午9:42:20
 */
package me.geloin.door.service;

import java.util.List;

import javax.annotation.Resource;

import me.geloin.door.bean.ListDto;
import me.geloin.door.entity.Menu;
import me.geloin.door.persistence.MenuPersistence;
import me.geloin.door.utils.PageBean;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-23 上午9:42:20
 * 
 */
@Service("me.geloin.door.service.MenuService")
public class MenuServiceImpl implements MenuService {

	@Resource(name = "me.geloin.door.persistence.MenuPersistence")
	private MenuPersistence menuPersistence;

	@Override
	public List<Menu> findAll() {

		Sort.Order order = new Sort.Order(Direction.ASC, "sort");
		Sort sort = new Sort(order);

		return menuPersistence.findAll(sort);
	}

	@Override
	public ListDto findAll(String name, String url, Long parentId,
			PageBean page) {
		return menuPersistence.findAll(name, url, parentId, page);
	}

}
