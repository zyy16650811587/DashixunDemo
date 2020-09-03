package com.example.mvplibrary.base2;


import com.example.mvplibrary.present.BasePresent;
import com.example.mvplibrary.view.BaseView;


public abstract class BaseMvpFragment<V extends BaseView,P extends BasePresent<V>> extends BaseFragment {
    public P mPresenter;
    @Override
    public void initData() {
        mPresenter=initPreszenter();
        if(mPresenter!=null){
            mPresenter.attachView((V) this);
            initEvent();
        }
    }

    protected abstract P initPreszenter();

    protected abstract void initEvent();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mPresenter!=null){
            mPresenter.destoryView();
            mPresenter=null;
        }
    }
}
