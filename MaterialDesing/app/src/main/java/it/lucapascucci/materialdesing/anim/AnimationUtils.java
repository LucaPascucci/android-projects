package it.lucapascucci.materialdesing.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.Interpolator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by Luca on 25/03/15.
 */
public class AnimationUtils {

    public static void animate(RecyclerView.ViewHolder holder, boolean goesDown){

        YoYo.with(Techniques.RubberBand)
                .duration(1000)
                .playOn(holder.itemView);

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX" ,0.5F, 0.8F, 1.0F);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0.5F, 0.8F, 1.0F);
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView,"translationY",goesDown==true?100:-100,0);
        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(holder.itemView, "translationX", -25, 25, -20, 20, -15, 15, -10, 10, -5, 5, 0);
        animatorSet.playTogether(animatorTranslateX, animatorTranslateY, animatorScaleX, animatorScaleY);
        animatorSet.setInterpolator(new AnticipateInterpolator());
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

    public static void animateToolbar(View containerToolbar){

    }
}
