package com.qianfeng.openapi.web.master.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "CACHE-SERVICE", fallback = CacheServiceFallback.class)
public interface CacheService {
    @RequestMapping(value = "/cache/get", method = RequestMethod.GET)
    String get(@RequestParam("key") String key);

    @RequestMapping(value = "/cache/setnx", method = RequestMethod.POST)
    boolean setnx(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("expireSecond") long expireSecond);

    @RequestMapping(value = "/cache/del", method = RequestMethod.POST)
    boolean del(@RequestParam("key") String key);

    @RequestMapping(value = "/cache/hmset", method = RequestMethod.POST)
    boolean hmset(@RequestParam("key") String key, @RequestBody Map<String,String> map);

    @RequestMapping(value = "/cache/save2Cache", method = RequestMethod.GET)
    void set(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("expireSecond") long expireSecond);

    @RequestMapping(value = "/cache/reverseRangeWithScores", method = RequestMethod.POST)
    public List<String> reverseRangeWithScores(@RequestParam("key") String key,@RequestParam("start") int start, @RequestParam("end") int end);

}
