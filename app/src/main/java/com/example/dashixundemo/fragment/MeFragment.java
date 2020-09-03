package com.example.dashixundemo.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dashixundemo.R;
import com.example.dashixundemo.activity.UserActivity;
import com.example.dashixundemo.uitl.SpUtil;
import com.example.dashixundemo.activity.LoginActivity;
import com.example.dashixundemo.activity.PeopleActivity;
import com.example.dashixundemo.bean.LoginBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    private TextView me_login_register;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_me, container, false);
        EventBus.getDefault().register(this);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        me_login_register = inflate.findViewById(R.id.me_login_register);
        TextView tv_shezhi = inflate.findViewById(R.id.tv_shezhi);
        ImageView im_tou = inflate.findViewById(R.id.im_tou);
        im_tou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String cookie = (String) SpUtil.getParam("cookie", "");
//                if(TextUtils.isEmpty(cookie)){
//                    Intent intent = new Intent(getActivity(), LoginActivity.class);
//                    startActivity(intent);
//                }else {
                    Intent intent = new Intent(getActivity(), UserActivity.class);
                    startActivity(intent);
//                }
            }
        });
        tv_shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SpUtil.clear();
                String user = (String) SpUtil.getParam("user", "");
                if (!TextUtils.isEmpty(user)){
                    me_login_register.setText(user);
                } else {
                    me_login_register.setText("登录/注册");
                }
            }
        });
        String user = (String) SpUtil.getParam("user", "");
        if (!TextUtils.isEmpty(user)){
            me_login_register.setText(user);
        } else {
            me_login_register.setText("登录/注册");
        }

        me_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cookie = (String) SpUtil.getParam("cookie", "");

                Integer token = (Integer) SpUtil.getParam("token", 0);

                if(token==0){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), PeopleActivity.class);
                    startActivity(intent);
                }


            }
        });

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mag(LoginBean loginBean){
        String userMobile = loginBean.getUserMobile();
        String user = (String) SpUtil.getParam("user", "");
        me_login_register.setText(userMobile);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
