package com.qianfeng.openapi.web.master.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qianfeng.openapi.web.master.dao.ApiMappingDAO;
import com.qianfeng.openapi.web.master.pojo.ApiMapping;
import com.qianfeng.openapi.web.master.service.ApiMappingService;
import com.qianfeng.openapi.web.master.service.api.CacheService;
import com.qianfeng.openapi.web.master.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApiMappingServiceImpl implements ApiMappingService {
    @Autowired
    private ApiMappingDAO apiMappingDAO;

    @Autowired
    private CacheService cacheService;
    private final String GATEWAY_REDIS_KEY = "APINAME:";

    @Override
    public void addApiMapping(Map mapping) {
        apiMappingDAO.addApiMapping(mapping);
        cacheService.hmset(GATEWAY_REDIS_KEY + mapping.get("gatewayApiName"), mapping);
    }

    @Override
    public void updateApiMapping(Map mapping) {
        apiMappingDAO.updateApiMapping(mapping);
        cacheService.hmset(GATEWAY_REDIS_KEY + mapping.get("gatewayApiName"), mapping);
    }

    @Override
    public PageInfo<ApiMapping> getMappingList(ApiMapping criteria, int offset, int limit) {
        PageHelper.offsetPage(offset, limit);
        return new PageInfo<>(apiMappingDAO.getMappingList(criteria));
    }

    @Override
    public ApiMapping getMappingById(int id) {
        return apiMappingDAO.getMappingById(id);
    }

    @Override
    public void deleteMapping(int[] ids) {
        if (ids == null || ids.length == 0) {
            return;
        }
        for (int id : ids) {
            ApiMapping mapping = apiMappingDAO.getMappingById(id);
            Map temp = new HashMap();
            temp.put("id", id);
            temp.put("state", 0);
            cacheService.del(GATEWAY_REDIS_KEY + mapping.getGatewayApiName());
            apiMappingDAO.updateApiMapping(temp);
        }

    }
}
