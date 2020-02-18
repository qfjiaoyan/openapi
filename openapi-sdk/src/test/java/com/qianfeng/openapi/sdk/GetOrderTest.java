package com.qianfeng.openapi.sdk;

import com.qianfeng.openapi.sdk.request.order.OrderGetRequest;
import org.junit.Test;

public class GetOrderTest extends BaseSdkTest{

    @Test
    public void testOrderGet() throws Exception {
        long s = System.currentTimeMillis();
        OrderGetRequest request = new OrderGetRequest();
        request.setOrderId("1");
        request.setOrderState("1");
        String response = client.execute(request);
        System.out.println(response);
    }
}
