package com.qianfeng.openapi.sdk;

import com.qianfeng.openapi.sdk.request.OpenApiRequest;

public interface OpenApiClient {

	public String execute(OpenApiRequest request)
			throws OpenApiException;

}
