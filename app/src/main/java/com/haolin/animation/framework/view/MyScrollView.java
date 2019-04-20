package com.haolin.animation.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.haolin.animation.framework.listener.AnimationInterface;

/**
 * 作者：haoLin_Lee on 2019/04/20 15:48
 * 邮箱：Lhaolin0304@sina.com
 * class:
 */
public class MyScrollView extends ScrollView {

    MyLinearLayout mContext;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContext = (MyLinearLayout) getChildAt(0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int scrollviewHeight = getHeight();
        for (int i = 0; i < mContext.getChildCount(); i++) {
            View child = mContext.getChildAt(i);
            if (!(child instanceof AnimationInterface)){
                continue;
            }
            AnimationInterface animationInterface = (AnimationInterface) child;
            int childTop = child.getTop();
            int childHeight = child.getHeight();
            int absoluteTop = childTop - t;
            if (absoluteTop <= scrollviewHeight) {
                int visibleGap = scrollviewHeight - absoluteTop;
                float ratio = visibleGap / (float) childHeight;
                //确保ratio 为 0-1范围
                animationInterface.onDisScroll(clamp(ratio, 1f, 0f));
            } else {
                animationInterface.onResetDiscroll();
            }
        }
    }

    private float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, max), min);
    }
}
