package cn.icatw.blog.dao.mapper;

import cn.icatw.blog.dao.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author icatw
 * @date 2022/3/3
 * @apiNote
 */
@Mapper
public interface CommentsMapper extends BaseMapper<Comment> {
}
