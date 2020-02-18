package com.qianfeng.openapi.web.master.controller;

import com.qianfeng.openapi.web.master.pojo.AppInfo;
import com.qianfeng.openapi.web.master.service.AppInfoService;
import com.qianfeng.openapi.web.master.service.api.SearchService;
import com.qianfeng.openapi.web.master.util.JsonUtil;
import com.qianfeng.openapi.web.master.util.SearchPojo;
import com.qianfeng.openapi.web.master.util.StaticAPIPojo;
import com.qianfeng.openapi.web.master.util.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索服务
 */
@RestController
@RequestMapping("/staticapi")
public class StaticAPIController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(params = "act=table")
    public TableData search(StaticAPIPojo criteria) {
        Map<String, String> stringStringMap = searchService.statApiCountAndAvg(criteria.getStartTime(), criteria.getEndTime());
        List<Map<String, String>> rows = new ArrayList<>();
        for (String key : stringStringMap.keySet()) {
            Map<String, String> map = new HashMap<>();
            map.put("key",key);
            String res = stringStringMap.get(key);
            String[] split = res.split(",");
            String apiMax = split[0];
            map.put("apiMax",apiMax);
            String apiAvg = split[1];
            map.put("apiAvg",apiAvg);
            rows.add(map);
        }
        return new TableData(1, rows);
    }

}
