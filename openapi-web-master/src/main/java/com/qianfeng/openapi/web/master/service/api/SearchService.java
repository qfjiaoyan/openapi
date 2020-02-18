package com.qianfeng.openapi.web.master.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "SEARCH-SERVICE", fallback = SearchServiceFallback.class)
public interface SearchService {
    @RequestMapping(value = "/search/searchlog", method = RequestMethod.POST)
    List<Map> searchLog(@RequestParam("paras") String paras);

    @RequestMapping(value = "/search/searchcount", method = RequestMethod.POST)
    Long searchLogCount(@RequestParam("paras") String paras);
	
	@RequestMapping(value = "/search/createindex", method = RequestMethod.POST)
    public int createindex();

    @RequestMapping(value = "/search/statCountAndAvg", method = RequestMethod.POST)
    public Map<String, String> statApiCountAndAvg(@RequestParam("startTime") long startTime, @RequestParam("endTime") long endTime);


}
