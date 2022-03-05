package cn.icatw.blog.vo;

import lombok.Data;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote "id": 5,
 * "avatar": null,
 * "tagName": "444"
 */
@Data
public class TagVo {
    private String id;
    private String tagName;
    private String avatar;
}
