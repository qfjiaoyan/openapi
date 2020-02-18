package com.qianfeng.openapi.sdk.request;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRequest {
	
	private final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	protected String timestamp = sdf.format(new Date());
	protected String version = "2.0";

	protected String venderId;
	protected String method;
	protected String sign;

	public Map<String, String> getSysParams() {
		Map<String, String> sysParams = new HashMap<String, String>();
		sysParams.put("method", method);
		sysParams.put("timestamp", timestamp);
		sysParams.put("v", version);
		return sysParams;
	}

	protected String getVenderId() {
		return venderId;
	}

	protected void setVenderId(String venderId) {
		this.venderId = venderId;
	}

	protected String getMethod() {
		return method;
	}

	protected void setMethod(String method) {
		this.method = method;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	protected String getVersion() {
		return version;
	}

	protected void setVersion(String version) {
		this.version = version;
	}

	protected String getSign() {
		return sign;
	}

	protected void setSign(String sign) {
		this.sign = sign;
	}

	public String getOtherParams() throws IOException {
		return null;
	}
	
}
