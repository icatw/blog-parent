package cn.icatw.blog.service;

import cn.icatw.blog.dao.pojo.SysUser;
import cn.icatw.blog.vo.Result;
import cn.icatw.blog.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据文章id查询作者
     * @param authorId
     * @return
     */
    SysUser findUserById(Long authorId);

    /**
     * 查询登陆用户
     * @param account
     * @param password
     * @return
     */
    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 注册前判断 用户是否存在
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 通过作者id获取作者vo对象
     * @param authorId
     * @return
     */
    UserVo findUserVoByAuthorId(Long authorId);
}
