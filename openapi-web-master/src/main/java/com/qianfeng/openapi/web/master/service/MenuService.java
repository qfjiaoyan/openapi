package com.qianfeng.openapi.web.master.service;


import com.qianfeng.openapi.web.master.pojo.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {
    List<Menu> getMenuTree();

    List<Menu> getMenuList();

    List<Menu> getFullMenuTree();

    void deleteMenus(Integer[] ids);

    void addMenu(Menu menu);

    Menu getMenuById(Integer id);

    void updateMenu(Menu menu);

    Map<String, Object> getUserPermission(Integer userId);

}
