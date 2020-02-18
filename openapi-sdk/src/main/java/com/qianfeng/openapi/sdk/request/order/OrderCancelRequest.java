package com.qianfeng.openapi.sdk.request.order;

import com.qianfeng.openapi.sdk.request.AbstractRequest;
import com.qianfeng.openapi.sdk.request.OpenApiRequest;
import com.qianfeng.openapi.sdk.util.JsonUtil;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class OrderCancelRequest extends AbstractRequest implements OpenApiRequest {

    private String orderId;
//    private String optionalFields;
    private String delApplyReason;
    private String delApplyType;

    public String getApiMethod() {
        return "taobao.order.order.update";
    }

    public String getAppJsonParams() throws IOException {
        Map<String, String> map = new TreeMap<String, String>();
		map.put("order_id", this.orderId);
		map.put("del_apply_type", this.delApplyType);
		map.put("del_apply_reason", this.delApplyReason);
//        map.put("optional_fields",this.optionalFields);
		return JsonUtil.toJson(map);
	}

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDelApplyReason() {
        return delApplyReason;
    }

    public void setDelApplyReason(String delApplyReason) {
        this.delApplyReason = delApplyReason;
    }

    public String getDelApplyType() {
        return delApplyType;
    }

    public void setDelApplyType(String delApplyType) {
        this.delApplyType = delApplyType;
    }


}
