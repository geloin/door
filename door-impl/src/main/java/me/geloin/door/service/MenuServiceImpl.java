/**
 *
 * @author geloin
 *
 * @date 2013-12-23 上午9:42:20
 */
package me.geloin.door.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import me.geloin.door.bean.DataGridVO;
import me.geloin.door.bean.ListDto;
import me.geloin.door.constant.Constants;
import me.geloin.door.entity.Menu;
import me.geloin.door.persistence.MenuPersistence;
import me.geloin.door.utils.BusinessUtil;
import me.geloin.door.utils.DataUtil;

import org.springframework.data.domain.PageRequest;
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

		// set property createTime when id is empty
		if (DataUtil.isEmpty(menu.getId())) {
			menu.setCreateTime(new Date());
		}

		return menuPersistence.save(menu);
	}

	@Override
	public void updateSort(List<Long> ids, Integer optId, Long parentId) {
		Constants.UpdateSortOpt opt = Constants.convertIntToOpt(optId);
		Integer length = ids.size();
		switch (opt) {
		case TO_TOP: {
			// update all menus' sort miss sort + length
			menuPersistence.updateAllSortPlus(new Long(length), parentId);

			Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
			Sort sort = new Sort(order);
			List<Menu> toUpdateMenus = menuPersistence.findByIdIn(ids, sort);
			for (int menuIndex = 0; menuIndex < toUpdateMenus.size(); menuIndex++) {
				Menu menu = toUpdateMenus.get(menuIndex);
				menu.setSort(new Long(menuIndex + 1));
				toUpdateMenus.set(menuIndex, menu);
			}

			menuPersistence.save(toUpdateMenus);

			break;
		}
		case TO_UP: {

			// find all in input
			Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
			Sort sort = new Sort(order);
			List<Menu> toUpdateMenus = menuPersistence.findByIdIn(ids, sort);

			// get the first, then cath it's sort
			Long firstSort = toUpdateMenus.get(0).getSort();

			// update firtSort menu's previous sort
			order = new Sort.Order(Sort.Direction.DESC, "sort");
			sort = new Sort(order);
			Pageable pageable = new PageRequest(0, 1, sort);
			List<Menu> previousMenus = menuPersistence
					.findByParentIdAndSortLessThan(parentId, firstSort,
							pageable);
			if (DataUtil.isEmpty(previousMenus)) {
				break;
			}
			Menu previousMenu = previousMenus.get(0);
			// update previous menu's sort miss sort plus length
			menuPersistence.updateSort(previousMenu.getId(),
					previousMenu.getSort() + length);

			// update to update menus' sort miss sort minus 1
			for (int menuIndex = 0; menuIndex < toUpdateMenus.size(); menuIndex++) {
				Menu toUpdateMenu = toUpdateMenus.get(menuIndex);
				toUpdateMenu.setSort(toUpdateMenu.getSort() - 1);
				toUpdateMenus.set(menuIndex, toUpdateMenu);
			}
			menuPersistence.save(toUpdateMenus);

			break;
		}
		case TO_DOWN: {
			// find all in input
			Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
			Sort sort = new Sort(order);
			List<Menu> toUpdateMenus = menuPersistence.findByIdIn(ids, sort);

			// get the last, then cath it's sort
			Long lastSort = toUpdateMenus.get(toUpdateMenus.size() - 1)
					.getSort();

			// update lastSort menu's next sort
			order = new Sort.Order(Sort.Direction.ASC, "sort");
			sort = new Sort(order);
			Pageable pageable = new PageRequest(0, 1, sort);
			List<Menu> nextMenus = menuPersistence
					.findByParentIdAndSortGreaterThan(parentId, lastSort,
							pageable);
			if (DataUtil.isEmpty(nextMenus)) {
				break;
			}
			Menu nextMenu = nextMenus.get(0);
			// update next menu's sort miss sort minus length
			menuPersistence.updateSort(nextMenu.getId(), nextMenu.getSort()
					- length);

			// update to update menus' sort miss sort plus 1
			for (int menuIndex = 0; menuIndex < toUpdateMenus.size(); menuIndex++) {
				Menu toUpdateMenu = toUpdateMenus.get(menuIndex);
				toUpdateMenu.setSort(toUpdateMenu.getSort() + 1);
				toUpdateMenus.set(menuIndex, toUpdateMenu);
			}
			menuPersistence.save(toUpdateMenus);

			break;
		}
		case TO_BOTTOM: {
			// update all menus' sort miss sort - length
			menuPersistence.updateAllSortMinus(new Long(length), parentId);

			// find max sort
			Long maxSort = menuPersistence.findMaxSort(parentId);

			Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
			Sort sort = new Sort(order);
			List<Menu> toUpdateMenus = menuPersistence.findByIdIn(ids, sort);
			for (int menuIndex = 0; menuIndex < toUpdateMenus.size(); menuIndex++) {
				Menu menu = toUpdateMenus.get(menuIndex);
				menu.setSort(new Long(menuIndex + 1 + maxSort));
				toUpdateMenus.set(menuIndex, menu);
			}

			menuPersistence.save(toUpdateMenus);

			break;
		}
		default: {
			break;
		}
		}

		// reset sort
		reloadSort(parentId);
	}

	@Override
	public void reloadSort(Long parentId) {
		Sort.Order order = new Sort.Order(Direction.ASC, "sort");
		Sort sort = new Sort(order);
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, sort);
		List<Menu> menus = menuPersistence.findByParentIdAndNameLikeAndUrlLike(
				parentId, "%%", "%%", pageable);
		for (int menuIndex = 0; menuIndex < menus.size(); menuIndex++) {
			Menu menu = menus.get(menuIndex);
			menu.setSort(new Long(menuIndex + 1));
			menus.set(menuIndex, menu);
		}
		menuPersistence.save(menus);
	}

}
