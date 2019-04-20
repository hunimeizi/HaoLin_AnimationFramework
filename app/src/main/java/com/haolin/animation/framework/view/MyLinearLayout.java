package com.haolin.animation.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.haolin.animation.framework.R;

/**
 * 作者：haoLin_Lee on 2019/04/20 13:09
 * 邮箱：Lhaolin0304@sina.com
 * class:
 */
public class MyLinearLayout extends LinearLayout {

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {

        return new MyLayoutParams(getContext(), attrs);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        MyLayoutParams myLayoutParams = (MyLayoutParams) params;
        if (!isDisScrollVable(myLayoutParams)) {
            super.addView(child, index, params);
        } else {
            MyFrameLayout myFrameLayout = new MyFrameLayout(getContext(), null);
            myFrameLayout.setmAnimation_alpha(myLayoutParams.mAnimation_alpha);
            myFrameLayout.setmAnimation_fromBgColor(myLayoutParams.mAnimation_fromBgColor);
            myFrameLayout.setmAnimation_scaleX(myLayoutParams.mAnimation_scaleX);
            myFrameLayout.setmAnimation_scaleY(myLayoutParams.mAnimation_scaleY);
            myFrameLayout.setmAnimation_toBgColor(myLayoutParams.mAnimation_toBgColor);
            myFrameLayout.setmAnimation_translation(myLayoutParams.mAnimation_translation);
            myFrameLayout.addView(child);
            super.addView(myFrameLayout, index, params);
        }
    }

    private boolean isDisScrollVable(MyLayoutParams params) {
        return params.mAnimation_alpha ||
                params.mAnimation_scaleX ||
                params.mAnimation_scaleY ||
                params.mAnimation_translation != -1 ||
                (params.mAnimation_toBgColor != -1 &&
                        params.mAnimation_fromBgColor != -1);

    }

    private class MyLayoutParams extends LayoutParams {

        private boolean mAnimation_alpha; //是否需要透明度
        private boolean mAnimation_scaleX;//是否需要X方向缩放
        private boolean mAnimation_scaleY;//是否需要Y方向缩放
        private int mAnimation_fromBgColor;//背景颜色变化开始
        private int mAnimation_toBgColor;//背景颜色变化结束
        private int mAnimation_translation;//平移值


        MyLayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.animation_layoutParams);
            mAnimation_alpha = typedArray.getBoolean(R.styleable.animation_layoutParams_animation_alpha, false);
            mAnimation_scaleX = typedArray.getBoolean(R.styleable.animation_layoutParams_animation_scaleX, false);
            mAnimation_scaleY = typedArray.getBoolean(R.styleable.animation_layoutParams_animation_scaleY, false);
            mAnimation_fromBgColor = typedArray.getInt(R.styleable.animation_layoutParams_animation_fromBgColor, -1);
            mAnimation_toBgColor = typedArray.getInt(R.styleable.animation_layoutParams_animation_toBgColor, -1);
            mAnimation_translation = typedArray.getInt(R.styleable.animation_layoutParams_animation_translation, -1);
            typedArray.recycle();
        }
    }
}
