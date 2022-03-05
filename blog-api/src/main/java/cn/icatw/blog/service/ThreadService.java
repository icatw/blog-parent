package cn.icatw.blog.service;

import cn.icatw.blog.dao.mapper.ArticleMapper;
import cn.icatw.blog.dao.pojo.Article;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author icatw
 * @date 2022/3/3
 * @apiNote
 */
@Component
public class ThreadService {
    //期望此操作在线程池中进行 不会影响原有的主线程
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        //articleMapper.updateById(articleUpdate);
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getId, article.getId())
                .eq(Article::getViewCounts, viewCounts);
        articleMapper.update(articleUpdate, wrapper);
        try {
            Thread.sleep(5000);
            System.out.println("更新完成了....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
