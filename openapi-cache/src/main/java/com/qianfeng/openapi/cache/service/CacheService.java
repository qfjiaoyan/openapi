package com.qianfeng.openapi.cache.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存接口，覆盖对REDIS的大部分操作
 */
public interface CacheService {
    /**
     * 存字符串类型的数据到redis
     *
     * @param key
     * @param value
     * @param expireSecond key过期时间：秒
     * @return
     * @throws Exception
     */
    boolean  set(String key, String value, long expireSecond);

    /**
     * 存对象类型的数据到redis
     *
     * @param key
     * @param obj
     * @param expireSecond key过期时间：秒
     * @return
     */
    boolean saveBytes2Redis(String key, byte[] obj, int expireSecond);

    /**
     * 从redis读取字符串类型的数据
     *
     * @param key
     * @return
     */
    String getFromRedis(String key);

    /**
     * 从redis删除字符串类型的数据key
     *
     * @param key
     * @return @
     */

    boolean deleteKey(String key);

    /**
     * 传入一个key，通过redis取得自增id
     *
     * @param key
     * @return @
     */
    long getAutoIncreId(String key);

    public boolean expire(String key, int seconds);

    public String get(String key);

    public String getSet(String key, String value);

    public boolean del(String key);

    public Long lpush(String key, String[] strings);

    public String lpop(String key);

    public List<String> lrange(String key, long start, long end);

    public Long llen(String key);

    public String hget(String key, String field);

    public boolean hset(String key, String field, String value);

    Long sadd(String key, String[] strings);

    Long scard(String key);

    String spop(String key);

    boolean hDel(String key, String... fields);

    boolean hmset(String key, Map<String,Object>  map) ;

    Map hgetall(String key);

    double incrementScore(String key, String value, double delta);

    List<String> reverseRangeWithScores(String key, long start, long end);
}
