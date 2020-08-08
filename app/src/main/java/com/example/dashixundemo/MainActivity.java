package com.example.dashixundemo;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.dashixundemo.fragment.MeFragment;
import com.example.dashixundemo.fragment.MessageFragment;
import com.example.dashixundemo.fragment.ShopFragment;
import com.example.dashixundemo.fragment.ShowFragment;
import com.example.dashixundemo.fragment.ZhuyeFragment;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationBar bnb;
    private FrameLayout fl;
    //默认选择第一个fragment
    private int lastSelectedPosition = 5;
    private ZhuyeFragment zhuyeFragment;
    private ShowFragment showFragment;
    private ShopFragment shopFragment;
    private MessageFragment messageFragment;
    private MeFragment meFragment;
    private FragmentManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        bnb = (BottomNavigationBar) findViewById(R.id.bnb);
        fl = (FrameLayout) findViewById(R.id.fl);

        zhuyeFragment = new ZhuyeFragment();
        showFragment = new ShowFragment();
        shopFragment = new ShopFragment();
        messageFragment = new MessageFragment();
        meFragment = new MeFragment();
        sm = getSupportFragmentManager();
        sm.beginTransaction().add(R.id.fl, zhuyeFragment).add(R.id.fl, showFragment)
                .add(R.id.fl, shopFragment).add(R.id.fl, messageFragment)
                .add(R.id.fl, meFragment)
                .show(zhuyeFragment)
                .hide(showFragment).hide(shopFragment)
                .hide(meFragment).hide(messageFragment)
                .commit();
        bnb

                .setMode(BottomNavigationBar.MODE_FIXED)
                /**
                 *  setMode() 内的参数有三种模式类型：
                 *  MODE_DEFAULT 自动模式：导航栏Item的个数<=3 用 MODE_FIXED 模式，否则用 MODE_SHIFTING 模式
                 *  MODE_FIXED 固定模式：未选中的Item显示文字，无切换动画效果。
                 *  MODE_SHIFTING 切换模式：未选中的Item不显示文字，选中的显示文字，有切换动画效果。
                 */
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                /**
                 *  setBackgroundStyle() 内的参数有三种样式
                 *  BACKGROUND_STYLE_DEFAULT: 默认样式 如果设置的Mode为MODE_FIXED，将使用BACKGROUND_STYLE_STATIC
                 *                                    如果Mode为MODE_SHIFTING将使用BACKGROUND_STYLE_RIPPLE。
                 *  BACKGROUND_STYLE_STATIC: 静态样式 点击无波纹效果
                 *  BACKGROUND_STYLE_RIPPLE: 波纹样式 点击有波纹效果
                 */
                .setActiveColor("#FF107FFD") //选中颜色
                .setInActiveColor("#808080") //未选中颜色
                .setBarBackgroundColor("#ffffff");//导航栏背景色

        bnb.addItem(new BottomNavigationItem(R.drawable.myshop,"首页"))
                .addItem(new BottomNavigationItem(R.drawable.myshop2,"分类"))
                .addItem(new BottomNavigationItem(R.drawable.myshop3,"购物车"))
                .addItem(new BottomNavigationItem(R.drawable.myshop4,"消息"))
                .addItem(new BottomNavigationItem(R.drawable.myshop5,"我的"))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();

        bnb.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if(position==0){
               sm.beginTransaction().show(zhuyeFragment)
                            .hide(showFragment).hide(shopFragment)
                            .hide(messageFragment).hide(meFragment)
                            .commit();

                }else if(position==1){
                    sm.beginTransaction().show(showFragment)
                            .hide(zhuyeFragment).hide(shopFragment)
                            .hide(messageFragment).hide(meFragment)
                            .commit();

                }else if(position==2){
                    sm.beginTransaction().show(shopFragment)
                            .hide(showFragment).hide(zhuyeFragment)
                            .hide(messageFragment).hide(meFragment)
                            .commit();
                }else if (position==3){
                    sm.beginTransaction().show(messageFragment)
                            .hide(showFragment).hide(shopFragment)
                            .hide(zhuyeFragment).hide(meFragment)
                            .commit();
                }else {
                    sm.beginTransaction().show(meFragment)
                            .hide(showFragment).hide(shopFragment)
                            .hide(messageFragment).hide(zhuyeFragment)
                            .commit();
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });


    }
}
