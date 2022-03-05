package cn.icatw.blog.controller;

import cn.icatw.blog.common.aop.LogAnnotation;
import cn.icatw.blog.common.cache.Cache;
import cn.icatw.blog.service.ArticleService;
import cn.icatw.blog.vo.Result;
import cn.icatw.blog.vo.params.ArticleParam;
import cn.icatw.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote
 */
@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 首页文章列表
     *
     * @param pageParams
     * @return
     */
    @PostMapping
    //加上此注解 代表对此接口记录日志
    @LogAnnotation(module = "文章", operator = "获取文章列表")
    @Cache(expire = 5 * 60 * 1000, name = "list_article")
    public Result listArticles(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    /**
     * 最热文章
     *
     * @return
     */
    @PostMapping("/hot")
    @Cache(expire = 5 * 60 * 1000, name = "hot_article")
    public Result getHotArticles() {
        int limit = 5;
        return articleService.getHotArticles(limit);
    }

    /**
     * 最新文章
     *
     * @return
     */
    @PostMapping("/new")
    @Cache(expire = 5 * 60 * 1000, name = "new_article")
    public Result getNewArticles() {
        int limit = 3;
        return articleService.getNewArticle(limit);
    }

    /**
     * 文章归档
     */
    @PostMapping("/listArchives")
    public Result listArchives() {
        return articleService.listArchives();
    }

    /**
     * 文章详情
     *
     * @param id
     * @return
     */
    @PostMapping("/view/{id}")
    public Result view(@PathVariable Long id) {
        return articleService.findArticleById(id);
    }

    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleParam articleParam) {
        return articleService.publishArticle(articleParam);
    }
}
