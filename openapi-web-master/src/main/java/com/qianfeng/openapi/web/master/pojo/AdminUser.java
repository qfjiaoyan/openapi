package com.qianfeng.openapi.web.master.pojo;

import lombok.Data;

@Data
public class AdminUser {
    private Integer id;
    private String password;
    private String email;
    private String realName;
    private Integer status;

}
