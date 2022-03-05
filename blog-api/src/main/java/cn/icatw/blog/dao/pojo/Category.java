package cn.icatw.blog.dao.pojo;

import lombok.Data;

/**
 * 文章分类
 *
 * @author 76218
 */
@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
