package cn.icatw.blog.handler;

import cn.icatw.blog.dao.pojo.SysUser;
import cn.icatw.blog.service.LoginService;
import cn.icatw.blog.utils.UserThreadLocal;
import cn.icatw.blog.vo.ErrorCode;
import cn.icatw.blog.vo.Result;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote 登陆拦截器
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在执行controller方法（handler）之前进行执行
        /**
         * 1、需要判断 请求的接口路径是否为HandlerMethod（controller中的方法）
         * 2、判断token是否为空 如果为空 未登陆
         * 3、如果token 不为空 登陆验证 检查token  loginService checkToken
         */
        if (!(handler instanceof HandlerMethod)) {
            //handler 可能是 RequestResourceHandler springboot程序 访问静态资源 默认去classpath下的static目录
            return true;
        }
        String token = request.getHeader("Authorization");

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}", requestURI);
        log.info("request method:{}", request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if (StringUtils.isBlank(token)) {
            //用response给页面返回错误信息的json数据
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            //用response给页面返回错误信息的json数据
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //登陆验证成功 返回true
        //父类接口默认为true
        //将sysUser放入本地线程池
        UserThreadLocal.put(sysUser);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //用完之后将本地内存池的信息删除，否则会有内存泄露的风险
        UserThreadLocal.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
