package com.qianfeng.openapi.sdk;

import com.qianfeng.openapi.sdk.request.order.OrderCancelRequest;

import org.junit.Test;

public class OrderCancelTest extends BaseSdkTest{
    @Test
    public void testOrderGet() throws Exception {
        long s = System.currentTimeMillis();
        OrderCancelRequest request = new OrderCancelRequest();
        request.setOrderId("1");
        request.setDelApplyReason("不想买了");
        request.setDelApplyType("2");
        String response = client.execute(request);
		System.out.println(response);
    }
}
