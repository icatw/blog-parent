package cn.icatw.blog.service.impl;

import cn.icatw.blog.dao.mapper.TagMapper;
import cn.icatw.blog.dao.pojo.Tag;
import cn.icatw.blog.service.TagService;
import cn.icatw.blog.vo.Result;
import cn.icatw.blog.vo.TagVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author icatw
 * @date 2022/3/2
 * @apiNote
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    //@Autowired
    //private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        //wrapper.eq("article_id", articleId);
        //List<Tag> tagList = this.baseMapper.selectList(wrapper);
        List<Tag> tags = this.baseMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public Result getHotTags(int limit) {
        /**
         * 1、标签所拥有的文章数量最多（根据标签id查询文章id数量并进行排序）
         * 2、查询 根据tag_id 分组 计数，从大到小 排列 去前limit个
         *
         */
        List<Long> tagIds = this.baseMapper.findHotTags(limit);
        if (CollectionUtils.isEmpty(tagIds)) {
            return Result.success(Collections.emptyList());
        }
        List<Tag> tagList = this.baseMapper.findTagsByTagIds(tagIds);
        return Result.success(tagList);
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> wrapper=new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tagList = this.baseMapper.selectList(wrapper);

        return Result.success(copyList(tagList));
    }

    @Override
    public Result findAllDetail() {
        List<Tag> tagList = this.baseMapper.selectList(null);

        return Result.success(copyList(tagList));
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = this.baseMapper.selectById(id);
        TagVo copy = copy(tag);
        return Result.success(copy);
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }

    private List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }
}
