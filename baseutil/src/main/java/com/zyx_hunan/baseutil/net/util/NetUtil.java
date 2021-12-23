package com.zyx_hunan.baseutil.net.util;

import com.zyx_hunan.baseutil.net.ApiPath;
import com.zyx_hunan.baseutil.net.Options;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetUtil {
    private static Options options;
    public String url="";
    public int default_time=0;
    public static ApiPath apiPath = null;


    public static Options options(){
        if(options == null){
            options = new Options();
        }
        return options;
    }


    private NetUtil() {

    }

    public static synchronized ApiPath getApiPath() {
        if (apiPath == null) {
            apiPath = new NetUtil().getNet();
        }
        return apiPath;
    }


    public  ApiPath getNet() {
        return initRetrofit(initOkHttpClient()).create(options.apiPath);
    }


    private Retrofit initRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(options.url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    private OkHttpClient initOkHttpClient() {
        return new OkHttpClient().newBuilder()
                .readTimeout(options.default_time, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(options.default_time, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(options.default_time, TimeUnit.SECONDS)//设置写入超时时间
                .addInterceptor(new LogInterceptor())//添加打印拦截器
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .build();
    }

}
