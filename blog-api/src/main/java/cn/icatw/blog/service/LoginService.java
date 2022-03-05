package cn.icatw.blog.service;

import cn.icatw.blog.dao.pojo.SysUser;
import cn.icatw.blog.vo.Result;
import cn.icatw.blog.vo.params.LoginParams;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote
 */
public interface LoginService {
    /**
     * 登陆
     *
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);

    /**
     * 校验token
     *
     * @param token
     * @return
     */
    SysUser checkToken(String token);

    /**
     * 退出登陆 清除token
     *
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     *
     * @param loginParams
     * @return
     */
    Result register(LoginParams loginParams);
}
