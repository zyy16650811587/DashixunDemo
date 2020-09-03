package com.example.dashixundemo.intercater;

import com.example.dashixundemo.uitl.SpUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class InterCoptorLogin implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        List<String> headers = response.headers("Set_Cookie");
        if (headers!=null){
            SpUtil.setParam("cookie",headers);
        }
        return response;
    }
}
