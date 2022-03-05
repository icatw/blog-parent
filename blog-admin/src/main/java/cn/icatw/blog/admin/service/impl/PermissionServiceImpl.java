package cn.icatw.blog.admin.service.impl;

import cn.icatw.blog.admin.mapper.PermissionMapper;
import cn.icatw.blog.admin.model.params.PageParam;
import cn.icatw.blog.admin.pojo.Permission;
import cn.icatw.blog.admin.service.PermissionService;
import cn.icatw.blog.admin.vo.PageResult;
import cn.icatw.blog.admin.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author icatw
 * @date 2022/3/4
 * @apiNote
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public Result listPermissions(PageParam pageParam) {
        /**
         * 表的所有字段 permission
         * 分页查询
         */
        Page<Permission> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageParam.getQueryString())) {
            wrapper.eq(Permission::getName, pageParam.getQueryString());
        }
        Page<Permission> permissionPage = this.baseMapper.selectPage(page, wrapper);
        PageResult<Permission> pageResult = new PageResult<>();

        pageResult.setList(permissionPage.getRecords());
        pageResult.setTotal(permissionPage.getTotal());
        return Result.success(pageResult);
    }
}
