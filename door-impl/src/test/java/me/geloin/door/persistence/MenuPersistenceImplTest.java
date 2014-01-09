/**
 *
 * @author geloin
 *
 * @date 2014-1-3 下午3:01:24
 */
package me.geloin.door.persistence;

import java.util.List;

import javax.annotation.Resource;

import me.geloin.door.BaseTestCase;
import me.geloin.door.entity.Menu;

import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-3 下午3:01:24
 * 
 */
public class MenuPersistenceImplTest extends BaseTestCase {

	@Resource(name = "me.geloin.door.persistence.MenuPersistence")
	private MenuPersistence menuPersistence;

	@Test
	public void testCountByParentIdAndNameLikeAndUrlLike() throws Exception {
		Long parentId = 11L;
		String name = "%%";
		String url = "%%";
		Pageable page = null;
		List<Long> count = menuPersistence
				.countByParentIdAndNameLikeAndUrlLike(parentId, name, url, page);
		System.out.println(count);
	}

	@Test
	public void testFindByParentIdAndNameLikeAndUrlLike() throws Exception {
		Long parentId = 11L;
		String name = "%%";
		String url = "%%";

		Pageable page = new PageRequest(0, 12);

		List<Menu> menus = menuPersistence.findByParentIdAndNameLikeAndUrlLike(
				parentId, name, url, page);
		for (Menu menu : menus) {
			System.out.println(menu.getName());
		}

		System.out.println("================================");

		page = new PageRequest(0, 1);
		menus = menuPersistence.findByParentIdAndNameLikeAndUrlLike(parentId,
				name, url, page);
		for (Menu menu : menus) {
			System.out.println(menu.getName());
		}

		System.out.println("================================");

		page = new PageRequest(1, 1);
		menus = menuPersistence.findByParentIdAndNameLikeAndUrlLike(parentId,
				name, url, page);
		for (Menu menu : menus) {
			System.out.println(menu.getName());
		}
	}
}
