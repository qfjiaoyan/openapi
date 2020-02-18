package com.qianfeng.openapi.web.master.controller;

import com.github.pagehelper.PageInfo;
import com.qianfeng.openapi.web.master.pojo.AdminUser;
import com.qianfeng.openapi.web.master.pojo.ApiMapping;
import com.qianfeng.openapi.web.master.service.ApiMappingService;
import com.qianfeng.openapi.web.master.util.AjaxMessage;
import com.qianfeng.openapi.web.master.util.TableData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 路由管理
 */
@RestController
@RequestMapping("/system/gateway")
public class ApiMappingController {

    @Autowired
    private ApiMappingService apiMappingService;

    @RequiresPermissions("sys:api:list")
    @RequestMapping(params = "act=table")
    public TableData table(ApiMapping apiMapping, Integer offset, Integer limit) {
        PageInfo<ApiMapping> pageInfo = apiMappingService.getMappingList(apiMapping, offset, limit);
        return new TableData(pageInfo.getTotal(), pageInfo.getList());
    }

    @RequiresPermissions("sys:api:add")
    @RequestMapping(params = "act=add")
    public AjaxMessage add(@RequestBody Map<String, String> apiMapping) {
        try {
            apiMappingService.addApiMapping(apiMapping);
            return new AjaxMessage(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxMessage(false, "添加失败");
    }

    @RequiresPermissions("sys:api:update")
    @RequestMapping(params = "act=update")
    public AjaxMessage update(@RequestBody Map<String, String> apiMapping) {
        try {
            apiMappingService.updateApiMapping(apiMapping);
            return new AjaxMessage(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxMessage(false, "修改失败");
    }

    @RequiresPermissions("sys:api:update")
    @RequestMapping(params = "act=info")
    public ApiMapping info(Integer id) {
        return apiMappingService.getMappingById(id);
    }

    @RequiresPermissions("sys:api:delete")
    @RequestMapping(params = "act=del")
    public AjaxMessage delete(@RequestBody int[] ids) {
        try {
            apiMappingService.deleteMapping(ids);
            return new AjaxMessage(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxMessage(false, "删除失败");
    }
}
