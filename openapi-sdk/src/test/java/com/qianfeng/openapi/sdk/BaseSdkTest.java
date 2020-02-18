package com.qianfeng.openapi.sdk;

public class BaseSdkTest {
    public static final String SERVER_URL = "http://114.242.146.109:9350/openapi";
//    public static final String SERVER_URL = "http://127.0.0.1:8500/openapi";
    public OpenApiClient  client = new DefaultClient(SERVER_URL, "eb6aa496-4918-4099-9082-19582e8438e1",
            "561AC1A8676CFCB0CC61B041AE42ABB8", "ff2ff43192a84bb49988720c307182c8");
}
