package com.qianfeng.openapi.sdk.request;

import org.codehaus.jackson.annotate.JsonProperty;

public class Field {
	private String key;
	private String value;

	public Field() {
		super();
	}

	public Field(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
    @JsonProperty("key")
	public String getKey() {
		return key;
	}
    @JsonProperty("key")
	public void setKey(String key) {
		this.key = key;
	}
	@JsonProperty("value")
	public String getValue() {
		return value;
	}
	@JsonProperty("value")
	public void setValue(String value) {
		this.value = value;
	}
}
