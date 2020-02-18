package com.qianfeng.openapi.web.master.controller;

import com.qianfeng.openapi.web.master.service.api.CacheService;
import com.qianfeng.openapi.web.master.service.api.SearchService;
import com.qianfeng.openapi.web.master.util.StaticAPIPojo;
import com.qianfeng.openapi.web.master.util.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/apirank")
public class APIRankController {

    @Autowired
    private CacheService cacheService;

    @RequestMapping(params = "act=table")
    public TableData search() {
        String day = getDateString(new Date(),"yyyyMMdd");
        String rankingKey =  "DAY:" + day;
        List<String> strings = cacheService.reverseRangeWithScores(rankingKey, 0, 10);
        List<Map<String, String>> rows = new ArrayList<>();
        for (String string : strings) {
            Map<String, String> map = new HashMap<>();
            String[] split = string.split(",");
            String apiName = split[0];
            map.put("apiName",apiName);
            String apiCount = split[1];
            map.put("apiCount",apiCount);
            rows.add(map);
        }
        return new TableData(1, rows);
    }

    public String getDateString(Date date, String pattern) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            return formatter.format(date);
        }
    }

}
