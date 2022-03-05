package cn.icatw.blog.admin.service;

import cn.icatw.blog.admin.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author icatw
 * @date 2022/3/4
 * @apiNote
 */
@Component
public class SecurityServiceImpl implements UserDetailsService {
    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //登录的时候会把username传递到这里
        //通过username查询admin表
        Admin admin = adminService.findAdminByUsername(username);
        if (admin == null) {
            //登陆失败
            return null;
        }
        UserDetails userDetails = new User(username, admin.getPassword(), new ArrayList<>());

        return userDetails;
    }
}
