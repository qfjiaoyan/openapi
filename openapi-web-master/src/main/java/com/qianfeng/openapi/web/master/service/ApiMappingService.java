package com.qianfeng.openapi.web.master.service;

import com.github.pagehelper.PageInfo;
import com.qianfeng.openapi.web.master.pojo.ApiMapping;

import java.util.Map;

public interface ApiMappingService {
    void addApiMapping(Map mapping);

    void updateApiMapping(Map mapping);

    PageInfo<ApiMapping> getMappingList(ApiMapping criteria, int offset, int limit);

    ApiMapping getMappingById(int id);

    void deleteMapping(int[] ids);
}
