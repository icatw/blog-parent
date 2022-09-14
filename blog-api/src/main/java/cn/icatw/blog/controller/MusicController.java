package cn.icatw.blog.controller;

import cn.icatw.blog.dao.pojo.Music;
import cn.icatw.blog.service.MusicService;
import cn.icatw.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author icatw
 * @date 2022/9/14
 * @email 762188827@qq.com
 * @apiNote
 */
@RestController
@RequestMapping("/music")
public class MusicController {
    @Autowired
    MusicService musicService;

    @GetMapping
    public Result getMusicList() {
        List<Music> list = musicService.list();
        return Result.success(list);
    }
}
