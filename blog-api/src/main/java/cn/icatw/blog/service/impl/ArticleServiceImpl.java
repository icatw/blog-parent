package cn.icatw.blog.service.impl;

import cn.icatw.blog.dao.dos.Archives;
import cn.icatw.blog.dao.mapper.ArticleBodyMapper;
import cn.icatw.blog.dao.mapper.ArticleMapper;
import cn.icatw.blog.dao.mapper.ArticleTagMapper;
import cn.icatw.blog.dao.pojo.*;
import cn.icatw.blog.service.*;
import cn.icatw.blog.utils.UserThreadLocal;
import cn.icatw.blog.vo.*;
import cn.icatw.blog.vo.params.ArticleParam;
import cn.icatw.blog.vo.params.PageParams;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<Article>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = this.baseMapper.listArticle(page, pageParams.getCategoryId(), pageParams.getTagId(), pageParams.getYear(), pageParams.getMonth());
        return Result.success(copyList(articleIPage.getRecords(), true, true));
    }

    @Override
    public Result getHotArticles(int limit) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title").orderByDesc("view_counts").last("limit " + limit);
        List<Article> articleList = this.baseMapper.selectList(wrapper);
        //List<HotArticleVo> hotArticleVoList=new ArrayList<>();
        //BeanUtils.copyProperties(articleList,hotArticleVoList);
        return Result.success(copyList(articleList, false, false));
    }

    @Override
    public Result getNewArticle(int limit) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title").orderByDesc("create_date").last("limit " + limit);
        List<Article> articles = this.baseMapper.selectList(wrapper);
        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result listArchives() {
        /**
         * select year(FROM_UNIXTIME(create_date/1000)) year,month(FROM_UNIXTIME(create_date/1000)) month,
         * count(*) count from ms_article group by year,month;
         */
        List<Archives> archivesList = this.baseMapper.listArchives();
        return Result.success(archivesList);
    }

    @Autowired
    private ThreadService threadService;

    @Override
    public Result findArticleById(Long id) {
        /**
         * 1、根据id查询文章信息
         * 2、根据bodyId和categoryId 去做关联查询
         *
         */
        Article article = this.baseMapper.selectById(id);
        ArticleVo articleVo = copy(article, true, true, true, true);
        //点击查询文章 新增阅读数 做更新操作 会更新时加写锁 阻塞其他的读操作 降低性能
        //更新 增加耗时 如果更新出问题 不能影响查看文章的操作
        //线程池 可以把更新操作放在线程池中执行 不会干扰主线程
        threadService.updateArticleViewCount(this.baseMapper, article);
        return Result.success(articleVo);
    }

    @Transactional
    @Override
    public Result publishArticle(ArticleParam articleParam) {
        /**
         * 1、发布文章 目的 构建article对象
         * 2、作者id 当前登陆用户
         * 3、标签 要将标签加入到关联列表当中
         * 4、body 内容存储article bodyId
         *
         */
        SysUser sysUser = UserThreadLocal.get();
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setWeight(Article.Article_Common);
        article.setBodyId(-1L);
        //发布文章之后会得到文章id
        this.baseMapper.insert(article);
        //tag
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }
        //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBody.setArticleId(article.getId());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        this.baseMapper.updateById(article);
        Map<String, String> map = new HashMap<>();
        map.put("id", article.getId().toString());
        return Result.success(map);
    }

    /**
     * 将articleList转换为articleVoList vo提供给前端
     *
     * @param records
     * @return
     */
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(this.copy(record, isTag, isAuthor, false, false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(this.copy(record, isTag, isAuthor, isBody, isCategory));
        }
        return articleVoList;
    }

    @Autowired
    private CategoryService categoryService;

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        BeanUtils.copyProperties(article, articleVo);

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //并不是所有的接口都需要标签和作者信息，需要判断
        if (isTag) {
            Long articleId = article.getId();
            List<TagVo> tags = tagService.findTagsByArticleId(articleId);
            articleVo.setTags(tags);
        }

        if (isAuthor) {
            Long authorId = article.getAuthorId();
            SysUser user = sysUserService.findUserById(authorId);
            articleVo.setAuthor(user.getNickname());
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            Category category = categoryService.getById(categoryId);
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(category, categoryVo);
            articleVo.setCategory(categoryVo);
        }
        return articleVo;
    }

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
