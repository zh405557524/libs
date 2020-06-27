package com.soul.lib.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @描述：okhttp 源码分析
 * @作者：祝明
 * @项目名:libs
 * @创建时间：2020/5/22 20:28
 */
public class OKHttpAnalyze {


    /**
     * okHttp请求示例
     */
    public static void okHttpUserAction() {

        //1 创建ok httpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        //2 创建request 请求
        Request request = new Request.Builder().url("https://www.baidu.com").get().build();
        //3 创建 call
        Call call = okHttpClient.newCall(request);

        //4 发起请求
        try {
            //同步请求
            call.execute();

            Response response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


    }


}
