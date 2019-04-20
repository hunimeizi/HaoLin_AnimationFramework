# HaoLin_AnimationFramework -- 动画框架

## 直接看效果图
<img width="300"  src="https://github.com/hunimeizi/HaoLin_AnimationFramework/blob/master/haolin_animation_framework.gif"/>

 ## 关于动画框架
- 添加自定义属性值

  ① 在values的attrs里加入属性
    ```xml
          <declare-styleable name="animation_layoutParams">
              <attr name="animation_alpha" format="boolean" />
              <attr name="animation_scaleX" format="boolean" />
              <attr name="animation_scaleY" format="boolean" />
              <attr name="animation_fromBgColor" format="color" />
              <attr name="animation_toBgColor" format="color" />
              <attr name="animation_translation" />
          </declare-styleable>

          <attr name="animation_translation">
              <flag name="fromTop" value="0x01" />
              <flag name="fromBottom" value="0x02" />
              <flag name="fromLeft" value="0x04" />
              <flag name="fromRight" value="0x08" />
          </attr>
    ```
   ② 在Activity布局中添加需要动画的系统View中加入
   ```xml
            animation:animation_alpha="true"  //是否开启渐变
            animation:animation_scaleY="true" //是否Y轴移动
            animation:animation_scaleX="true" //是否X轴移动
            animation:animation_translation="fromLeft|fromBottom" // 从左下方向右上方移进来
            animation:animation_translation="fromRight|fromBottom" // 从右下方向左上方移进来
            animation:animation_fromBgColor="#ff0000" //从该颜色开始
            animation:animation_toBgColor="#88ee66"   //到改颜色结束
   ```
   ③ 完整的布局代码
   ```xml
            <?xml version="1.0" encoding="utf-8"?>
                <com.haolin.animation.framework.view.MyScrollView xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:animation="http://schemas.android.com/apk/res-auto"
                android:layout_width="match_parent"
                android:layout_height="match_parent">

                <com.haolin.animation.framework.view.MyLinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent">

                <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="662dp"
                    android:background="@mipmap/image1"
                    animation:animation_alpha="true"
                    animation:animation_scaleY="true"/>

                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="200dp"
                    android:background="#007788"
                    android:gravity="center_horizontal|center_vertical"
                    android:text="带上行李，去旅行"
                    animation:animation_alpha="true"
                    animation:animation_scaleY="true" />

               <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="200dp"
                    android:src="@mipmap/image2"
                    animation:animation_alpha="true"
                    animation:animation_translation="fromLeft|fromBottom" />

              <TextView
                     android:layout_width="match_parent"
                     android:layout_height="200dp"
                     android:background="#007788"
                     android:gravity="center_horizontal|center_vertical"
                     android:text="带上行李，去旅行"
                     animation:animation_alpha="true"
                     animation:animation_translation="fromRight|fromBottom" />

               <TextView
                    android:layout_width="match_parent"
                    android:layout_height="200dp"
                    android:gravity="center_horizontal|center_vertical"
                    android:text="带上行李，去旅行"
                    animation:animation_fromBgColor="#ff0000"
                    animation:animation_toBgColor="#88ee66"
                    animation:animation_alpha="true"
                    animation:animation_scaleY="true" />
            </com.haolin.animation.framework.view.MyLinearLayout>
            </com.haolin.animation.framework.view.MyScrollView>
  ```
### 自定义View
- MyLinearLayout
   ```xml
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

    ```
- MyFrameLayout

    ```xml
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

    ```
    
### MyScrollView

    ```xml

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

    ```
