package com.example.mvplibrary.model;

import com.example.mvplibrary.BaseModel;

import java.util.HashMap;

public class ModelFractory {
    //创建model集合
    private static HashMap<String, BaseModel> modelHashMap=new HashMap<>();
    //泛型方法
    public static <M extends BaseModel>M createModel(Class<M> mClass){
        if(modelHashMap.get(mClass)!=null){
            return (M) modelHashMap.get(mClass);
        }
        M mModel=null;
        try {
            mModel = mClass.newInstance();
            if(mModel!=null){
                    modelHashMap.put(mClass.getName(),mModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mModel;
    }
}
