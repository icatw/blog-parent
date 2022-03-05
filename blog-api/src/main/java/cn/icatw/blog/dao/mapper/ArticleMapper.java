package cn.icatw.blog.dao.mapper;

import cn.icatw.blog.dao.dos.Archives;
import cn.icatw.blog.dao.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 首页文章归档
     * @return
     */
    List<Archives> listArchives();

    /**
     * 归档文章列表
     * @param page
     * @param categoryId
     * @param tagId
     * @param year
     * @param month
     * @return
     */
    IPage<Article> listArticle(Page<Article> page, Long categoryId, Long tagId, String year, String month);
}
