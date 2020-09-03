package com.example.mvplibrary.base2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.trello.rxlifecycle2.components.support.RxFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目名：Shopping
 * 包名：  com.example.mvplibrary.base
 * 文件名：BaseFragment
 * 创建者：liangxq
 * 创建时间：2020/8/5  15:32
 * 描述：TODO
 */
public abstract class BaseFragment extends RxFragment {
    public Activity mActivity;
    public Context context;
    private View rootView;
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity= (Activity) context;
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(initLayoutId(), (ViewGroup) rootView,false);
        }
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    protected abstract void initData();

    protected abstract int initLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rootView != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }

        if(unbinder!=null){
            unbinder.unbind();
        }
    }
}
