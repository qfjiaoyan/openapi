package com.qianfeng.openapi.web.master.controller;

import com.qianfeng.openapi.web.master.pojo.AppInfo;
import com.qianfeng.openapi.web.master.service.AppInfoService;
import com.qianfeng.openapi.web.master.util.AjaxMessage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 应用管理
 */
@RestController
@RequestMapping("/appinfo")
public class AppInfoController {

    @Autowired
    private AppInfoService appInfoService;

    @RequiresPermissions("sys:app:list")
    @RequestMapping(params = "act=table")
    public List<AppInfo> table(AppInfo info) {
        return appInfoService.getInfoList(info);
    }


    @RequiresPermissions("sys:app:update")
    @RequestMapping(params = "act=update")
    public AjaxMessage update(@RequestBody Map<String,String> info) {
        try {
            appInfoService.updateAppInfo(info);
            return new AjaxMessage(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxMessage(false, "修改失败");
    }

    @RequiresPermissions("sys:app:update")
    @RequestMapping(params = "act=info")
    public AppInfo info(Integer id) {
        return appInfoService.getInfoById(id);
    }

}
