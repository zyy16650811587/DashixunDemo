package com.example.dashixundemo.app;

import android.app.Application;

import com.example.dashixundemo.Contents;
import com.example.dashixundemo.uitl.SpUtil;
import com.example.httplibrary1.HttpConstant;
import com.example.httplibrary1.HttpGlobalConfig;
import com.example.httplibrary1.utils.LogUtils;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.IOException;
import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
//import com.squareup.leakcanary.LeakCanary;

public class ShopApplication extends Application {
    private static ShopApplication app;

    public static ShopApplication getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
       JPushInterface.setDebugMode(true);
      JPushInterface.init(this);
        ArrayList<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new InterCoptorLogin());
        HttpGlobalConfig.getInstance()
                .setBaseurl("http://169.254.189.205:8080/kotlin/")
                .setTimeout(HttpConstant.TIME_OUT)
                .setShowLog(true)
                .setAppKey(Contents.JPUSHREGISTID,JPushInterface.getRegistrationID(this))
               .setInterceptors(interceptors)
                .setTimeUnit(HttpConstant.TIME_UNIT)
                .initReady(this);
//      HttpGlobalConfig.getInstance()
//              .setBaseurl("http://api.ergedd.com/")
//              .setTimeout(HttpConstant.TIME_OUT)
//             .setShowLog(true)
//              .setTimeUnit(HttpConstant.TIME_UNIT)
//              .initReady(this);

      CrashReport.initCrashReport(getApplicationContext(), "ef93bab6b8", false);
       // CrashReport.initCrashReport(getApplicationContext());
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
         //  CrashReport.testJavaCrash();

    }


    private class InterCoptorLogin implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            String headers = response.header("Set-Cookie");
            LogUtils.e("111111111111"+headers);
            if (headers!=null){
                SpUtil.setParam("cookie",headers);
            }
            return response;
        }
    }
}
