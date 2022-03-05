package cn.icatw.blog.admin.controller;

import cn.icatw.blog.admin.model.params.PageParam;
import cn.icatw.blog.admin.pojo.Permission;
import cn.icatw.blog.admin.service.PermissionService;
import cn.icatw.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author icatw
 * @date 2022/3/4
 * @apiNote
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping("/permission/permissionList")
    public Result listPermissions(@RequestBody PageParam pageParam) {
        return permissionService.listPermissions(pageParam);
    }

    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission) {
        boolean save = permissionService.save(permission);
        return Result.success(null);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission) {
        boolean b = permissionService.updateById(permission);
        return Result.success(null);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id) {
        boolean b = permissionService.removeById(id);
        return Result.success(b);
    }
}

