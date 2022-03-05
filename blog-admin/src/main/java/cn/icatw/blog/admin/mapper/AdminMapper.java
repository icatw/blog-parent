package cn.icatw.blog.admin.mapper;

import cn.icatw.blog.admin.pojo.Admin;
import cn.icatw.blog.admin.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author icatw
 * @date 2022/3/4
 * @apiNote
 */
public interface AdminMapper extends BaseMapper<Admin> {
    /**
     * 根据adminId查询权限
     *
     * @param adminId
     * @return
     */
    @Select("select * from ms_permission where id in (select permission_id from ms_admin_permission where admin_id=#{adminId})")
    List<Permission> findPermissionByAdminId(Long adminId);
}
