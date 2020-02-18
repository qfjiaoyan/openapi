package com.qianfeng.openapi.cache.service.impl;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.qianfeng.openapi.cache.exception.ExceptionDict;
import com.qianfeng.openapi.cache.exception.RedisException;
import com.qianfeng.openapi.cache.service.CacheService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

@Service("redisService")
public class RedisServiceImpl implements CacheService {
    private final static Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 存字符串类型的数据到redis
     *
     * @param key
     * @param value
     * @param expireSecond key过期时间：秒
     * @return
     * @throws Exception
     */
    @Override
    public boolean set(String key, String value, long expireSecond) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
            if (expireSecond>0){
                stringRedisTemplate.expire(key, expireSecond ,TimeUnit.SECONDS);
            }
            return true;
        } catch (JedisException e) {
            log.error("存字符串类型的数据到redis失败,key:{}", key, e);
            throw new RedisException(ExceptionDict.defaultCode, "存字符串类型的数据到redis失败,key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     * 存字节类型的数据到redis
     *
     * @param key
     * @param obj
     * @param expireSecond key过期时间：秒
     * @return
     */
    @Override
    public boolean saveBytes2Redis(String key, byte[] obj, int expireSecond) {
        return true;
    }

    /**
     * 从redis读取字符串类型的数据
     *
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public String getFromRedis(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (JedisException e) {
            log.error("从redis读取字符串类型的数据失败，key：" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "从redis读取字符串类型的数据失败,key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     * 从redis删除字符串类型的数据
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteKey(String key) {
        try {
            stringRedisTemplate.delete(key);
            return true;
        } catch (JedisException e) {
            log.error("从redis删除字符串类型的数据key,key:" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "从redis删除字符串类型的数据key,key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }


    /**
     * 传入一个key，通过redis取得自增id
     *
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public long getAutoIncreId(String key) {
        try {
            return stringRedisTemplate.opsForValue().increment(key, 1);
        } catch (JedisException e) {
            log.error("通过redis取得自增id失败，key：" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "通过redis取得自增id失败，key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     *设定某个缓存KEY的过期时间
     * @param key
     * @param seconds    秒为单位
     * @return
     */
    @Override
    public boolean expire(String key, int seconds) {
        try {
            return stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        } catch (JedisException e) {
            log.error("expire error, key:" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "expire error, key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     *根据缓存KEY获取值
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (JedisException e) {
            log.error("get error, key:" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "get error, key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
     * @param key
     * @param value
     * @return
     */
    @Override
    public String getSet(String key, String value) {
        try {
            return stringRedisTemplate.opsForValue().getAndSet(key, value);
        } catch (JedisException e) {
            log.error("getSet error, key:" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "getSet error, key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     * 删除指定的KEY的缓存信息
     * @param key
     * @return
     */
    @Override
    public boolean del(String key) {
        try {
            return stringRedisTemplate.delete(key);
        } catch (JedisException e) {
            log.error("del error, key:" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "del error, key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     * 将多个值插入到列表头部
     * @param key
     * @param strings
     * @return
     */
    @Override
    public Long lpush(String key, String[] strings) {
        try {
            return stringRedisTemplate.opsForList().leftPushAll(key, strings);
        } catch (JedisException e) {
            log.error("lpush error, key:" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "lpush error, key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     * 用于移除并返回列表的第一个元素。
     * @param key
     * @return
     */
    @Override
    public String lpop(String key) {
        try {
           return stringRedisTemplate.opsForList().leftPop(key);
        } catch (JedisException e) {
            log.error("lpop error, key:" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "lopp error, key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     * 返回列表中指定区间内的元素，区间以偏移量 START 和 END 指定
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<String> lrange(String key, long start, long end) {
        try {
            return stringRedisTemplate.opsForList().range(key,start, end);
        } catch (JedisException e) {
            log.error("lrange error, key:" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "lrange error, key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     * 返回列表的长度
     * @param key
     * @return
     */
    @Override
    public Long llen(String key) {
        try {
            return stringRedisTemplate.opsForList().size(key);
        } catch (JedisException e) {
            log.error("llen error, key:" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "llen error, key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
     * @param key
     * @param map
     * @return
     */
    @Override
    public boolean hmset(String key, Map<String,Object> map) {
        try {
            System.out.println(map);
            stringRedisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (JedisException e) {
            log.error("hmset error, key:" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "hmset error, key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     * 同返回哈希表 key 中，所有的域和值。
     * @param key
     * @return
     */
    @Override
    public Map hgetall(String key) {
        try {
            return stringRedisTemplate.opsForHash().entries(key);
        } catch (JedisException e) {
            log.error("hmset error, key:" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "hmset error, key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     * 取HASH中某个字段的值
     * @param key
     * @param field
     * @return
     */
    @Override
    public String hget(String key, String field) {
        try {
            return (String)stringRedisTemplate.opsForHash().get(key, field);
        } catch (JedisException e) {
            log.error("hget error, key:" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "hget error, key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     * 如果给定的哈希表并不存在， 那么一个新的哈希表将被创建并执行 HSET 操作。
     * 如果域 field 已经存在于哈希表中， 那么它的旧值将被新值 value 覆盖。
     * @param key
     * @param field
     * @param value
     * @return
     */
    @Override
    public boolean hset(String key, String field, String value) {
        try {
            stringRedisTemplate.opsForHash().put(key, field, value);
            return true;
        } catch (JedisException e) {
            log.error("hset error, key:" + key, e);
            return false;
        } catch (Exception e) {
            log.error("redis error", e);
            return false;
        }
    }

    /**
     * Redis Sadd 将一个或多个成员元素加入到集合中，已经存在于集合的成员元素将被忽略。
     * 假如集合 key 不存在，则创建一个只包含添加的元素作成员的集合。
     * @param key
     * @param strings
     * @return
     */
    @Override
    public Long sadd(String key, String[] strings) {
        try {
            return stringRedisTemplate.opsForSet().add(key,strings);
        } catch (JedisException e) {
            log.error("sadd error, key:" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "sadd error, key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     * 返回集合中元素的数量
     * @param key
     * @return
     */
    @Override
    public Long scard(String key) {
        Jedis jedis = null;
        HashMap<Long, JedisPool> jedisPoolContext = null;
        JedisPool pool = null;
        try {
            return stringRedisTemplate.opsForSet().size(key);
        } catch (JedisException e) {
            log.error("scard error, key:" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "scard error, key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     * 移除并返回集合中的一个随机元素。
     * @param key
     * @return
     */
    @Override
    public String spop(String key) {
        try {
            return stringRedisTemplate.opsForSet().pop(key);
        } catch (JedisException e) {
            log.error("spop error, key:" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "spop error, key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     * 返回哈希表 key 中，所有的域和值。
     * @param key 业务数据集合key
     * @return
     */
//    @Override
//    public Map<Object, Object> hgetAll(String key) {
//        try {
//            return stringRedisTemplate.opsForHash().entries(key);
//        } catch (JedisException e) {
//            log.error("hgetAll error, key:" + key, e);
//            throw new RedisException(ExceptionDict.defaultCode, "hgetAll error, key:" + key + ",error:" + e.getMessage());
//        } catch (Exception e) {
//            log.error("redis error", e);
//            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
//        }
//    }
    @Override
    public boolean hDel(String key, String... fields) {
        try {
            stringRedisTemplate.opsForHash().delete(key, fields);
            return true;
        } catch (JedisException e) {
            log.error("hDel error, key:" + key, e);
            throw new RedisException(ExceptionDict.defaultCode, "hDel error, key:" + key + ",error:" + e.getMessage());
        } catch (Exception e) {
            log.error("redis error", e);
            throw new RedisException(ExceptionDict.defaultCode, e.getMessage());
        }
    }

    /**
     * 修改变量中的元素的分值。
     * @param key
     * @param value
     * @param delta
     * @return
     */
    @Override
    public double incrementScore(String key, String value, double delta) {
        return stringRedisTemplate.opsForZSet().incrementScore(key,value,delta);
    }

    /**
     * 索引倒序排列区间值。
     * @param key 键
     * @param start 开始Score
     * @param end   结束SCORE
     * @return      返回列表
     */
    @Override
    public List<String> reverseRangeWithScores(String key, long start, long end) {
        List<String> lists =new ArrayList<>();
        // 索引倒序排列区间值。
        Set<ZSetOperations.TypedTuple<String>> typedTupleSet = new HashSet<ZSetOperations.TypedTuple<String>>();
        typedTupleSet = stringRedisTemplate.opsForZSet().reverseRangeWithScores(key,start,end);
        Iterator<ZSetOperations.TypedTuple<String>> iterator = typedTupleSet.iterator();
        while (iterator.hasNext()) {
            ZSetOperations.TypedTuple<String> typedTuple = iterator.next();
            String value = typedTuple.getValue();
            double score= typedTuple.getScore();
            lists.add(value + "," + String.valueOf(score));
        }
        return lists;
    }


}



