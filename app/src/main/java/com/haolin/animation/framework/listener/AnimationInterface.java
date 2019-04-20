package com.haolin.animation.framework.listener;

/**
 * 作者：haoLin_Lee on 2019/04/20 19:46
 * 邮箱：Lhaolin0304@sina.com
 * class:
 */
public interface AnimationInterface {

    /**
     * 当滑动的时候调用该方法，用来控制里面的执行动画
     * @param ratio 值
     */
    void onDisScroll(float ratio);

    /**
     * 重置动画
     */
    void onResetDiscroll();
}
