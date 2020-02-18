package com.qianfeng.openapi.web.master.controller;

import com.qianfeng.openapi.web.master.dao.ApiMappingDAO;
import com.qianfeng.openapi.web.master.dao.AppInfoDAO;
import com.qianfeng.openapi.web.master.dao.UserTokenDAO;
import com.qianfeng.openapi.web.master.pojo.ApiMapping;
import com.qianfeng.openapi.web.master.pojo.AppInfo;
import com.qianfeng.openapi.web.master.pojo.UserToken;
import com.qianfeng.openapi.web.master.service.api.CacheService;
import com.qianfeng.openapi.web.master.service.api.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class InitController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private UserTokenDAO userTokenDAO;

    @Autowired
    private AppInfoDAO appInfoDAO;

    @Autowired
    private ApiMappingDAO apiMappingDAO;

    private final String GATEWAY_REDIS_KEY = "APINAME:";
    private final String TOKEN_KEY = "TOKEN:";
    public final String CACHE_API = "APPKEY:";

    @RequestMapping("/initopenapi")
    public Map<String ,Object> initOpenApi(){
        Map<String ,Object> map = new HashMap<>();
        //创建索引库
        int createindex = searchService.createindex();
        map.put("code",0);
        if(createindex>0){
            map.put("createindex","初始化索引成功");
        }else {
            map.put("createindex","初始化索引失败");
        }
        try {
            //app信息
            AppInfo pojo = new AppInfo();
            List<AppInfo> simpleInfoList = appInfoDAO.getInfoList(pojo);
            for (AppInfo appInfo : simpleInfoList) {
                Map<String,String> mapinfo = new HashMap<String,String>();
                mapinfo.put("id",String.valueOf(appInfo.getId()));
                mapinfo.put("corpName",appInfo.getCorpName());
                mapinfo.put("appName",appInfo.getAppName());
                mapinfo.put("appKey",appInfo.getAppKey());
                mapinfo.put("appSecret",appInfo.getAppSecret());
                mapinfo.put("redirectUrl",appInfo.getRedirectUrl());
                mapinfo.put("limit",String.valueOf(appInfo.getLimit()));
                mapinfo.put("description",appInfo.getDescription());
                cacheService.hmset(CACHE_API + appInfo.getAppKey(),mapinfo);
            }
            //路由信息
            ApiMapping criteria = new ApiMapping();
            criteria.setState(1);
            List<ApiMapping> mappingList = apiMappingDAO.getMappingList(criteria);
            for (ApiMapping apiMapping : mappingList) {
                Map<String,String> mapinfo = new HashMap<String,String>();
                mapinfo.put("id",String.valueOf(apiMapping.getId()));
                mapinfo.put("gatewayApiName",apiMapping.getGatewayApiName());
                mapinfo.put("insideApiUrl",apiMapping.getInsideApiUrl());
                mapinfo.put("state",String.valueOf(apiMapping.getState()));
                mapinfo.put("description",apiMapping.getDescription());
                mapinfo.put("serviceId",apiMapping.getServiceId());
                cacheService.hmset(GATEWAY_REDIS_KEY + apiMapping.getGatewayApiName(), mapinfo);
            }

            //token信息
            UserToken userToken = new UserToken();
            List<UserToken> tokenList = userTokenDAO.getTokenList(userToken);
            for (UserToken token : tokenList) {
                Map<String,String> mapinfo = new HashMap<String,String>();
                mapinfo.put("id",String.valueOf(token.getId()));
                mapinfo.put("appId",String.valueOf(token.getAppId()));
                mapinfo.put("userId",String.valueOf(token.getUserId()));
                mapinfo.put("accessToken",token.getAccessToken());
                mapinfo.put("startTime",String.valueOf(token.getStartTime().getTime()));
                mapinfo.put("expireTime",String.valueOf(token.getExpireTime().getTime()));
                cacheService.hmset(TOKEN_KEY + token.getAccessToken(), mapinfo);
            }
            map.put("cacheService","缓存初始化成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("cacheService","缓存初始化失败");
        }
        return map;
    }

}
