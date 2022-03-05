package cn.icatw.blog.service.impl;

import cn.icatw.blog.dao.mapper.SysUserMapper;
import cn.icatw.blog.dao.pojo.SysUser;
import cn.icatw.blog.service.LoginService;
import cn.icatw.blog.service.SysUserService;
import cn.icatw.blog.vo.ErrorCode;
import cn.icatw.blog.vo.LoginUserVo;
import cn.icatw.blog.vo.Result;
import cn.icatw.blog.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private LoginService loginService;

    @Override
    public SysUser findUserById(Long authorId) {
        SysUser sysUser = this.baseMapper.selectById(authorId);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("王顺");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.eq(SysUser::getPassword, password);
        queryWrapper.select(SysUser::getAccount, SysUser::getId, SysUser::getAvatar, SysUser::getNickname);
        queryWrapper.last("limit 1");
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        /**
         * 1、token合法性校验
         *  是否为空，解析是否成功 redis是否存在
         *  2、如果校验失败 返回错误
         *  3、如果成功 返回对应的结果 LoginUserVo
         */

        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(String.valueOf(sysUser.getId()));
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("account", account).last("limit 1");
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public UserVo findUserVoByAuthorId(Long authorId) {
        SysUser sysUser = this.baseMapper.selectById(authorId);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("王顺");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser, userVo);
        userVo.setId(String.valueOf(sysUser.getId()));
        return userVo;
    }
}
