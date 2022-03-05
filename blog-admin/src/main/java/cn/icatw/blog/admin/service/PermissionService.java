package cn.icatw.blog.admin.service;

import cn.icatw.blog.admin.model.params.PageParam;
import cn.icatw.blog.admin.pojo.Permission;
import cn.icatw.blog.admin.vo.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author icatw
 * @date 2022/3/4
 * @apiNote
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 权限列表
     *
     * @param pageParam
     * @return
     */
    Result listPermissions(PageParam pageParam);
}
