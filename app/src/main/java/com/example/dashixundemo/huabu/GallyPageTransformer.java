package com.example.dashixundemo.huabu;


import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by wangjitao on 2016/10/20 0020.
 * 实现Viewpager的翻页效果PageTransformer
 */
public class GallyPageTransformer implements ViewPager.PageTransformer {
    private static final float min_scale = 0.85f;

    @Override
    public void transformPage(View page, float position) {
        float scaleFactor = Math.max(min_scale, 1 - Math.abs(position));
        float rotate = 20 * Math.abs(position);
        if (position < -1) {

        } else if (position < 0) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(rotate);
        } else if (position >= 0 && position < 1) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(-rotate);
        } else if (position >= 1) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(-rotate);
        }
    }
}
