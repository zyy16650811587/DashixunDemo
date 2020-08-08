package com.example.httplibrary1.client;


import android.text.TextUtils;
import android.util.Log;

import com.example.httplibrary1.HttpConstant;
import com.example.httplibrary1.HttpGlobalConfig;
import com.example.httplibrary1.HttpManager;
import com.example.httplibrary1.HttpObserable;
import com.example.httplibrary1.callback.BaseCallBack;
import com.example.httplibrary1.net.ApiServer;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.POST;


public class HttpClient {
    String tag;
    //请求方式
    Method method;
    //请求参数
    Map<String, Object> paramser;
    //请求头信息
    Map<String, Object> headres;
    //Rxjava绑定生命周期
    LifecycleProvider lifecycleProvider;
    //绑定Activity具体的生命周的
    ActivityEvent activityEvent;
    //绑定Fragment的具体的生命周期的
    FragmentEvent fragmentEvent;
    String baseUrl;
    //拼接的url
    String apiUrl;
    //是否是json上传表示
    boolean isJson;
    //json字符串
    String jsonbody;
    //超时时间
    long time;
    //时间单位
    TimeUnit timeUnit;
    BaseCallBack baseCallBack;

    public HttpClient(Builder builder) {
        this.method = builder.method;
        this.activityEvent = builder.activityEvent;
        this.fragmentEvent = builder.fragmentEvent;
        this.paramser = builder.paramser;
        this.headres = builder.headres;
        this.lifecycleProvider = builder.lifecycleProvider;
        this.baseUrl = builder.baseUrl;
        this.apiUrl = builder.apiUrl;
        this.isJson = builder.isJson;
        this.jsonbody = builder.jsonbody;
        this.time = builder.time;
        this.timeUnit = builder.timeUnit;
    }

    public void request(BaseCallBack baseCallBack) {
        if (baseCallBack == null) {
            new RuntimeException("no have callback,must do Observer");
        }
        this.baseCallBack = baseCallBack;
        doRequest();
    }

    private void doRequest() {
        //组装Obserable,并且根据请求方式返回对应的Obserable，去处理异常结果的回调
        if (TextUtils.isEmpty(tag)) {
            tag = System.currentTimeMillis() + "";
        }
        baseCallBack.setTag(tag);
        //添加参数信息
        addPramaers();
        //添加头信息
        addHeadrs();
        if (HttpGlobalConfig.getInstance().getBaseurl() != null) {
            this.baseUrl = HttpGlobalConfig.getInstance().getBaseurl();
        }
        Observable observable = createObservable();
        HttpObserable httpObserable = new HttpObserable.Builder(observable)
                .setActivityEvent(activityEvent)
                .setFragmentEvent(fragmentEvent)
                .setBaseObserver(baseCallBack)
                .build();
        httpObserable.observer().subscribe(baseCallBack);

    }

    private Observable createObservable() {
        Observable observable = null;
        boolean hasBodyString = !TextUtils.isEmpty(jsonbody);
        RequestBody requestBody = null;
        if (hasBodyString) {
            String mediaType = isJson ? "application/json; charset=utf-8" : "text/plain;charset=utf-8";
            requestBody = RequestBody.create(okhttp3.MediaType.parse(mediaType), jsonbody);
        }
        //默认请求时POST
        if (method == null) {
            method = Method.POST;
        }
        if (HttpGlobalConfig.getInstance().getTimeout() != 0) {
            this.time = HttpGlobalConfig.getInstance().getTimeout();
        }
        if (this.time == 0) {
            this.time = HttpConstant.TIME_OUT;
        }
        if (HttpGlobalConfig.getInstance().getTimeUnit() != null) {
            this.timeUnit = HttpGlobalConfig.getInstance().getTimeUnit();
        }
        if (this.timeUnit == null) {
            this.timeUnit = HttpConstant.TIME_UNIT;
        }
        Log.e("liangxq", "createObservable: " + baseUrl);
        ApiServer apiServer = HttpManager.getInstance().getrerofit(baseUrl, time, timeUnit).create(ApiServer.class);
        switch (method) {
            case POST:
                if (isJson) {
                    observable = apiServer.postjson(apiUrl, requestBody, headres);
                } else {
                    observable = apiServer.post(apiUrl, paramser, headres);
                    Log.e("111", "createObservable: "+apiUrl );
                }
                break;
            case GET:
                observable = apiServer.get(apiUrl, paramser, headres);
                break;
            case DELETE:
                observable = apiServer.delete(apiUrl, paramser, headres);
                break;
            case PUT:
                observable = apiServer.put(apiUrl, paramser, headres);
                break;
        }
        return observable;
    }

