package com.soul.http.retrofit;


import com.soul.lib.Global;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description:Retrofit创建工程
 * Author: 祝明
 * CreateDate: 2019-07-02 16:10
 * UpdateUser:
 * UpdateDate: 2019-07-02 16:10
 * UpdateRemark:
 */
public class RetrofitFactory {


    private static RetrofitFactory sRetrofitFactory;
    private final OkHttpClient.Builder client;


    public synchronized static RetrofitFactory getInstance() {
        if (sRetrofitFactory == null) {
            synchronized (RetrofitFactory.class) {
                if (sRetrofitFactory == null) {
                    sRetrofitFactory = new RetrofitFactory();
                }
            }
        }
        return sRetrofitFactory;
    }


    private RetrofitFactory() {
        File cacheFile = new File(Global.getContext().getCacheDir(), "okhttp");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);
        client = new OkHttpClient.Builder()
                .connectTimeout(12, TimeUnit.SECONDS)//连接超时时间
                .writeTimeout(12, TimeUnit.SECONDS)//写入超时时间
//                .sslSocketFactory(createSSLSocketFactory())
//                .hostnameVerifier(new TrustAllHostnameVerifier())
                .cache(cache);
    }

    /**
     * 创建一个retrofit
     *
     * @param baseUrl      根地址
     * @param interceptors 适配器
     * @return
     */
    public Retrofit createRetrofit(String baseUrl, List<Interceptor> interceptors) {
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                List<Interceptor> oldInterceptors = client.interceptors();
                if (!oldInterceptors.contains(interceptor)) {
                    client.addInterceptor(interceptor);
                }
            }
        }
        return new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }
    //自定义SS验证相关类
    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }
}
