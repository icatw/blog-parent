package cn.icatw.blog.dao.mapper;

import cn.icatw.blog.dao.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询标签列表
     *
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(@Param("articleId") Long articleId);

    /**
     * 查询最热标签
     *
     * @param limit
     * @return
     */
    List<Long> findHotTags(int limit);

    /**
     * 通过标签id获取标签
     *
     * @param tagIds
     * @return
     */
    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
