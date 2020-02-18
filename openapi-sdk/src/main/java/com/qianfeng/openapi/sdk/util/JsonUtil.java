package com.qianfeng.openapi.sdk.util;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.io.IOException;

public final class JsonUtil {

	private static final ObjectMapper mapper = new ObjectMapper();

	private JsonUtil() {
		throw new UnsupportedOperationException();
	}
	
	public static String toJson(Object obj) throws IOException {
		mapper.setSerializationInclusion(Inclusion.NON_NULL); 
		mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);  
		mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		return mapper.writeValueAsString(obj);
	}

}
