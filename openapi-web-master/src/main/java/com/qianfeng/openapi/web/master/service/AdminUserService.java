package com.qianfeng.openapi.web.master.service;


import com.github.pagehelper.PageInfo;
import com.qianfeng.openapi.web.master.pojo.AdminUser;

import java.util.List;

public interface AdminUserService {

    PageInfo<AdminUser> getUserList(AdminUser user, Integer offset, Integer pageSize);

    void addUserRole(Integer userId, Integer[] roleIds);

    List<Integer> getUserRoleIds(Integer userId);

    void addUser(AdminUser user);

    void updateUser(AdminUser user);

    AdminUser getUserById(Integer id);

    void deleteUser(int[] ids);
}
