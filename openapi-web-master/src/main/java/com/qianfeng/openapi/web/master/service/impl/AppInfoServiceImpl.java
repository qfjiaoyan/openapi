package com.qianfeng.openapi.web.master.service.impl;

import com.qianfeng.openapi.web.master.dao.AppInfoDAO;
import com.qianfeng.openapi.web.master.pojo.AppInfo;
import com.qianfeng.openapi.web.master.service.AppInfoService;
import com.qianfeng.openapi.web.master.service.api.CacheService;
import com.qianfeng.openapi.web.master.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AppInfoServiceImpl implements AppInfoService {
    @Autowired
    private AppInfoDAO appInfoDAO;

    @Autowired
    private CacheService cacheService;
    public final String CACHE_API = "APPKEY:";

    @Override
    public List<AppInfo> getSimpleInfoList() {
        return appInfoDAO.getSimpleInfoList();
    }

    @Override
    public void updateAppInfo(Map<String,String> appInfo) {
        appInfoDAO.updateAppInfo(appInfo);
        cacheService.hmset(CACHE_API + appInfo.get("appKey"),appInfo);
    }

    @Override
    public List<AppInfo> getInfoList(AppInfo info) {
        return appInfoDAO.getInfoList(info);
    }

    @Override
    public AppInfo getInfoById(int id) {
        return appInfoDAO.getInfoById(id);
    }
}
