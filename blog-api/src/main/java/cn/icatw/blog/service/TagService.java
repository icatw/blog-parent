package cn.icatw.blog.service;

import cn.icatw.blog.dao.pojo.Tag;
import cn.icatw.blog.vo.Result;
import cn.icatw.blog.vo.TagVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote
 */
public interface TagService extends IService<Tag> {
    /**
     * 通过文章id查询标签list
     *
     * @param articleId 文章id
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 获取最热标签 默认前六条
     *
     * @param limit 前六条
     * @return
     */
    Result getHotTags(int limit);

    /**
     * 查询所有
     * @return
     */
    Result findAll();

    /**
     * 查询所有详情
     * @return
     */
    Result findAllDetail();

    /**
     * 根据标签id查询标签下的文章列表
     * @param id
     * @return
     */
    Result findDetailById(Long id);
}
