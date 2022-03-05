package cn.icatw.blog.controller;

import cn.icatw.blog.service.SysUserService;
import cn.icatw.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/currentUser")
    public Result getUserInfo(@RequestHeader("Authorization") String token) {
        return sysUserService.findUserByToken(token);
    }
}
