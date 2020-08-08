package com.example.httplibrary1.callback;

import android.text.TextUtils;

import com.example.httplibrary1.disapoable.RequestManagerIml;
import com.example.httplibrary1.exception.ApiException;
import com.example.httplibrary1.exception.ExceptionEngine;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public  abstract class BaseObserver<T> implements Observer<T> {
  String tag;
    @Override
    public void onSubscribe(Disposable d) {
        if(!TextUtils.isEmpty(tag)){
          RequestManagerIml.getInstance().add(tag,d);
        }
    }

    @Override
    public void onNext(T t) {
      if(!TextUtils.isEmpty(tag)){
        RequestManagerIml.getInstance().remove(tag);
      }

    }



  @Override
    public void onError(Throwable e) {
      if(e instanceof ApiException){
      ApiException apiException= (ApiException) e;
      apiException.setMsg(e.getMessage());
      }else {
        onError("未知异常", ExceptionEngine.UN_KNOWN_ERROR);
      }
      if(!TextUtils.isEmpty(tag)){
        RequestManagerIml.getInstance().remove(tag);
      }

    }

  @Override
    public void onComplete() {
        if(!RequestManagerIml.getInstance().isDispose(tag)){
        RequestManagerIml.getInstance().cancle(tag);
    }
    }
  public abstract void onError(String message, int code) ;

  public abstract void cancle();
  public void setTag(String tag) {
    this.tag = tag;
  }
}


