package com.qianfeng.openapi.web.master.service.api;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 缓存服务的熔断
 */
@Component
public class CacheServiceFallback implements CacheService {
    @Override
    public String get(String key) {
        return "";
    }

    @Override
    public boolean setnx(String key, String value, long expireSecond) {
        return false;
    }

    @Override
    public boolean del(String key) {
        return false;
    }

    @Override
    public void set(String key, String value, long expireSecond) {

    }

    @Override
    public List<String> reverseRangeWithScores(String key, int start, int end) {
        return null;
    }

    @Override
    public boolean hmset(String key, Map<String,String> map) {
        return false;
    }
}
