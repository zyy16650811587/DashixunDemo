package com.example.httplibrary1;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import okhttp3.Interceptor;

public class HttpGlobalConfig {
    private String baseurl;
    private long timeout;
    private TimeUnit timeUnit;
    //公共请求参数
    private Map<String, Object> baseparems;
    //公共的请求头信息
    private Map<String, Object> baseHeards;
    //公共的拦截器
    private ArrayList<Interceptor> interceptors;
    //日志开关
    private boolean isShowLog;
    private Context context;
    private Handler handler;

    private Map<String,String> appkeys;

    private HttpGlobalConfig() {

    }

    private static final class HttpGlobalConfigHodler {
        private static HttpGlobalConfig INSTANCE = new HttpGlobalConfig();
    }

    public static HttpGlobalConfig getInstance() {
        return HttpGlobalConfigHodler.INSTANCE;
    }

    public String getBaseurl() {
        return baseurl;
    }

    public HttpGlobalConfig setBaseurl(String baseurl) {
        this.baseurl = baseurl;
        return HttpGlobalConfig.getInstance();
    }

    public long getTimeout() {
        return timeout;
    }

    public HttpGlobalConfig setTimeout(long timeout) {
        this.timeout = timeout;
        return HttpGlobalConfig.getInstance();
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public HttpGlobalConfig setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return HttpGlobalConfig.getInstance();
    }

    public Map<String, Object> getBaseparems() {
        return baseparems;
    }

    public HttpGlobalConfig setBaseparems(Map<String, Object> baseparems) {
        this.baseparems = baseparems;
        return HttpGlobalConfig.getInstance();
    }

    public Map<String, Object> getBaseHeards() {
        return baseHeards;
    }

    public HttpGlobalConfig setBaseHeards(Map<String, Object> baseHeards) {
        this.baseHeards = baseHeards;
        return HttpGlobalConfig.getInstance();
    }

    public ArrayList<Interceptor> getInterceptors() {
        return interceptors;
    }

    public HttpGlobalConfig setInterceptors(ArrayList<Interceptor> interceptors) {
        this.interceptors = interceptors;
        return HttpGlobalConfig.getInstance();
    }

    public boolean isShowLog() {
        return isShowLog;
    }

    public HttpGlobalConfig setShowLog(boolean showLog) {
        isShowLog = showLog;
        return HttpGlobalConfig.getInstance();
    }


    public HttpGlobalConfig initReady(Context context) {
        this.context = context.getApplicationContext();
        handler = new Handler(Looper.getMainLooper());
        return HttpGlobalConfig.getInstance();
    }
    //配置各种appk
    public HttpGlobalConfig setAppKey(String key,String value){
        if(appkeys==null){
            appkeys=new HashMap<>();
        }
        appkeys.put(key,value);
        return HttpGlobalConfig.getInstance();
    }


    //获取对应的appKey
    public Object getAppkey(String key){
        if(appkeys!=null&&key!=null){
            return appkeys.get(key);
        }
        return null;
    }
}
