package com.namig.tahmazli.deezerandroidclient.utils;

import android.animation.ValueAnimator;

public final class AnimatorUtils {
    private AnimatorUtils() {
        throw new IllegalStateException(
                "It is forbidden to construct AnimatorUtils class instance"
        );
    }

    public static ValueAnimator createAnimatorOfInt(
            final AnimatorUpdateListener<Integer> listener,
            final int... values) {
        final ValueAnimator animator = ValueAnimator.ofInt(values);
        animator.addUpdateListener(animation ->
                listener.update((int) animation.getAnimatedValue()));
        return animator;
    }

    public static ValueAnimator createAnimatorOfFloat(
            final AnimatorUpdateListener<Float> listener,
            final float... values) {
        final ValueAnimator animator = ValueAnimator.ofFloat(values);
        animator.addUpdateListener(animation ->
                listener.update((float) animation.getAnimatedValue()));
        return animator;
    }

    public interface AnimatorUpdateListener<T> {
        void update(final T value);
    }
}
