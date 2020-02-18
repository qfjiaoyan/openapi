package com.qianfeng.openapi.web.master.dao;

import com.qianfeng.openapi.web.master.pojo.AppInfo;

import java.util.List;
import java.util.Map;

public interface AppInfoDAO {
    List<AppInfo> getSimpleInfoList();

    void updateAppInfo(Map appInfo);

    List<AppInfo> getInfoList(AppInfo appInfo);

    AppInfo getInfoById(int id);
}
