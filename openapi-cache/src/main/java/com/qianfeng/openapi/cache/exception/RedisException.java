package com.qianfeng.openapi.cache.exception;


public class RedisException extends BaseException {
    public RedisException(String code, String msg) {
        super(code,msg);
    }
}

