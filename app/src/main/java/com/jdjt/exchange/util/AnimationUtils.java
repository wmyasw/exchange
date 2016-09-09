package com.jdjt.exchange.util;

import android.view.View;

import com.nineoldandroids.animation.Animator;
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

}
