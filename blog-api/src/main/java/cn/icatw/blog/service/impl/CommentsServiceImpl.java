package cn.icatw.blog.service.impl;

import cn.icatw.blog.dao.mapper.CommentsMapper;
import cn.icatw.blog.dao.pojo.Comment;
import cn.icatw.blog.dao.pojo.SysUser;
import cn.icatw.blog.service.CommentsService;
import cn.icatw.blog.service.SysUserService;
import cn.icatw.blog.utils.UserThreadLocal;
import cn.icatw.blog.vo.CommentVo;
import cn.icatw.blog.vo.Result;
import cn.icatw.blog.vo.UserVo;
import cn.icatw.blog.vo.params.CommentParams;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author icatw
 * @date 2022/3/3
 * @apiNote
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comment> implements CommentsService {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result getCommentsById(Long articleId) {
        /**
         * 1、根据文章id查询评论列表 从comments表中查询
         * 2、根据作者id 查询作者信息
         * 3、如果level=1 要去查询子评论
         * 4、递归查询
         */
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId, articleId)
                .eq(Comment::getLevel, 1);
        List<Comment> comments = this.baseMapper.selectList(wrapper);
        List<CommentVo> commentVoList = copyList(comments);

        return Result.success(commentVoList);
    }

    @Override
    public Result addComment(CommentParams commentParams) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParams.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParams.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParams.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        } else {
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParams.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.baseMapper.insert(comment);
        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        commentVo.setId(String.valueOf(comment.getId()));
        //添加作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoByAuthorId(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        Integer level = comment.getLevel();
        //父评论需要设置子评论数组
        if (level == 1) {
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
        //子评论需要添加评论对象 是给谁评论
        //添加toUser 给谁评论
        if (level > 1) {
            Long toUid = comment.getToUid();
            //通过回复id去查询评论人信息
            UserVo toUserVo = sysUserService.findUserVoByAuthorId(toUid);
            commentVo.setToUser(toUserVo);
        }

        //添加评论数
        QueryWrapper<Comment> countWrapper = new QueryWrapper<>();
        countWrapper.eq("article_id", comment.getArticleId());
        Integer count = this.baseMapper.selectCount(countWrapper);
        commentVo.setCommentCounts(count);

        return commentVo;
    }


    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id)
                .eq(Comment::getLevel, 2);
        return copyList(this.baseMapper.selectList(queryWrapper));
    }
}
