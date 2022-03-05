package cn.icatw.blog.service;

import cn.icatw.blog.dao.pojo.Comment;
import cn.icatw.blog.vo.Result;
import cn.icatw.blog.vo.params.CommentParams;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author icatw
 * @date 2022/3/3
 * @apiNote
 */
public interface CommentsService extends IService<Comment> {
    /**
     * 通过文章id获取评论
     *
     * @param articleId 文章id
     * @return
     */
    Result getCommentsById(Long articleId);

    /**
     * 新增评论
     *
     * @param commentParams
     * @return
     */
    Result addComment(CommentParams commentParams);
}
