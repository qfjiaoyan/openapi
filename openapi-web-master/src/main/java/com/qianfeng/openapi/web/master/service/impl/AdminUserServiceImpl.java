package com.qianfeng.openapi.web.master.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qianfeng.openapi.web.master.dao.AdminUserDAO;
import com.qianfeng.openapi.web.master.pojo.AdminUser;
import com.qianfeng.openapi.web.master.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserDAO adminUserDAO;


    @Override
    public PageInfo<AdminUser> getUserList(AdminUser user, Integer offset, Integer pageSize) {
        PageHelper.offsetPage(offset, pageSize);
        return new PageInfo<>(adminUserDAO.getUserList(user));
    }

    @Override
    public void addUserRole(Integer userId, Integer[] roleIds) {
        adminUserDAO.deleteUserRole(userId);
        for (Integer roleId : roleIds) {
            adminUserDAO.addUserRole(roleId, userId);
        }
    }

    @Override
    public List<Integer> getUserRoleIds(Integer userId) {
        return adminUserDAO.getUserRoleIds(userId);
    }

    @Override
    public void addUser(AdminUser user) {
        adminUserDAO.addUser(user);
    }

    @Override
    public void updateUser(AdminUser user) {
        adminUserDAO.updateUser(user);
    }

    @Override
    public AdminUser getUserById(Integer id) {
        return adminUserDAO.getUserById(id);
    }

    @Override
    public void deleteUser(int[] ids) {
        AdminUser user=new AdminUser();
        //逻辑删除
        user.setStatus(0);
        for (int id : ids) {
            user.setId(id);
            adminUserDAO.updateUser(user);
        }
    }
}
