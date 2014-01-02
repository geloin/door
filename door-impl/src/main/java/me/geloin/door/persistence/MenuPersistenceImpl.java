/**
 *
 * @author geloin
 *
 * @date 2013-12-26 下午4:45:15
 */
package me.geloin.door.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import me.geloin.door.bean.ListDto;
import me.geloin.door.entity.Menu;
import me.geloin.door.utils.DataUtil;
import me.geloin.door.utils.PageBean;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-26 下午4:45:15
 * 
 */
@Repository
public class MenuPersistenceImpl implements MenuPersistencePlus {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public ListDto findAll(String name, String url, Long parentId,
			PageBean page, String orderHql) {

		StringBuilder whereBuilder = new StringBuilder();
		Map<Integer, Object> values = new HashMap<Integer, Object>();
		if (DataUtil.isNotEmpty(name)) {
			whereBuilder.append(" and name like ?1 ");
			values.put(1, "%" + name + "%");
		}
		if (DataUtil.isNotEmpty(url)) {
			whereBuilder.append(" and url like ?2 ");
			values.put(2, "%" + url + "%");
		}
		if (DataUtil.isNotEmpty(parentId)) {
			whereBuilder.append(" and parent.id = ?3 ");
			values.put(3, parentId);
		}

		StringBuilder listBuilder = new StringBuilder("from Menu where 1 = 1 ")
				.append(whereBuilder).append(orderHql);
		StringBuilder countBuilder = new StringBuilder(
				"select count(id) from Menu where 1 = 1 ").append(whereBuilder);
		Query listQuery = em.createQuery(listBuilder.toString());
		Query countQuery = em.createQuery(countBuilder.toString());

		if (DataUtil.isNotEmpty(values)) {
			for (Map.Entry<Integer, Object> en : values.entrySet()) {
				listQuery.setParameter(en.getKey(), en.getValue());
				countQuery.setParameter(en.getKey(), en.getValue());
			}
		}

		if (DataUtil.isNotEmpty(page)) {
			listQuery.setFirstResult(page.getStart());
			listQuery.setMaxResults(page.getPageSize());
		}

		List<Menu> rows = listQuery.getResultList();
		Long records = Long.parseLong(countQuery.getSingleResult().toString());

		ListDto result = new ListDto();
		result.setPage(page.getPage());
		result.setRecords(records);
		result.setRows(rows);

		Integer total = 0;
		Integer pageSize = page.getPageSize();
		if (records % pageSize == 0) {
			total = (int) (records / pageSize);
		} else {
			total = (int) (records / pageSize) + 1;
		}

		result.setTotal(total);

		return result;
	}
}
