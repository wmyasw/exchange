package com.jdjt.exchange.util;

import android.graphics.Color;
import android.view.View;

import com.android.pc.ioc.app.Ioc;
import com.jdjt.exchange.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * @author wmy
 * @Description: 编写工具类，设置动画的 静态方法
 * @FileName:AnimationUtils
 * @Package com.jdjt.exchange.util
 * @Date 2016/9/9 16:12
 */
public class AnimationUtils {
    /**
     * 位移动画
     * @param content 执行位移 计算高度的 内容控件
     * @param isShow  判断向上位移 和向下位移， 为false 向上位移，为true 向下位移
     */
    public static void showHideAnimation(final View content, boolean isShow, int translation) {
        if(translation==0){
            translation = isShow ? -content.getHeight() : content.getHeight();
        }
        final int finalTranslation = translation;
        ViewPropertyAnimator.animate(content).translationYBy(finalTranslation).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).setDuration(500).start();
    }
    /**
     * 开始背景动画（此处为属性动画）
     */
    public  static void startBackgroundAnimator(final View view){
        /*
         *参数解释：
         *target：设置属性动画的目标类，此处是当前自定义view所以使用this
         *propertyName:属性名称。（要对View的那个属性执行动画操作）
         *values数组：根据时间的推移动画将根据数组的内容进行改变
         */

        int colorStar= Ioc.getIoc().getApplication().getResources().getColor(R.color.white);
        int colorEnd= Ioc.getIoc().getApplication().getResources().getColor(R.color.half_full);
//        ValueAnimator anim = ObjectAnimator.ofInt(view, "backgroundColor",colorStar,colorEnd);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorStar,colorEnd);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.setBackgroundColor((Integer)animator.getAnimatedValue());
            }
        });
        colorAnimation.setDuration(500);
        colorAnimation.start();

    }
}
