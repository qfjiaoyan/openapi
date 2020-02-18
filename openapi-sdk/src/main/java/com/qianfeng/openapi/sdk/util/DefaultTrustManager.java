package com.qianfeng.openapi.sdk.util;


import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by IntelliJ IDEA.
 * User: yfliufei
 * Date: 14-4-28
 * Time: 下午5:55
 * To change this template use File | Settings | File Templates.
 */
public class DefaultTrustManager implements X509TrustManager {
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }
}