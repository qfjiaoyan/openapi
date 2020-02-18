package com.qianfeng.openapi.web.master.controller;

import com.github.pagehelper.PageInfo;
import com.qianfeng.openapi.web.master.pojo.UserToken;
import com.qianfeng.openapi.web.master.service.UserTokenService;
import com.qianfeng.openapi.web.master.service.api.CacheService;
import com.qianfeng.openapi.web.master.util.AjaxMessage;
import com.qianfeng.openapi.web.master.util.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * token管理
 * @author menglili
 */
@RestController
@RequestMapping("token")
public class TokenController {

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private CacheService cacheService;

    private final String TOKEN_KEY = "TOKEN:";

    @RequestMapping(params = "act=table")
    public TableData table(UserToken token, Integer offset, Integer limit) {
        PageInfo<UserToken> pageInfo = userTokenService.getTokenList(token, offset, limit);
        return new TableData(pageInfo.getTotal(), pageInfo.getList());
    }

    @RequestMapping(params = "act=info")
    public UserToken info(int id) {
        return userTokenService.getTokenById(id);
    }

    @RequestMapping(params = "act=update")
    public AjaxMessage update(@RequestBody Map<String,String> map) {
        cacheService.hmset(TOKEN_KEY + map.get("accessToken"), map);
        return new AjaxMessage(true);
    }

    @RequestMapping(params = "act=add")
    public AjaxMessage add(@RequestBody Map<String,String> map) {
        cacheService.hmset(TOKEN_KEY + map.get("accessToken"), map);
        return new AjaxMessage(true);
    }
}
