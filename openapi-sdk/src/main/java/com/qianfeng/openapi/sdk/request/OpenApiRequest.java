package com.qianfeng.openapi.sdk.request;

import java.io.IOException;
import java.util.Map;



public interface OpenApiRequest {
	public String getApiMethod();

	public Map<String, String> getSysParams();

	public String getAppJsonParams() throws IOException;

	public String getOtherParams() throws IOException;
}