    private void addHeadrs() {
        if (headres == null) {
            headres = new HashMap<>();
        }
        if (HttpGlobalConfig.getInstance().getBaseHeards() != null) {
            headres.putAll(HttpGlobalConfig.getInstance().getBaseHeards());
        }
    }

    private void addPramaers() {
        if (paramser == null) {
            paramser = new HashMap<>();
        }
        //添加公共的请求参数
        if (HttpGlobalConfig.getInstance().getBaseparems() != null) {
            paramser.putAll(HttpGlobalConfig.getInstance().getBaseparems());
        }

    }

    public static final class Builder {
        //请求方式
        Method method;
        //请求参数
        Map<String, Object> paramser;
        //请求头信息
        Map<String, Object> headres;
        //Rxjava绑定生命周期
        LifecycleProvider lifecycleProvider;
        //绑定Activity具体的生命周的
        ActivityEvent activityEvent;
        //绑定Fragment的具体的生命周期的
        FragmentEvent fragmentEvent;
        String baseUrl;
        //拼接的url
        String apiUrl;
        //是否是json上传表示
        boolean isJson;
        //json字符串
        String jsonbody;
        //超时时间
        long time;
        //时间单位
        TimeUnit timeUnit;

        public Builder get() {
            this.method = Method.GET;
            return this;
        }

        public Builder post() {
            this.method = Method.POST;

            return this;
        }

        public Builder delete() {
            this.method = Method.DELETE;
            return this;
        }

        public Builder put() {
            this.method = Method.PUT;
            return this;
        }

        public Builder() {
        }

        public Method getMethod() {
            return method;
        }

        public Map<String, Object> getParamser() {
            return paramser;
        }

        //设置参数的拼接
        public Builder setParamser(Map<String, Object> paramser) {
            if (paramser == null) {
                paramser = new HashMap<>();
            }
            //添加公共的请求参数
            if (HttpGlobalConfig.getInstance().getBaseparems() != null) {
                paramser.putAll(HttpGlobalConfig.getInstance().getBaseparems());
            }
            this.paramser = paramser;
            return this;
        }

        public Map<String, Object> getHeadres() {
            return headres;
        }

        //请求头的拼接
        public Builder setHeadres(Map<String, Object> headres) {
            if (headres == null) {
                headres = new HashMap<>();
            }
            if (HttpGlobalConfig.getInstance().getBaseHeards() != null) {
                headres.putAll(HttpGlobalConfig.getInstance().getBaseHeards());
            }
            this.headres = headres;
            return this;
        }

        public LifecycleProvider getLifecycleProvider() {
            return lifecycleProvider;
        }

        //用于绑定Rxjava的生命周期
        public Builder setLifecycleProvider(LifecycleProvider lifecycleProvider) {
            this.lifecycleProvider = lifecycleProvider;
            return this;
        }

        public ActivityEvent getActivityEvent() {
            return activityEvent;
        }

        public Builder setActivityEvent(ActivityEvent activityEvent) {
            this.activityEvent = activityEvent;
            return this;
        }

        public FragmentEvent getFragmentEvent() {
            return fragmentEvent;
        }

        public Builder setFragmentEvent(FragmentEvent fragmentEvent) {
            this.fragmentEvent = fragmentEvent;
            return this;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public Builder setBaseUrl(String baseUrl) {
            if (HttpGlobalConfig.getInstance().getBaseurl() != null) {
                this.baseUrl = HttpGlobalConfig.getInstance().getBaseurl();
            } else {
                this.baseUrl = baseUrl;
            }
            return this;
        }

        public String getApiUrl() {
            return apiUrl;
        }

        public Builder setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
            return this;
        }

        public long getTime() {
            return time;
        }

        public Builder setTime(long time) {
            if (HttpGlobalConfig.getInstance().getTimeout() != 0) {
                this.time = HttpGlobalConfig.getInstance().getTimeout();
            }
            if (time == 0) {
                this.time = HttpConstant.TIME_OUT;
            }
            this.time = time;
            return this;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        public Builder setTimeUnit(TimeUnit timeUnit) {
            if (HttpGlobalConfig.getInstance().getTimeUnit() != null) {
                this.timeUnit = HttpGlobalConfig.getInstance().getTimeUnit();
            }
            if (timeUnit == null) {
                this.time = HttpConstant.TIME_OUT;
            }
            this.timeUnit = HttpConstant.TIME_UNIT;
            return this;
        }

        public Builder setJsonBody(String jsonBody, boolean isJson) {
            this.jsonbody = jsonBody;
            this.isJson = isJson;
            return this;
        }

        public HttpClient build() {
            return new HttpClient(this);
        }
    }
}
