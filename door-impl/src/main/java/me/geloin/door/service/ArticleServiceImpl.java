/**
 *
 * @author geloin
 *
 * @date 2014-1-9 下午4:29:05
 */
package me.geloin.door.service;

import java.util.List;

import javax.annotation.Resource;

import me.geloin.door.bean.DataGridVO;
import me.geloin.door.bean.ListDto;
import me.geloin.door.constant.Constants;
import me.geloin.door.entity.Article;
import me.geloin.door.persistence.ArticlePersistence;
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
 * @date 2014-1-9 下午4:29:05
 * 
 */
@Service("me.geloin.door.service.ArticleService")
public class ArticleServiceImpl implements ArticleService {

	@Resource(name = "me.geloin.door.persistence.ArticlePersistence")
	private ArticlePersistence articlePersistence;

	@Override
	public ListDto<Article> findAll(Long channelId, String title,
			DataGridVO grid) {

		title = BusinessUtil.generateLikeValue(title);

		Pageable pageable = BusinessUtil.generatePageable(grid);

		List<Article> articles = articlePersistence
				.findByChannelIdAndTitleLike(channelId, title, pageable);
		Long count = articlePersistence.countByChannelIdAndTitleLike(channelId,
				title).get(0);

		ListDto<Article> result = new ListDto<Article>(grid.getPage(),
				grid.getRows(), count, articles);

		return result;
	}

	@Override
	public Article findOne(Long id) {
		return articlePersistence.findOne(id);
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			articlePersistence.delete(id);
		}
	}

	@Override
	public Article save(Article article) {
		return articlePersistence.save(article);
	}

	@Override
	public void updateSort(List<Long> ids, Integer optId, Long channelId) {
		Constants.UpdateSortOpt opt = Constants.convertIntToOpt(optId);
		Integer length = ids.size();
		switch (opt) {
		case TO_TOP: {
			// update all articles' sort miss sort + length
			articlePersistence.updateAllSortPlus(new Long(length), channelId);

			Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
			Sort sort = new Sort(order);
			List<Article> toUpdateArticles = articlePersistence.findByIdIn(ids,
					sort);
			for (int articleIndex = 0; articleIndex < toUpdateArticles.size(); articleIndex++) {
				Article article = toUpdateArticles.get(articleIndex);
				article.setSort(new Long(articleIndex + 1));
				toUpdateArticles.set(articleIndex, article);
			}

			articlePersistence.save(toUpdateArticles);

			break;
		}
		case TO_UP: {

			// find all in input
			Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
			Sort sort = new Sort(order);
			List<Article> toUpdateArticles = articlePersistence.findByIdIn(ids,
					sort);

			// get the first, then cath it's sort
			Long firstSort = toUpdateArticles.get(0).getSort();

			// update firtSort article's previous sort
			order = new Sort.Order(Sort.Direction.DESC, "sort");
			sort = new Sort(order);
			Pageable pageable = new PageRequest(0, 1, sort);
			List<Article> previousArticles = articlePersistence
					.findByChannelIdAndSortLessThan(channelId, firstSort,
							pageable);

			if (DataUtil.isEmpty(previousArticles)) {
				break;
			}
			Article previousArticle = previousArticles.get(0);
			// update previous article's sort miss sort plus length
			articlePersistence.updateSort(previousArticle.getId(),
					previousArticle.getSort() + length);

			// update to update articles' sort miss sort minus 1
			for (int articleIndex = 0; articleIndex < toUpdateArticles.size(); articleIndex++) {
				Article toUpdateArticle = toUpdateArticles.get(articleIndex);
				toUpdateArticle.setSort(toUpdateArticle.getSort() - 1);
				toUpdateArticles.set(articleIndex, toUpdateArticle);
			}
			articlePersistence.save(toUpdateArticles);

			break;
		}
		case TO_DOWN: {
			// find all in input
			Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
			Sort sort = new Sort(order);
			List<Article> toUpdateArticles = articlePersistence.findByIdIn(ids,
					sort);

			// get the last, then cath it's sort
			Long lastSort = toUpdateArticles.get(toUpdateArticles.size() - 1)
					.getSort();

			// update lastSort article's next sort
			order = new Sort.Order(Sort.Direction.ASC, "sort");
			sort = new Sort(order);
			Pageable pageable = new PageRequest(0, 1, sort);
			List<Article> nextArticles = articlePersistence
					.findByChannelIdAndSortGreaterThan(channelId, lastSort,
							pageable);

			if (DataUtil.isEmpty(nextArticles)) {
				break;
			}
			Article nextArticle = nextArticles.get(0);
			// update next article's sort miss sort minus length
			articlePersistence.updateSort(nextArticle.getId(),
					nextArticle.getSort() - length);

			// update to update articles' sort miss sort plus 1
			for (int articleIndex = 0; articleIndex < toUpdateArticles.size(); articleIndex++) {
				Article toUpdateArticle = toUpdateArticles.get(articleIndex);
				toUpdateArticle.setSort(toUpdateArticle.getSort() + 1);
				toUpdateArticles.set(articleIndex, toUpdateArticle);
			}
			articlePersistence.save(toUpdateArticles);

			break;
		}
		case TO_BOTTOM: {
			// update all articles' sort miss sort - length
			articlePersistence.updateAllSortMinus(new Long(length), channelId);

			// find max sort
			Long maxSort = articlePersistence.findMaxSort(channelId);

			Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
			Sort sort = new Sort(order);
			List<Article> toUpdateArticles = articlePersistence.findByIdIn(ids,
					sort);
			for (int articleIndex = 0; articleIndex < toUpdateArticles.size(); articleIndex++) {
				Article article = toUpdateArticles.get(articleIndex);
				article.setSort(new Long(articleIndex + 1 + maxSort));
				toUpdateArticles.set(articleIndex, article);
			}

			articlePersistence.save(toUpdateArticles);

			break;
		}
		default: {
			break;
		}
		}

		reloadSort(channelId);
	}

	@Override
	public void reloadSort(Long channelId) {
		Sort.Order order = new Sort.Order(Direction.ASC, "sort");
		Sort sort = new Sort(order);
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, sort);

		List<Article> articles = articlePersistence
				.findByChannelIdAndTitleLike(channelId, "%%", pageable);

		for (int articleIndex = 0; articleIndex < articles.size(); articleIndex++) {
			Article article = articles.get(articleIndex);
			article.setSort(new Long(articleIndex + 1));
			articles.set(articleIndex, article);
		}
		articlePersistence.save(articles);
	}
}
