package cn.icatw.blog.vo.params;

import lombok.Data;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote
 */
@Data
public class LoginParams {
    private String account;
    private String password;
    private String nickname;
}
