package com.example.mvplibrary.base2;


import com.example.mvplibrary.present.BasePresent;
import com.example.mvplibrary.view.BaseView;

/**
 * 项目名：Shopping
 * 包名：  com.example.mvplibrary.base
 * 文件名：BaseMvpActivity
 * 创建者：liangxq
 * 创建时间：2020/8/5  15:23
 * 描述：TODO
 */
public abstract class BaseMvpActivity<V extends BaseView, P extends BasePresent<V>> extends BaseActivity {
    public P mPresenter;
    @Override
    protected void initEvent() {
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    protected abstract P initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destoryView();
            mPresenter = null;
        }
    }
}
