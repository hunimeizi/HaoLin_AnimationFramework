package com.haolin.animation.framework.view;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.MonthDisplayHelper;
import android.widget.FrameLayout;

import com.haolin.animation.framework.listener.AnimationInterface;

/**
 * 作者：haoLin_Lee on 2019/04/20 13:11
 * 邮箱：Lhaolin0304@sina.com
 * class:
 */
public class MyFrameLayout extends FrameLayout implements AnimationInterface {

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private static final int TRANSLATION_FROM_TOP = 0x01;
    private static final int TRANSLATION_FROM_BOTTOM = 0x02;
    private static final int TRANSLATION_FROM_LEFT = 0x04;
    private static final int TRANSLATION_FROM_RIGHT = 0x08;

    private static ArgbEvaluator sArgbEvaluator = new ArgbEvaluator();

    private boolean mAnimation_alpha; //是否需要透明度
    private boolean mAnimation_scaleX;//是否需要X方向缩放
    private boolean mAnimation_scaleY;//是否需要Y方向缩放
    private int mAnimation_fromBgColor;//背景颜色变化开始
    private int mAnimation_toBgColor;//背景颜色变化结束
    private int mAnimation_translation;//平移值
    private int mWidth;
    private int mHeight;

    public void setmAnimation_alpha(boolean mAnimation_alpha) {
        this.mAnimation_alpha = mAnimation_alpha;
    }

    public void setmAnimation_scaleX(boolean mAnimation_scaleX) {
        this.mAnimation_scaleX = mAnimation_scaleX;
    }

    public void setmAnimation_scaleY(boolean mAnimation_scaleY) {
        this.mAnimation_scaleY = mAnimation_scaleY;
    }

    public void setmAnimation_fromBgColor(int mAnimation_fromBgColor) {
        this.mAnimation_fromBgColor = mAnimation_fromBgColor;
    }

    public void setmAnimation_toBgColor(int mAnimation_toBgColor) {
        this.mAnimation_toBgColor = mAnimation_toBgColor;
    }

    public void setmAnimation_translation(int mAnimation_translation) {
        this.mAnimation_translation = mAnimation_translation;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    public void onDisScroll(float ratio) {
        //判断是否有动画 开启动画
        //ratio 0~1
        if (mAnimation_alpha) {
            setAlpha(ratio);
        }
        if (mAnimation_scaleX) {
            setScaleX(ratio);
        }
        if (mAnimation_scaleY) {
            setScaleY(ratio);
        }
        if (isTranslationFrom(TRANSLATION_FROM_BOTTOM)) { //是否从底部
            setTranslationY(mHeight * (1 - ratio));
        }
        if (isTranslationFrom(TRANSLATION_FROM_TOP)) {
            setTranslationY(-mHeight * (1 - ratio));
        }
        if (isTranslationFrom(TRANSLATION_FROM_LEFT)) {
            setTranslationX(-mWidth * (1 - ratio));
        }
        if (isTranslationFrom(TRANSLATION_FROM_RIGHT)) {
            setTranslationX(mWidth * (1 - ratio));
        }
        //判断从什么颜色到什么颜色
        if (mAnimation_fromBgColor !=-1&&mAnimation_toBgColor!=-1){
            setBackgroundColor((Integer) sArgbEvaluator.evaluate(ratio,mAnimation_fromBgColor,mAnimation_toBgColor));
        }
    }

    private boolean isTranslationFrom(int translationMask) {
        //fromLeft|fromBottom & fromBottom = fromBottom
        return mAnimation_translation != -1 && (mAnimation_translation & translationMask) == translationMask;
    }

    @Override
    public void onResetDiscroll() {
        int ratio = 0;
        if (mAnimation_alpha) {
            setAlpha(ratio);
        }
        if (mAnimation_scaleX) {
            setScaleX(ratio);
        }
        if (mAnimation_scaleY) {
            setScaleY(ratio);
        }
        if (isTranslationFrom(TRANSLATION_FROM_BOTTOM)) { //是否从底部
            setTranslationY(mHeight * (1 - ratio));
        }
        if (isTranslationFrom(TRANSLATION_FROM_TOP)) {
            setTranslationY(-mHeight * (1 - ratio));
        }
        if (isTranslationFrom(TRANSLATION_FROM_LEFT)) {
            setTranslationY(-mWidth * (1 - ratio));
        }
        if (isTranslationFrom(TRANSLATION_FROM_RIGHT)) {
            setTranslationY(mWidth * (1 - ratio));
        }

    }
}
