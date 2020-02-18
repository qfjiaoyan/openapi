package com.qianfeng.openapi.sdk;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.qianfeng.openapi.sdk.request.OpenApiRequest;
import com.qianfeng.openapi.sdk.util.CodecUtil;
import com.qianfeng.openapi.sdk.util.HttpUtil;
import com.qianfeng.openapi.sdk.util.StringUtil;


public class DefaultClient implements OpenApiClient {
    public static final String CHARSET_UTF8 = "UTF-8";
    private static final String JSON_PARAM_KEY = "param_json";
    private static final String OTHER_PARAM_KEY = "other";

    private String serverUrl;
    private String accessToken;
    private int connectTimeout = 0;
    private int readTimeout = 0;
    private String appKey;
    private String appSecret;


    public DefaultClient(String serverUrl, String accessToken, String appKey, String appSecret) {
        super();
        this.serverUrl = serverUrl;
        this.accessToken = accessToken;
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

    
    /**
     *
     * @param serverUrl
     * @param accessToken
     */
    public DefaultClient(String serverUrl, String accessToken, String appKey, String appSecret, String fuzz) {
        super();
        this.serverUrl = serverUrl;
        this.accessToken = accessToken;
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

    
    
    
    /**
     * @param serverUrl
     * @param accessToken
     * @param appKey
     * @param connectTimeout
     * @param readTimeout
     */
    public DefaultClient(String serverUrl, String accessToken, String appKey, String appSecret,
                         int connectTimeout, int readTimeout) {
        this(serverUrl, accessToken, appKey, appSecret);
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    public  String execute(OpenApiRequest request) throws OpenApiException {
        try {
            String url = buildUrl(request);
            Map<String, String> params = new HashMap<String, String>();
            String json = request.getAppJsonParams();
            params.put(JSON_PARAM_KEY, json);
            if(request.getOtherParams() != null) {
            	params.put(OTHER_PARAM_KEY, request.getOtherParams());
            }
            String rsp = rsp = HttpUtil.doPost(url, params, connectTimeout, readTimeout);
            return rsp;
        } catch (Exception e) {
        	e.printStackTrace();
            throw new OpenApiException("出现异常，请重试");
        }
    }

    private String buildUrl(OpenApiRequest request)
            throws Exception {
    	Map<String, String> sysParams = request.getSysParams();
        Map<String, String> pmap = new TreeMap<String, String>();
        pmap.put(JSON_PARAM_KEY, request.getAppJsonParams());
        sysParams.put("method", request.getApiMethod());
        sysParams.put("accessToken", this.accessToken);
        sysParams.put("app_key", this.appKey);
        pmap.putAll(sysParams);
        String sign = sign(pmap, this.appSecret);
        sysParams.put("sign", sign);
        StringBuilder sb = new StringBuilder(serverUrl);
        sb.append("?");
        sb.append(HttpUtil.buildQuery(sysParams, CHARSET_UTF8));
        return sb.toString();
    }


    private String sign(Map<String, String> pmap, String appSecret)
            throws Exception {
        StringBuilder sb = new StringBuilder(appSecret);
        String testStr = pmap.toString();
        for (Entry<String, String> entry : pmap.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            if (StringUtil.areNotEmpty(name, value)) {
                sb.append(name).append(value);
            }
        }
        sb.append(appSecret);
        String result = CodecUtil.md5(sb.toString());
        return result;
    }
}
