package cn.icatw.blog.service;

import cn.icatw.blog.dao.pojo.Article;
import cn.icatw.blog.vo.Result;
import cn.icatw.blog.vo.params.ArticleParam;
import cn.icatw.blog.vo.params.PageParams;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote
 */
public interface ArticleService extends IService<Article> {
    /**
     * 分页查询集合
     *
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 首页最热文章
     *
     * @param limit
     * @return
     */
    Result getHotArticles(int limit);

    /**
     * 最新文章
     *
     * @param limit
     * @return
     */
    Result getNewArticle(int limit);

    /**
     * 首页 文章归档
     *
     * @return
     */
    Result listArchives();

    /**
     * 文章详情
     *
     * @param id
     * @return
     */
    Result findArticleById(Long id);

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    Result publishArticle(ArticleParam articleParam);
}
