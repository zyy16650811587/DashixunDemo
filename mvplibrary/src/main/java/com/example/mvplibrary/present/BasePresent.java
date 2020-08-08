package com.example.mvplibrary.present;

import com.example.mvplibrary.view.BaseView;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.ref.WeakReference;

public class BasePresent<V extends BaseView> {
    public V mView;
    private WeakReference<V> weakReference;
    public void attachView(V v){
        weakReference =new WeakReference<V>(v);
       mView=weakReference.get();
    }
    //返回视图层对象
    public LifecycleProvider lifecycleProvider(){
        return (LifecycleProvider) mView;
    }
    //销毁方法
    public void destoryView(){
        if(weakReference!=null){
            weakReference.clear();
        }
    }
}
