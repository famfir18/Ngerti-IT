package com.app.ngertiit.Util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import androidx.recyclerview.widget.RecyclerView;

public class AnimationUtilDown {
    public static void animate(RecyclerView.ViewHolder holder, boolean goesDown){

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown ? 60 : -60, 0);
        animatorTranslateY.setDuration(1100);

        animatorSet.playTogether(animatorTranslateY);
        animatorSet.start();

    }
}
