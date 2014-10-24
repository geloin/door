/**
 *
 * @author geloin
 *
 * @date 2013-12-23 上午9:42:20
 */
package me.geloin.door.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import me.geloin.door.bean.CollVO;
import me.geloin.door.entity.Menu;
import me.geloin.door.persistence.MenuPersistence;
import me.geloin.door.utils.Utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
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
	public CollVO<Menu> findAll(final String name, final String url,
			final Long parentId, Integer pageNum, Integer pageSize) {

		pageNum -= 1;
		Sort sort = new Sort("sort");
		Pageable pageable = new PageRequest(pageNum, pageSize, sort);
		Page<Menu> menus = menuPersistence.findAll(new Specification<Menu>() {

			@Override
			public Predicate toPredicate(Root<Menu> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> pdts = new ArrayList<Predicate>();
				if (Utils.isNotEmpty(name)) {
					Predicate namePdt = cb
							.like(root.get("name").as(String.class), "%" + name
									+ "%");
					pdts.add(namePdt);
				}

				if (Utils.isNotEmpty(url)) {
					Predicate urlPdt = cb.like(
							root.get("url").as(String.class), "%" + url + "%");
					pdts.add(urlPdt);
				}

				if (Utils.isNotEmpty(parentId) && parentId.intValue() != 0) {
					Predicate pidPdt = cb.equal(
							root.get("parentId").as(Long.class), parentId);
					pdts.add(pidPdt);
				}

				Predicate[] predicates = new Predicate[pdts.size()];
				return cb.and(pdts.toArray(predicates));
			}
		}, pageable);

		CollVO<Menu> result = new CollVO<Menu>();
		result.setCount(menus.getTotalElements());
		result.setData(menus.getContent());

		return result;
	}

	@Override
	public Menu save(Menu entity) {
		Menu menu = null;

		if (Utils.isEmpty(entity.getSort())) {
			entity.setSort(0L);
		}

		if (Utils.isNotEmpty(entity.getId())) {
			menu = menuPersistence.findOne(entity.getId());
			menu.setName(entity.getName());
			menu.setUrl(entity.getUrl());
		} else {
			entity.setCreateTime(new Date());
			menu = entity;
		}

		return menuPersistence.save(menu);
	}

}
