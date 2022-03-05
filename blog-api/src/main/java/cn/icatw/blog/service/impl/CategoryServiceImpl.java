package cn.icatw.blog.service.impl;

import cn.icatw.blog.dao.mapper.CategoryMapper;
import cn.icatw.blog.dao.pojo.Category;
import cn.icatw.blog.service.CategoryService;
import cn.icatw.blog.vo.CategoryVo;
import cn.icatw.blog.vo.Result;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author icatw
 * @date 2022/3/3
 * @apiNote
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Override
    public Result findAll() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Category::getId, Category::getCategoryName);
        List<Category> categories = this.baseMapper.selectList(wrapper);
        return Result.success(copyList(categories));
    }

    @Override
    public Result findAllDetail() {
        List<Category> categories = this.baseMapper.selectList(null);
        return Result.success(copyList(categories));
    }

    @Override
    public Result findDetailById(Long id) {
        Category category = this.baseMapper.selectById(id);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        categoryVo.setId(String.valueOf(category.getId()));
        return Result.success(categoryVo);
    }


    private CategoryVo copy(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        categoryVo.setId(String.valueOf(category.getId()));
        return categoryVo;
    }

    private List<CategoryVo> copyList(List<Category> categoryList) {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryVoList.add(copy(category));
        }
        return categoryVoList;
    }
}
