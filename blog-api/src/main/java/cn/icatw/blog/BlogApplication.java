package cn.icatw.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote
 */
@SpringBootApplication
@MapperScan(basePackages = "cn.icatw.blog.dao.mapper")
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}
