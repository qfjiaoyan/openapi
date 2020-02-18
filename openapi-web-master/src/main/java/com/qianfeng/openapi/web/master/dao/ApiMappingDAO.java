package com.qianfeng.openapi.web.master.dao;

import com.qianfeng.openapi.web.master.pojo.ApiMapping;

import java.util.List;
import java.util.Map;

public interface ApiMappingDAO {

    void addApiMapping(Map mapping);

    void updateApiMapping(Map mapping);

    List<ApiMapping> getMappingList(ApiMapping criteria);

    ApiMapping getMappingById(int id);
}
