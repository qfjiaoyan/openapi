package com.qianfeng.openapi.web.master.service.api;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author menglili
 * 搜索服务的熔断
 */
@Component
public class SearchServiceFallback implements SearchService {
    @Override
    public List<Map> searchLog(String paras) {
        return null;
    }

    @Override
    public Long searchLogCount(String paras) {
        return 0L;
    }
	
	@Override
    public int createindex() {
        return 0;
    }

    @Override
    public Map<String, String> statApiCountAndAvg(long startTime, long endTime) {
        return null;
    }
}
