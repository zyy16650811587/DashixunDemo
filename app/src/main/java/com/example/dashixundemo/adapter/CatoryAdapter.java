package com.example.dashixundemo.adapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dashixundemo.R;
import com.example.dashixundemo.bean.ShowBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CatoryAdapter extends BaseQuickAdapter<ShowBean, BaseViewHolder> {
       public SparseArray<Boolean> checks;
    public int checksposition=-1;
    public CatoryAdapter( @Nullable List<ShowBean> data) {
        super(R.layout.show_item_rcy, data);
      checks=new SparseArray<>();
        for (int i=0;i<data.size();i++){
            checks.put(i,false);
        }
//        for (int i = 0; i < data.size(); i++) {
//            data.get(i).setCheck(false);
//        }

    }

    @Override
    public void setNewData(@Nullable List<ShowBean> data) {
        super.setNewData(data);
        if(data!=null && data.size()==0){
            for (int i=0;i<data.size();i++){
                checks.put(i,false);
            }

//            for (int i = 0; i < data.size(); i++) {
//                data.get(i).setCheck(false);
//            }
        }
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, ShowBean beans) {
        TextView tv = baseViewHolder.getView(R.id.tv);
        int position = baseViewHolder.getPosition();
        boolean check = beans.isCheck();

        if (getmParme()==position){
            tv.setBackgroundResource(R.color.colorBar);
        } else {
            tv.setBackgroundResource(R.color.cardview_shadow_start_color);
        }
//        if(checks.get(position)){
//            checksposition=position;
//            tv.setSelected(true);
//        }else {
//            tv.setSelected(false);
//        }

        tv.setText(beans.getCategoryName());
    }

    int mParme;

    public int getmParme() {
        return mParme;
    }

    public void setmParme(int mParme) {
        this.mParme = mParme;
    }
     //       if (check){
//            tv.setSelected(true);
//
//        } else {
//            tv.setSelected(false);
//            beans.setCheck(false);
//        }


}
