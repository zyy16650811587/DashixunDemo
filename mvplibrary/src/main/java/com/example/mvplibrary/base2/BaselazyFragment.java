package com.example.mvplibrary.base2;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mvplibrary.present.BasePresent;
import com.example.mvplibrary.view.BaseView;

import androidx.annotation.Nullable;

/**
 * 项目名：AndroidAptDemo
 * 包名：  com.liangxq.mymvpapp.base
 * 文件名：BaselazyFragment
 * 创建者：liangxq
 * 创建时间：2020/2/9  16:17
 * 描述：TODO
 */
public abstract class BaselazyFragment<V extends BaseView, P extends BasePresent<V>> extends BaseFragment {

    boolean mIsPrepare = false;        //初始化视图
    boolean mIsVisible = false;        //不可见
    boolean mIsFirstLoad = true;    //第一次加载

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mIsPrepare = true;
        lazyLoad();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mIsVisible = true;
            lazyLoad();
        } else {
            mIsVisible = false;
        }
    }

    protected abstract void lazyinitData();

    private void lazyLoad() {
        if (mIsPrepare && mIsVisible && mIsFirstLoad) {
            lazyinitData();
            mIsFirstLoad=false;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsPrepare = false;
        mIsVisible = false;
        mIsFirstLoad = true;
    }

}
