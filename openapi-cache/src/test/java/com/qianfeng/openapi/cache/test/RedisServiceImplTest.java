package com.qianfeng.openapi.cache.test;

import com.qianfeng.openapi.cache.CacheServiceApplication;
import com.qianfeng.openapi.cache.service.CacheService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CacheServiceApplication.class)
@WebAppConfiguration
public class RedisServiceImplTest {
    private final static Logger log = LoggerFactory.getLogger(RedisServiceImplTest.class);

    @Autowired
    private CacheService cacheService;

    @Test
    public void platfromIdTest() {
        //boolean b = cacheService.set("zhang1111", "jianbin", 100);
        Map<String,Object> map=new HashMap<>();
        map.put("aa","asf");
        cacheService.hmset("test", map);
        //TestCase.assertEquals(true, b);
    }

}