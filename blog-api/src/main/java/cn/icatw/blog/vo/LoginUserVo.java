package cn.icatw.blog.vo;

import lombok.Data;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote
 */
@Data
public class LoginUserVo {

    private String id;

    private String account;

    private String nickname;

    private String avatar;
}
