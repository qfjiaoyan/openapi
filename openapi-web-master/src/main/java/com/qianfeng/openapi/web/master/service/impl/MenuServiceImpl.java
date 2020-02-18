package com.qianfeng.openapi.web.master.service.impl;

import com.qianfeng.openapi.web.master.dao.MenuDAO;
import com.qianfeng.openapi.web.master.dao.RoleDAO;
import com.qianfeng.openapi.web.master.pojo.Menu;
import com.qianfeng.openapi.web.master.service.MenuService;
import com.qianfeng.openapi.web.master.shiro.MyShiroFilterFactoryBean;
import com.qianfeng.openapi.web.master.util.AdminConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDAO menuDAO;
    @Autowired
    private RoleDAO roleDAO;

    @Override
    public List<Menu> getMenuTree() {
        List<Menu> menus = menuDAO.getAllMenu();
        return makeMenuTree(menus);
    }

    /**
     * 封装不带按钮的菜单树，左边导航和选择父菜单时使用
     */
    private List<Menu> makeMenuTree(List<Menu> menus) {
        List<Menu> firstMenu = new ArrayList<>();
        Map<Integer, Menu> menuMap = new HashMap<>();
        for (Menu menu : menus) {
            menuMap.put(menu.getId(), menu);
            if (menu.getParentId() == null) {//一级菜单
                firstMenu.add(menu);
            }
        }
        for (Menu menu : menus) {
            if (menu.getType() != AdminConstants.MENU_TYPE_BUTTON) {//不是按钮
                if (menu.getParentId() != null && menuMap.containsKey(menu.getParentId())) {
                    menuMap.get(menu.getParentId()).getChildren().add(menu);
                }
            }
        }
        return firstMenu;
    }

    /**
     * 所有菜单组成的菜单树，管理菜单列表页使用
     * @return
     */
    @Override
    public List<Menu> getFullMenuTree() {
        List<Menu> menus = menuDAO.getAllMenu();
        List<Menu> firstMenu = new ArrayList<>();
        Map<Integer, Menu> menuMap = new HashMap<>();
        for (Menu menu : menus) {
            menuMap.put(menu.getId(), menu);
            if (menu.getParentId() == null) {//一级菜单
                firstMenu.add(menu);
            }
        }
        for (Menu menu : menus) {
            if (menu.getParentId() != null && menuMap.containsKey(menu.getParentId())) {
                menuMap.get(menu.getParentId()).getChildren().add(menu);
            }
        }
        return firstMenu;
    }

    @Override
    public void deleteMenus(Integer[] ids) {
        for (Integer id : ids) {
            menuDAO.updateParentId(id);//将子菜单变成一级菜单
            roleDAO.deleteRoleMenuByMenuId(id);//删除角色菜单
            menuDAO.deleteMenu(id);//删除菜单

        }
    }

    @Override
    public void addMenu(Menu menu) {
        menuDAO.addMenu(menu);
    }

    @Override
    public Menu getMenuById(Integer id) {
        return menuDAO.getMenuById(id);
    }

    @Autowired
    private MyShiroFilterFactoryBean shiroFilterFactoryBean;

    @Override
    public void updateMenu(Menu menu) {
        menuDAO.updateMenu(menu);
        shiroFilterFactoryBean.update();
    }

    @Override
    public Map<String, Object> getUserPermission(Integer userId) {
        List<Menu> menus = menuDAO.getUserMenu(userId);
        Map<String, Object> result = new HashMap<>();
        List<String> permissions = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getType() == AdminConstants.MENU_TYPE_BUTTON && StringUtils.hasText(menu.getPerms())) {
                permissions.add(menu.getPerms());
            }
        }
        result.put("menuList", makeMenuTree(menus));
        result.put("permissions", permissions);
        return result;
    }

    @Override
    public List<Menu> getMenuList() {
        List<Menu> menus = menuDAO.getAllMenu();
        List<Menu> firstMenu = new ArrayList<>();
        Map<Integer, Menu> menuMap = new HashMap<>();
        for (Menu menu : menus) {
            menuMap.put(menu.getId(), menu);
            if (menu.getParentId() == null) {//一级菜单
                firstMenu.add(menu);
            }
        }
        for (Menu menu : menus) {
            if (menu.getParentId() != null && menuMap.containsKey(menu.getParentId())) {
                menuMap.get(menu.getParentId()).getChildren().add(menu);
            }
        }
        System.out.println(menuMap);
        List<Menu> sortMenu = new ArrayList<>();
        makeMenuList(firstMenu, sortMenu);
        return sortMenu;
    }

    private void makeMenuList(List<Menu> menus, List<Menu> target) {
        for (Menu menu : menus) {
            target.add(menu);
            if (menu.getChildren().size() > 0) {
                makeMenuList(menu.getChildren(), target);
            }
        }
    }
}
