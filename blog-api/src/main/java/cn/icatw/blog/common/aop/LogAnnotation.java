package cn.icatw.blog.common.aop;

import java.lang.annotation.*;

/**
 * @author icatw
 * @date 2022/3/3
 * @apiNote 日志记录注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String module() default "";

    String operator() default "";
}
