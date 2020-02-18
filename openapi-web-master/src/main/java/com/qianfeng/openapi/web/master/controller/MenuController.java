package com.qianfeng.openapi.web.master.controller;

import com.qianfeng.openapi.web.master.pojo.Menu;
import com.qianfeng.openapi.web.master.service.MenuService;
import com.qianfeng.openapi.web.master.util.AjaxMessage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping(params = "act=list")
    @RequiresPermissions("sys:menu:list")
    public List<Menu> list() {
        return menuService.getMenuList();
    }

    /**
     * 菜单树，在添加和修改页面选择父菜单用
     * @return
     */
    @RequiresPermissions({"sys:menu:add","sys:menu:update"})
    @RequestMapping(params = "act=tree")
    public List<Menu> tree() {
        return menuService.getMenuTree();
    }

    @RequestMapping(params = "act=delete")
    @RequiresPermissions("sys:menu:delete")
    public AjaxMessage delete(@RequestBody Integer[] ids) {
        try {
            menuService.deleteMenus(ids);
            return new AjaxMessage(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxMessage(true, "删除失败");
        }
    }

    @RequestMapping(params = "act=info")
    @RequiresPermissions("sys:menu:update")
    public Menu goEdit(Integer id) {
        return menuService.getMenuById(id);
    }

    @RequestMapping(params = "act=add")
    @RequiresPermissions("sys:menu:add")
    public AjaxMessage add(@RequestBody Menu menu) {
        try {
            menuService.addMenu(menu);
            return new AjaxMessage(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxMessage(true, "添加失败");
        }
    }
    @RequiresPermissions("sys:menu:update")
    @RequestMapping(params = "act=update")
    public AjaxMessage update(@RequestBody Menu menu) {
        try {
            menuService.updateMenu(menu);
            return new AjaxMessage(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxMessage(true, "修改失败");
        }
    }
}
