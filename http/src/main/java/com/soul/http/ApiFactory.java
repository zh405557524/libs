package com.soul.http;


import com.soul.http.retrofit.HttpLoggingInterceptor;
import com.soul.http.retrofit.RetrofitFactory;
import com.soul.lib.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import retrofit2.Retrofit;

/**
 * Description: api工厂
 * Author: 祝明
 * CreateDate: 2020/12/10 10:09
 * UpdateUser:
 * UpdateDate: 2020/12/10 10:09
 * UpdateRemark:
 */
public class ApiFactory {


    /**
     * 技能数据，综合后台
     */
    private Retrofit mRetrofitService;
    private List<Interceptor> mInterceptors;

    public static ApiFactory getInstance() {

        return ApiFactoryHolder.sInstance;
    }


    private ApiFactory() {

    }

    public void initService(String url) {
        initService(url, null);
    }

    public void initService(String url, List<Interceptor> interceptors) {
        mInterceptors = new ArrayList<>();
        if (interceptors != null) {
            mInterceptors.addAll(interceptors);
        }
        // log拦截器
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(message -> LogUtil.i("okHttp", message));
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mInterceptors.add(logInterceptor);
        mRetrofitService = RetrofitFactory.getInstance().createRetrofit(url, mInterceptors);
    }


    public <T> T getApi(Class<T> service) {
        return mRetrofitService.create(service);
    }

    private static class ApiFactoryHolder {
        private static final ApiFactory sInstance = new ApiFactory();
    }

}
