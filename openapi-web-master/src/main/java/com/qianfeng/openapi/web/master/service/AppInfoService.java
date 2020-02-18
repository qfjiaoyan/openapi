package com.qianfeng.openapi.web.master.service;

import com.qianfeng.openapi.web.master.pojo.AppInfo;

import java.util.List;
import java.util.Map;

public interface AppInfoService {
    List<AppInfo> getSimpleInfoList();

    void updateAppInfo(Map<String,String> appInfo);

    List<AppInfo> getInfoList(AppInfo info);

    AppInfo getInfoById(int id);
}
