package com.qianfeng.openapi.web.master.pojo;

import lombok.Data;

@Data
public class ApiMapping {
    private Integer id;
    private String gatewayApiName;
    private String insideApiUrl;
    private Integer state;
    private String description;
    private String serviceId;
}
