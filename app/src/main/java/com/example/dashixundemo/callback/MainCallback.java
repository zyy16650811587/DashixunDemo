package com.example.dashixundemo.callback;

import com.example.dashixundemo.bean.ShowBean;

import java.util.List;

public interface MainCallback {
    void onSuccess(List<ShowBean> list);
    void onError(String s);
}
