package com.qianfeng.openapi.web.master.controller;

import com.github.pagehelper.PageInfo;
import com.qianfeng.openapi.web.master.pojo.AdminUser;
import com.qianfeng.openapi.web.master.pojo.Role;
import com.qianfeng.openapi.web.master.service.AdminUserService;
import com.qianfeng.openapi.web.master.service.RoleService;
import com.qianfeng.openapi.web.master.util.AjaxMessage;
import com.qianfeng.openapi.web.master.util.TableData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/system/user")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private RoleService roleService;

    @RequiresPermissions("sys:user:list")
    @RequestMapping(params = "act=table")
    public TableData table(AdminUser adminUser, Integer offset, Integer limit) {
        PageInfo<AdminUser> pageInfo = adminUserService.getUserList(adminUser, offset, limit);
        return new TableData(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 授权时的角色树
     */
    @RequiresPermissions("sys:user:assign")
    @RequestMapping(params = "act=role_tree")
    public List<Role> roleList() {
        return roleService.getRoleList(null);
    }

    /**
     * 获取用户已有的角色，回填角色树中的复选框
     * @param userId
     */
    @RequiresPermissions("sys:user:assign")
    @RequestMapping(params = "act=user_role")
    public List<Integer> userRole(Integer userId) {
        return adminUserService.getUserRoleIds(userId);
    }

    @RequiresPermissions("sys:user:assign")
    @RequestMapping(params = "act=assign_role")
    public AjaxMessage assignRole(Integer userId, Integer[] roleIds) {
        try {
            adminUserService.addUserRole(userId, roleIds);
            return new AjaxMessage(true, "分配成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxMessage(false, "分配失败");
    }

    @RequiresPermissions("sys:user:add")
    @RequestMapping(params = "act=add")
    public AjaxMessage add(@RequestBody AdminUser adminUser) {
        try {
            adminUserService.addUser(adminUser);
            return new AjaxMessage(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxMessage(false, "添加失败");
    }

    @RequiresPermissions("sys:user:update")
    @RequestMapping(params = "act=update")
    public AjaxMessage update(@RequestBody AdminUser adminUser) {
        try {
            adminUserService.updateUser(adminUser);
            return new AjaxMessage(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxMessage(false, "修改失败");
    }

    @RequiresPermissions("sys:user:update")
    @RequestMapping(params = "act=info")
    public AdminUser info(Integer id) {
        return adminUserService.getUserById(id);
    }

    @RequiresPermissions("sys:user:delete")
    @RequestMapping(params = "act=del")
    public AjaxMessage delete(@RequestBody int[] ids) {
        try {
            adminUserService.deleteUser(ids);
            return new AjaxMessage(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxMessage(false, "删除失败");
    }
}
