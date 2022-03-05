package cn.icatw.blog.service;

import cn.icatw.blog.dao.pojo.Category;
import cn.icatw.blog.vo.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author icatw
 * @date 2022/3/3
 * @apiNote
 */
public interface CategoryService extends IService<Category> {
    /**
     * 查询所有分类
     * @return
     */
    Result findAll();

    /**
     * 查询所有分类 包括详情
     * @return
     */
    Result findAllDetail();

    /**
     * 根据id获取自己的分类详情
     * @param id
     * @return
     */
    Result findDetailById(Long id);
}
