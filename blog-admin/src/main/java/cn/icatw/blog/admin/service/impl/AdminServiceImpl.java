package cn.icatw.blog.admin.service.impl;

import cn.icatw.blog.admin.mapper.AdminMapper;
import cn.icatw.blog.admin.pojo.Admin;
import cn.icatw.blog.admin.pojo.Permission;
import cn.icatw.blog.admin.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author icatw
 * @date 2022/3/4
 * @apiNote
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Override
    public Admin findAdminByUsername(String username) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, username).last("limit 1");
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public List<Permission> findPermissionByAdminId(Long adminId) {
        return this.baseMapper.findPermissionByAdminId(adminId);
    }
}
