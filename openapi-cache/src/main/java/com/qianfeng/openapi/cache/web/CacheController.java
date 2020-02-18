package com.qianfeng.openapi.cache.web;

import com.qianfeng.openapi.cache.exception.ExceptionDict;
import com.qianfeng.openapi.cache.exception.RedisException;
import com.qianfeng.openapi.cache.service.CacheService;
import com.qianfeng.openapi.cache.service.DistributedLocker;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * cache controller 版本 v1
 *
 * 升级原因：存储的 value 从请求体中获取，防止存储的value过大导致传输不到服务端
 *
 * @author：qianfeng
 * @version: v1.0
 * @date: 2019/5/9 16:34
 */
@RestController
@RequestMapping("/cache")
public class CacheController {
    private final static Logger log = LoggerFactory.getLogger(CacheController.class);
    @Autowired
    private CacheService redisService;
    @Autowired
    private DistributedLocker redissonLocker;

    @ApiOperation(value = "存字符串类型的数据到cache")
    @RequestMapping(value = "/save2Cache", method = RequestMethod.GET)
    public void save2Cache(
            @ApiParam(name = "key", value = "cache key", required = true) @RequestParam("key") String key,
            @ApiParam(name = "value", value = "cache value", required = true) @RequestParam("value") String value,
            @ApiParam(name = "expireSecond", value = "key过期时间:秒,-1为永不过期", required = true) @RequestParam("expireSecond") Integer expireSecond) {
        checkParams(key);
        log.info("key:{},value:{},expireSecond:{}", key, value, expireSecond);
        redisService.set(key, value, expireSecond);
    }

    @ApiOperation(value = "取缓存中的值")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(
            @ApiParam(name = "key", value = "cache key", required = true) @RequestParam("key") String key){
        checkParams(key);
        log.info("key:{}", key);
        return redisService.get(key);
    }

    @ApiOperation(value = "从redis删除字符串类型的数据")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public boolean del(
            @ApiParam(name = "key", value = "cache key", required = true) @RequestParam("key") String key) {
        log.info("key:{},value:{}", key);
        checkParams(key);
        return redisService.del(key);
    }

    @ApiOperation(value = "key的值设为value,当且仅当key不存在时", notes = "若给定的key已经存在trylock不做任何动作")
    @RequestMapping(value = "/trylock", method = RequestMethod.POST)
    public boolean tryLock(
            @ApiParam(name = "key", value = "cache key", required = true) @RequestParam("key") String key,
            @ApiParam(name = "waitTime", value = "等到最大时间", required = true) @RequestParam("waitTime") int waitTime,
            @ApiParam(name = "expireSecond", value = "key过期时间:秒", required = true) @RequestParam("expireSecond") int expireSecond) {
        checkParams(key);
        return redissonLocker.tryLock(key,TimeUnit.SECONDS,waitTime,expireSecond);
    }

    @ApiOperation(value = "释放锁")
    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    public boolean tryLock(
            @ApiParam(name = "key", value = "cache key", required = true) @RequestParam("key") String key) {
        checkParams(key);
        redissonLocker.unlock(key);
        return true;
    }

    @ApiOperation(value = "存MAP类型的数据到cache")
    @RequestMapping(value = "/hmset", method = RequestMethod.POST)
    public boolean hmset(
            @ApiParam(name = "key", value = "cache key", required = true) @RequestParam("key") String key,
            @ApiParam(name = "map", value = "map", required = true) @RequestBody Map<String,Object> map) {
        checkParams(key);
        log.info("key:{},map:{}", key, map);
        return redisService.hmset(key, map);
    }

    @ApiOperation(value = "返回哈希表 key 中的MAP值")
    @RequestMapping(value = "/hgetall", method = RequestMethod.POST)
    public Map hgetall(
            @ApiParam(name = "key", value = "cache key", required = true) @RequestParam("key") String key){
        checkParams(key);
        log.info("key:{},", key);
        return redisService.hgetall(key);
    }

    @ApiOperation(value = "返回哈希表 key 中的MAP中的值")
    @RequestMapping(value = "/hget", method = RequestMethod.POST)
    public String  hgetall(
            @ApiParam(name = "key", value = "cache key", required = true) @RequestParam("key") String key,
            @ApiParam(name = "field", value = "field key", required = true) @RequestParam("field") String field){
        checkParams(key);
        log.info("key:{},", key);
        return redisService.hget(key,field);
    }

    @ApiOperation(value = "传入一个key，通过redis取得自增id")
    @RequestMapping(value = "/increId", method = RequestMethod.POST)
    public Long increId(
            @ApiParam(name = "key", value = "cache key", required = true) @RequestParam("key") String key){
        checkParams(key);
        log.info("key:{},", key);
        return redisService.getAutoIncreId(key);
    }

    @ApiOperation(value = "传设置过期时间")
    @RequestMapping(value = "/expire", method = RequestMethod.POST)
    public boolean expire(
            @ApiParam(name = "key", value = "cache key", required = true) @RequestParam("key") String key,
            @ApiParam(name = "expireSecond", value = "key过期时间:秒", required = true) @RequestParam("expireSecond") Integer expireSecond){
        checkParams(key);
        log.info("key:{},", key);
        return redisService.expire(key, expireSecond);
    }

    @ApiOperation(value = "增加ZSET值")
    @RequestMapping(value = "/incrementScore", method = RequestMethod.POST)
    public double  incrementScore(
            @ApiParam(name = "key", value = "cache key", required = true) @RequestParam("key") String key,
            @ApiParam(name = "value", value = "value", required = true) @RequestParam("value") String value,
            @ApiParam(name = "delta", value = "delta", required = true) @RequestParam("delta") double delta){
        checkParams(key);
        log.info("key:{},", key);
        return redisService.incrementScore(key,value,delta);
    }

    @ApiOperation(value = "索引倒序排列区间值")
    @RequestMapping(value = "/reverseRangeWithScores", method = RequestMethod.POST)
    public List<String> reverseRangeWithScores(
            @ApiParam(name = "key", value = "cache key", required = true) @RequestParam("key") String key,
            @ApiParam(name = "start", value = "start", required = true) @RequestParam("start") int start,
            @ApiParam(name = "end", value = "end", required = true) @RequestParam("end") int end){
        checkParams(key);
        log.info("key:{},", key);
        return redisService.reverseRangeWithScores(key,start,end);
    }

    public void checkParams(String key) {
        if (StringUtils.isBlank(key)) {
            throw new RedisException(ExceptionDict.paramError, "参数不能为空");
        }
    }

    @ApiOperation(value = "测试使用")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String test(@RequestParam("param_json") String param_json){
        log.info("josn:" + param_json);
        return "测试成功";
    }

}
