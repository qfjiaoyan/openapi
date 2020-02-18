package com.qianfeng.openapi.web.master.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qianfeng.openapi.web.master.dao.AdminUserDAO;
import com.qianfeng.openapi.web.master.dao.RoleDAO;
import com.qianfeng.openapi.web.master.pojo.Role;
import com.qianfeng.openapi.web.master.service.RoleService;
import com.qianfeng.openapi.web.master.shiro.MyShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private AdminUserDAO adminUserDAO;

    @Override
    public PageInfo<Role> getRoleList(Role role, Integer offset, Integer pageSize) {
        PageHelper.offsetPage(offset, pageSize);
        return new PageInfo<>(roleDAO.getRoleList(role));
    }

    @Autowired
    private MyShiroFilterFactoryBean shiroFilterFactoryBean;

    @Override
    public void addRoleMenu(Integer roleId, Integer[] menuIds) {
        //多对多的处理，先删中间表，再插入
        roleDAO.deleteRoleMenuByRoleId(roleId);
        for (Integer menuId : menuIds) {
            roleDAO.addRoleMenu(roleId, menuId);
        }
        shiroFilterFactoryBean.update();//动态刷新权限
    }

    @Override
    public void addRole(Role role) {
        roleDAO.addRole(role);
    }

    @Override
    public void updateRole(Role role) {
        roleDAO.updateRole(role);
    }

    /**
     * 根据角色id获得对应的权限，用于授权页面，选中已有权限
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> getRoleMenuIds(Integer roleId) {
        return roleDAO.getRoleMenuIds(roleId);
    }

    @Override
    public List<Role> getRoleList(Role role) {
        return roleDAO.getRoleList(role);
    }

    @Override
    public void deleteRole(int[] ids) {
        Role role = new Role();
        //逻辑删除，把状态改成无效
        role.setStatus(0);
        for (int id : ids) {
            roleDAO.deleteRoleMenuByRoleId(id);
            adminUserDAO.deleteUserRole(id);
            role.setId(id);
            roleDAO.updateRole(role);
        }
    }

    @Override
    public Role getRoleId(Integer id) {
        return roleDAO.getRoleId(id);
    }
}
