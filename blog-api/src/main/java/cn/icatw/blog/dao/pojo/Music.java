package cn.icatw.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author icatw
 * @date 2022/9/14
 * @email 762188827@qq.com
 * @apiNote
 */
@Data
public class Music {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String artist;
    private String url;
    private String cover;
}
