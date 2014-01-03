/**
 *
 * @author geloin
 *
 * @date 2013-12-23 上午9:42:20
 */
package me.geloin.door.service;

import java.util.List;

import javax.annotation.Resource;

import me.geloin.door.bean.DataGridVO;
import me.geloin.door.bean.ListDto;
import me.geloin.door.entity.Menu;
import me.geloin.door.persistence.MenuPersistence;
import me.geloin.door.utils.BusinessUtil;

import org.springframework.data.domain.Pageable;
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
	public ListDto<Menu> findAll(String name, String url, Long parentId,
			DataGridVO grid) {

		name = BusinessUtil.generateLikeValue(name);
		url = BusinessUtil.generateLikeValue(url);

		Pageable pageRequest = BusinessUtil.generatePageable(grid);

		List<Menu> menus = menuPersistence.findByParentIdAndNameLikeAndUrlLike(
				parentId, name, url, pageRequest);
		Long count = menuPersistence.countByParentIdAndNameLikeAndUrlLike(
				parentId, name, url, pageRequest).get(0);

		ListDto<Menu> result = new ListDto<Menu>(grid.getPage(),
				grid.getRows(), count, menus);

		return result;
	}

	@Override
	public Menu findOne(Long id) {
		return menuPersistence.findOne(id);
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			menuPersistence.delete(id);
		}
	}

	@Override
	public Menu save(Menu menu) {
		return menuPersistence.save(menu);
	}

}
