package com.example.dashixundemo.base;

import android.util.Log;

import com.example.dashixundemo.callback.MainCallback;
import com.example.dashixundemo.bean.RespoensePrams;
import com.example.dashixundemo.bean.ShowBean;
import com.example.dashixundemo.callback.ShowCallback;
import com.example.httplibrary1.client.HttpClient;
import com.example.httplibrary1.utils.JsonUtils;
import com.example.httplibrary1.utils.LogUtils;
import com.example.mvplibrary.BaseModel;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.List;

public class MainModel implements BaseModel {

    public void jiexi(MainCallback mainCallback) {
        HashMap<String, Object> headerMap = new HashMap<>();
        headerMap.put("Content-Type","application/json");
        RespoensePrams respoensePrams = new RespoensePrams();
        respoensePrams.setParentId(0);
        String s = JsonUtils.classToJson("0");
        new HttpClient.Builder()
                .post()
                .setApiUrl("category/getCategory")
                .setHeadres(headerMap)
            .setJsonBody(JsonUtils.classToJson(s),true)
              //  .setJsonBody(s,true)
                .build()
                .request(new ShowCallback<List<ShowBean>>() {
                    @Override
                    public void onError(String message, int code) {
                        Log.e("111", "onError: "+message );
                    }

                    @Override
                    public void cancle() {

                    }

                    @Override
                    protected void onSuccess(List<ShowBean> beans) {
                        LogUtils.e(beans.toString()+"==========");
                       mainCallback.onSuccess(beans);
                    }

                    @Override
                    public List<ShowBean> convert(JsonElement result) {
                       // Log.e("11111", "convert: "+result.toString());
                     //   return JsonUtils.jsonToClassList(result,ShowBean.class);
                        return null;
                    }
                });

    }
}
