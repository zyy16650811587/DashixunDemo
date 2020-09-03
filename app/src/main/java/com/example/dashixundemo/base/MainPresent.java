package com.example.dashixundemo.base;

import com.example.dashixundemo.callback.MainCallback;
import com.example.dashixundemo.bean.ShowBean;
import com.example.httplibrary1.utils.LogUtils;
import com.example.mvplibrary.model.ModelFractory;
import com.example.mvplibrary.present.BasePresent;

import java.util.List;

public class
MainPresent extends BasePresent<MainView> implements MainCallback {

    @Override
    public void onSuccess(List<ShowBean> bean) {
        mView.Tdata(bean);
    }

    @Override
    public void onError(String s) {


    }

    public void jiexi() {
        MainModel model = ModelFractory.createModel(MainModel.class);
        model.jiexi(this);

        LogUtils.e("================");
    }
}
