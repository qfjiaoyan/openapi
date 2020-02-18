package com.qianfeng.openapi.cache.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RedissionConfig {
    private static final Logger logger = LoggerFactory.getLogger(RedissionConfig.class);

    @Autowired
    private Environment env;
    private Config config;

    private static final String REDISSION_ClIENT_NAME = "SHIT_IS_PERFECT";

    private static final Integer POOL_SIZE = 200;

    private static final Integer IDLE_SIZE = 15;

    private static final Integer TIME_OUT = 10000;

    @Value("${redis.address.master}")
    private String master;
    @Value("${redis.address.slave}")
    private String slave;
    @Value("${redis.connectionMode}")
    private Integer connectionMode;
    @Value("${redis.address.cluster}")
    private String cluster;
//    @Value("${spring.redis.password}")
//    private String redisPass;
    @Bean
    public RedissonClient redissionClient(){
        config = new Config();
        switch (connectionMode){
            //standalone 单机模式
            case 1:
                config.useSingleServer().setAddress(master)
                        .setConnectionPoolSize(POOL_SIZE)
                        .setClientName(REDISSION_ClIENT_NAME)
                        .setConnectionMinimumIdleSize(IDLE_SIZE)
                        .setConnectTimeout(TIME_OUT)
//                        .setPassword(redisPass)
                        .setPingTimeout(30000);
                break;
             //master_slave 主从模式
            case 2:
                config.useMasterSlaveServers().setClientName(REDISSION_ClIENT_NAME)
                        .setConnectTimeout(TIME_OUT)
                        .setMasterAddress(master)
                        .addSlaveAddress(slave)
                        .setMasterConnectionMinimumIdleSize(IDLE_SIZE)
                        .setMasterConnectionPoolSize(POOL_SIZE)
                        .setSlaveConnectionMinimumIdleSize(IDLE_SIZE)
                        .setSlaveConnectionPoolSize(POOL_SIZE);
                break;
             //cluster 集群模式
            case 3:
                config.useClusterServers()
                        .addNodeAddress(cluster.split(","))//这是用的集群server
                        .setScanInterval(2000) //设置集群状态扫描时间
                        .setMasterConnectionPoolSize(10000) //设置连接数
                        .setSlaveConnectionPoolSize(10000);
                break;
        }
        return Redisson.create(config);
    }
}
