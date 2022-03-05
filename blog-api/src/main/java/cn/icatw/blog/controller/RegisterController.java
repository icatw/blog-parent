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
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParams loginParams) {
        //sso 单点登陆，后期如果把登陆注册功能提取出去（单独的服务，可以独立提供接口服务）
        return loginService.register(loginParams);
    }
}
