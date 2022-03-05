package cn.icatw.blog.dao.pojo;

import lombok.Data;

/**
 * 文章内容
 * @author 76218
 */
@Data
public class ArticleBody {

    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}