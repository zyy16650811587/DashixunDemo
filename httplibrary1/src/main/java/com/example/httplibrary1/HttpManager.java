package com.example.httplibrary1;

import com.example.httplibrary1.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    private static volatile HttpManager instance;

    private HttpManager() {
    }

    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    public Retrofit getrerofit(String url, long outTime, TimeUnit timeUnit) {
        return new Retrofit.Builder()

                .client(getBaseOkhttp(outTime, timeUnit))
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public OkHttpClient getBaseOkhttp(long outTime, TimeUnit timeUnit) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.e(message);
            }
        });
//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                Response proceed = chain.proceed(request);
//                return proceed;
//            }
//        };
        Interceptor interceptor = new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response proceed = chain.proceed(request);
                return proceed;
            }
        };
        Interceptor[] interceptors = {httpLoggingInterceptor, interceptor};
        return getCliect(outTime, timeUnit, interceptors);
    }

    public OkHttpClient getCliect(long outTime, TimeUnit timeUnit, Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(outTime, timeUnit);
        builder.writeTimeout(outTime, timeUnit);
        builder.readTimeout(outTime, timeUnit);
        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }
        ArrayList<Interceptor> interceptors1 = HttpGlobalConfig.getInstance().getInterceptors();
        if (interceptors1 != null) {
            for (Interceptor interceptor : interceptors1) {
                builder.addInterceptor(interceptor);
            }

        }
        return builder.build();
    }
}


