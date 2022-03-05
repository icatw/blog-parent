package cn.icatw.blog.controller;

import cn.icatw.blog.service.LoginService;
import cn.icatw.blog.vo.Result;
import cn.icatw.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParams loginParams) {
        return loginService.login(loginParams);
    }
}
