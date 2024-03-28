package com.namig.tahmazli.deezerandroidclient.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.util.Size;
import android.view.View;
import android.widget.FrameLayout;

public final class TransitionAnimators {
    private TransitionAnimators() {
        throw new IllegalStateException(
                "It is forbidden to create TransitionAnimators instance"
        );
    }

    public static Animator createBoundsAnimator(final Size startBounds,
                                                 final Size endBounds,
                                                 final View view) {
        final FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) view.getLayoutParams();

        final ValueAnimator wAnimator = AnimatorUtils.createAnimatorOfInt(
                width -> {
                    layoutParams.width = width;
                    view.setLayoutParams(layoutParams);
                },
                startBounds.getWidth(),
                endBounds.getWidth());


        final ValueAnimator hAnimator = AnimatorUtils.createAnimatorOfInt(
                height -> {
                    layoutParams.height = height;
                    view.setLayoutParams(layoutParams);
                },
                startBounds.getHeight(),
                endBounds.getHeight());

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(wAnimator, hAnimator);
        return animatorSet;
    }

    public static Animator createXYAnimator(final PointF startPosition,
                                             final PointF endPosition,
                                             final View view) {
        final ValueAnimator xAnimator = AnimatorUtils.createAnimatorOfFloat(
                view::setX,
                startPosition.x, endPosition.x);

        final ValueAnimator yAnimator = AnimatorUtils.createAnimatorOfFloat(
                view::setY,
                startPosition.y, endPosition.y);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(xAnimator, yAnimator);
        return animatorSet;
    }
}
