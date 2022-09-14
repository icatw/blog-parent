package cn.icatw.blog.service.impl;

import cn.icatw.blog.dao.mapper.MusicMapper;
import cn.icatw.blog.dao.pojo.Music;
import cn.icatw.blog.service.MusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author icatw
 * @date 2022/9/14
 * @email 762188827@qq.com
 * @apiNote
 */
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {
}
