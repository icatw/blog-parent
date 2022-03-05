package cn.icatw.blog.controller;

import cn.icatw.blog.service.TagService;
import cn.icatw.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote
 */
@RestController
@RequestMapping("/tags")
public class TagsController {
    @Autowired
    private TagService tagService;

    @GetMapping("/hot")
    public Result getHotTags() {
        int limit = 6;
        return tagService.getHotTags(limit);
    }
    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }

    @GetMapping("/detail")
    public Result detail(){
        return tagService.findAllDetail();
    }
    @GetMapping("/detail/{id}")
    public Result getDetailById(@PathVariable Long id){
        return tagService.findDetailById(id);
    }
}
