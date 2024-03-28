package com.namig.tahmazli.deezerandroidclient.utils.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnticipateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SharedElementTransition {

    public static final String TAG = SharedElementTransition.class.getSimpleName();

    private final FrameLayout mParent;
    private final Map<String, Transition> mEnqueuedTransitions = new HashMap<>();

    public SharedElementTransition(final Window window) {
        mParent = new FrameLayout(window.getContext());
        window.addContentView(mParent, new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
        ));
    }

    public void enqueue(final View view,
                        final String[] transitionNames) {
        for (String transitionName : transitionNames) {
            ViewUtils.ensureViewIsLaidOut(view, v -> {
                final Transition transition = create(v, transitionName);
                mEnqueuedTransitions.put(transitionName, transition);
            });
        }
    }

    public void transition(final View view,
                           final String[] transitionNames) {
        for (String transitionName : transitionNames) {
            final Transition transition = mEnqueuedTransitions.get(transitionName);
            Objects.requireNonNull(transition,
                    String.format("Could not find a transition for %s",
                            transitionName));

            ViewUtils.ensureViewIsLaidOut(view, v ->
                    transition(transition, v, transitionName));
        }
    }

    private void transition(final Transition activeTransition,
                            final View view,
                            final String transitionName) {

        final View targetView = findTransitioningView(view, transitionName)
                .orElseThrow(() -> new IllegalStateException(String.format(
                        "Could not find target view with transition name %s",
                        transitionName
                )));

        final View transitioningView = activeTransition.transitioningView();

        final PointF startPoint = activeTransition.startingPoint;
        final PointF endPoint = calculateViewPositionOnWindow(targetView);

        final Size startSize = new Size(
                transitioningView.getMeasuredWidth(),
                transitioningView.getMeasuredHeight());
        final Size endSize = new Size(
                targetView.getMeasuredWidth(),
                targetView.getMeasuredHeight());

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                TransitionAnimators.createXYAnimator(startPoint, endPoint, transitioningView),
                TransitionAnimators.createBoundsAnimator(startSize, endSize, transitioningView)
        );
        animatorSet.setInterpolator(new AnticipateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {
                super.onAnimationStart(animation);
                Log.d(TAG, "onAnimationStart: ");
                targetView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.d(TAG, "onAnimationEnd: ");
                targetView.setVisibility(View.VISIBLE);

                mParent.removeView(transitioningView);
                mEnqueuedTransitions.remove(transitionName);

                animatorSet.removeListener(this);
            }
        });
        animatorSet.start();
    }

    private Transition create(final View parent,
                              final String transitionName) {
        final View view = findTransitioningView(parent, transitionName)
                .orElseThrow(() -> new IllegalStateException(String.format(
                        "Could not find a transitioning view with name %s",
                        transitionName
                )));

        final PointF positionOnWindow = calculateViewPositionOnWindow(view);
        final var snapshotView = drawSnapshotOfTransitioningViewOnWindow(view, positionOnWindow);
        return new Transition(snapshotView, positionOnWindow);
    }

    private View drawSnapshotOfTransitioningViewOnWindow(final View transitioningView,
                                                         final PointF coordinates) {

        final View snapshotView = createSnapshotViewOfTransitioningView(transitioningView);
        mParent.addView(snapshotView);

        snapshotView.setX(coordinates.x);
        snapshotView.setY(coordinates.y);

        return snapshotView;
    }

    private static View createSnapshotViewOfTransitioningView(final View transitioningView) {
        final ImageView snapshotView = new ImageView(transitioningView.getContext());
        final Bitmap snapshot = getSnapshotOfTransitioningView(transitioningView);

        snapshotView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        snapshotView.setImageBitmap(snapshot);

        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                snapshot.getWidth(),
                snapshot.getHeight());

        snapshotView.setLayoutParams(layoutParams);
        return snapshotView;
    }
    private static Bitmap getSnapshotOfTransitioningView(final View transitioningView) {
        final Bitmap bitmap = Bitmap.createBitmap(
                transitioningView.getWidth(),
                transitioningView.getHeight(),
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        transitioningView.draw(canvas);

        return bitmap;
    }

    private static Optional<View> findTransitioningView(final View view,
                                                        final String transitionName) {

        if (transitionName.equals(view.getTransitionName())) {
            return Optional.of(view);
        } else if (view instanceof ViewGroup parent) {
            for (int i = 0; i < parent.getChildCount(); i++) {
                final View child = parent.getChildAt(i);
                Optional<View> optionalView = findTransitioningView(child, transitionName);
                if (optionalView.isPresent())
                    return optionalView;
            }
        }

        return Optional.empty();
    }

    private static PointF calculateViewPositionOnWindow(final View view) {
        final int[] location = new int[2];
        view.getLocationInWindow(location);

        float viewX = location[0];
        float viewY = location[1];

        Log.d(TAG, String.format(Locale.getDefault(),
                "calculateTransitioningViewPosition: [%f, %f]",
                viewX,
                viewY));

        return new PointF(viewX, viewY);
    }

    public record Transition(View transitioningView,
                             PointF startingPoint) {
    }
}
