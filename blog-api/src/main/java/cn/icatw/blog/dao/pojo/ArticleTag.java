package cn.icatw.blog.dao.pojo;

import lombok.Data;

/**
 * @author icatw
 * @date 2022/3/3
 * @apiNote
 */
@Data
public class ArticleTag {
    private Long id;
    private Long articleId;
    private Long tagId;
}
