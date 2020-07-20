package com.greenism.acl.controller;


import com.greenism.acl.entity.Permission;
import com.greenism.acl.service.PermissionService;
import com.greenism.commonutils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public Result indexAllPermission() {
        List<Permission> list =  permissionService.queryAllMenuGuli();
        return Result.ok().data("children",list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        permissionService.removeChildByIdGuli(id);
        return Result.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public Result doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
        return Result.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public Result toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return Result.ok().data("children", list);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public Result save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public Result updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok();
    }

    //查询所有菜单
    @GetMapping("/getAllPermissions")
    public Result getAllPermissions(){
        List<Permission> list = permissionService.getAllPermissions();
        return Result.ok().data("permissions",list);
    }

    //递归删除菜单
    @DeleteMapping("/deletePermission/{id}")
    public Result deletePermission(@PathVariable String id){
        permissionService.deletePermission(id);
        return Result.ok();
    }

    //为角色添加菜单
    @PostMapping("/addPermissionsForRole/{roleId}")
    public Result addPermissionsForRole(@PathVariable String roleId,String[] permissionIds){
        permissionService.addPermissionsForRole(roleId,permissionIds);
        return Result.ok();
    }

}

