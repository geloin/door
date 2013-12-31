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
	public List<Menu> findAll(String name, String url, Long parentId,
			PageBean page) {

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
				.append(whereBuilder);
		Query listQuery = em.createQuery(listBuilder.toString());

		if (DataUtil.isNotEmpty(values)) {
			for (Map.Entry<Integer, Object> en : values.entrySet()) {
				listQuery.setParameter(en.getKey(), en.getValue());
			}
		}

		if (DataUtil.isNotEmpty(page)) {
			listQuery.setFirstResult(page.getStart());
			listQuery.setMaxResults(page.getPageSize());
		}
		return listQuery.getResultList();
	}
}
