package com.qianfeng.openapi.web.master.controller;

import com.qianfeng.openapi.web.master.pojo.AppInfo;
import com.qianfeng.openapi.web.master.service.AppInfoService;
import com.qianfeng.openapi.web.master.service.api.SearchService;
import com.qianfeng.openapi.web.master.util.JsonUtil;
import com.qianfeng.openapi.web.master.util.SearchPojo;
import com.qianfeng.openapi.web.master.util.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * 搜索服务
 *
 * @author menglili
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private SearchService searchService;

    @RequestMapping(params = "act=table")
    public TableData search(SearchPojo criteria) {
        criteria.setHighLightPostTag("</B></font>");
        criteria.setHighLightPreTag("<font style='color:red'><B>");
        String str = JsonUtil.getJSON(criteria);
        Long count = searchService.searchLogCount(str);
        if (count != null && count > 0) {
            List<Map> list = searchService.searchLog(str);
            return new TableData(count, list);
        }
        return new TableData(0, null);
    }

    /**
     * 公司下拉列表
     */
    @RequestMapping(params = "act=app")
    public List<AppInfo> appInfo() {
        return appInfoService.getSimpleInfoList();
    }
}
