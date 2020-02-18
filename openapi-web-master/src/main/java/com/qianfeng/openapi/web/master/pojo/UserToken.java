package com.qianfeng.openapi.web.master.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author menglili
 */
@Data
public class UserToken {
    private Integer id;
    private Integer appId;
    private Integer userId;
    private String accessToken;
    private Date expireTime;
    private Date startTime;
}
