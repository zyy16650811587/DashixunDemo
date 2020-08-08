package com.example.httplibrary1;

import com.example.httplibrary1.callback.BaseCallBack;
import com.example.httplibrary1.callback.BaseObserver;
import com.example.httplibrary1.exception.ExceptionEngine;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HttpObserable {
    LifecycleProvider lifecycleProvider;
    ActivityEvent activityEvent;
    FragmentEvent fragmentEvent;
    Observable observable;
    BaseObserver baseObserver;


    public HttpObserable(Builder buidler) {
        this.lifecycleProvider=buidler.lifecycleProvider;
        this.activityEvent=buidler.activityEvent;
        this.fragmentEvent=buidler.fragmentEvent;
        this.observable=buidler.observable;
        this.baseObserver=buidler.baseObserver;

    }
    public Observable map(){
        return observable.map(new Function<JsonElement,Object>() {
            @Override
            public Object apply(JsonElement o) throws Exception {
                return new Gson().toJson(o);
            }
        });
    };
    //错误信息的分类回调
private Observable onErrorResumeNext(){
    return bindlifecycle().onErrorResumeNext(new Function<Throwable, ObservableSource>() {
        @Override
        public ObservableSource apply(Throwable throwable) throws Exception {
            return Observable.error(ExceptionEngine.handleException(throwable));
        }
    });
}
private Observable doOnDispose() {
    if (baseObserver != null) {
        return onErrorResumeNext().doOnDispose(new Action() {
            @Override
            public void run() throws Exception {
                baseObserver.cancle();
            }
        });
    }
    return onErrorResumeNext();
}

    private Observable bindlifecycle() {
        Observable observable = map();
        if(lifecycleProvider !=null){
            if(activityEvent !=null || fragmentEvent !=null){
                if(activityEvent!=null&& fragmentEvent!=null){
                    return map().compose(lifecycleProvider.bindUntilEvent(activityEvent));
                }
                if(activityEvent !=null){
                    return map().compose(lifecycleProvider.bindUntilEvent(activityEvent));

                }
                if(fragmentEvent !=null){
                    return map().compose(lifecycleProvider.bindUntilEvent(fragmentEvent));
                }

            }else {
                return map().compose(lifecycleProvider.bindToLifecycle());
            }
        }
        return observable;
    }
    public Observable observer(){
    return doOnDispose().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    public static final class Builder{
        LifecycleProvider lifecycleProvider;
        ActivityEvent activityEvent;
        FragmentEvent fragmentEvent;
        Observable observable;
       BaseObserver baseObserver;



        public Builder setLifecycleProvider(LifecycleProvider lifecycleProvider) {
            this.lifecycleProvider = lifecycleProvider;
            return this;
        }



        public Builder setActivityEvent(ActivityEvent activityEvent) {
            this.activityEvent = activityEvent;
            return this;
        }



        public Builder setFragmentEvent(FragmentEvent fragmentEvent) {
            this.fragmentEvent = fragmentEvent;
            return this;
        }



        public Builder setObservable(Observable observable) {
            this.observable = observable;
            return this;
        }



        public Builder setBaseObserver(BaseObserver baseObserver) {
            this.baseObserver = baseObserver;
            return this;
        }
        public Builder(Observable observable) {
            this.observable = observable;
        }

        public HttpObserable build(){
            return new HttpObserable(this);
        }
    }

}
