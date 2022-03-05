package cn.icatw.blog.admin.service;

import cn.icatw.blog.admin.pojo.Admin;
import cn.icatw.blog.admin.pojo.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author icatw
 * @date 2022/3/4
 * @apiNote
 */
public interface AdminService extends IService<Admin> {
    /**
     * 通过username查询admin
     * @param username
     * @return
     */
    Admin findAdminByUsername(String username);

    /**
     * 根据adminId查询对应的权限
     * @param adminId
     * @return
     */
    List<Permission> findPermissionByAdminId(Long adminId);
}
