package cn.icatw.blog.controller;

import cn.icatw.blog.service.CommentsService;
import cn.icatw.blog.vo.Result;
import cn.icatw.blog.vo.params.CommentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author icatw
 * @date 2022/3/3
 * @apiNote 评论
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    @GetMapping("/article/{id}")
    public Result comments(@PathVariable("id") Long articleId) {
        return commentsService.getCommentsById(articleId);
    }

    @PostMapping("/create/change")
    public Result addComment(@RequestBody CommentParams commentParams) {
        return commentsService.addComment(commentParams);
    }
}
