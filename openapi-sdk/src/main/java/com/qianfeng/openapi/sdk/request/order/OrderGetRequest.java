package com.qianfeng.openapi.sdk.request.order;

import com.qianfeng.openapi.sdk.util.JsonUtil;
import com.qianfeng.openapi.sdk.request.AbstractRequest;
import com.qianfeng.openapi.sdk.request.OpenApiRequest;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class OrderGetRequest extends AbstractRequest implements
        OpenApiRequest {

    private String orderId;
    private String optionalFields;

    private String orderState;

    public String getApiMethod() {
        return "taobao.pop.order.get";
    }

    public String getAppJsonParams() throws IOException {
        Map<String, String> map = new TreeMap<String, String>();
		map.put("order_id", this.orderId);
		map.put("order_state", this.orderState);
//        map.put("optional_fields",this.optionalFields);
		return JsonUtil.toJson(map);
	}

    public String getOrderState() {
        return orderState;
    }


    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }


    public String getOrderId() {
        return orderId;
    }


    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getOptionalFields() {
        return optionalFields;
    }


    public void setOptionalFields(String optionalFields) {
        this.optionalFields = optionalFields;
    }
}
