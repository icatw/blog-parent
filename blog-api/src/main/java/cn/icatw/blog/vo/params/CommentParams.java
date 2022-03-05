package cn.icatw.blog.vo.params;

import lombok.Data;

/**
 * @author icatw
 * @date 2022/3/3
 * @apiNote
 */
@Data
public class CommentParams {
    private Long articleId;
    private String content;
    private Long parent;
    private Long toUserId;
}
