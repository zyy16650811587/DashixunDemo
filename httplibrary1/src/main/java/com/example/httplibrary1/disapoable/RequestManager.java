package com.example.httplibrary1.disapoable;

import java.lang.ref.SoftReference;

import io.reactivex.disposables.Disposable;

public interface RequestManager {
     void add(String tag, Disposable disposable);
     void remove(String tag);
     void cancle(String tag);
     void cancleAll();

}
