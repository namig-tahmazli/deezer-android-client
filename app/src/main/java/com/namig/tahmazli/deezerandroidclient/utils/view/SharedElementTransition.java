package com.namig.tahmazli.deezerandroidclient.utils.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.os.Build;
import android.util.Log;
import android.util.Size;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namig.tahmazli.deezerandroidclient.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class SharedElementTransition {

    public static final String TAG = SharedElementTransition.class.getSimpleName();

    private final FrameLayout mParent;
    private final Map<String, Transition> mEnqueuedTransitions = new HashMap<>();
    private final int TRANSITION_ANIM_DURATION;

    public SharedElementTransition(final Window window) {
        mParent = new FrameLayout(window.getContext());
        window.addContentView(mParent, new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
        ));
        TRANSITION_ANIM_DURATION = window.getContext()
                .getResources().getInteger(R.integer.transition_anim_duration);
    }

    public void enqueue(final RecyclerView recyclerView,
                        final int adapterPosition,
                        final String[] transitionNames,
                        final Runnable onEnqueued) {
        final var viewHolder = recyclerView.findViewHolderForAdapterPosition(adapterPosition);
        if (viewHolder != null) {
            enqueue(viewHolder.itemView, transitionNames);
            onEnqueued.run();
        } else {
            ViewUtils.getNotifiedWhenViewIsAttached(recyclerView, adapterPosition,
                    v -> recyclerView.postDelayed(() -> {
                        if (v.isLaidOut() && v.isAttachedToWindow()) {
                            enqueue(v, transitionNames);
                            onEnqueued.run();
                        } else {
                            v.requestLayout();
                            ViewUtils.ensureViewIsPreDrawn(v, view -> {
                                enqueue(view, transitionNames);
                                onEnqueued.run();
                            });
                        }
                    }, 100));
            recyclerView.scrollToPosition(adapterPosition);
        }
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
        animatorSet.setDuration(TRANSITION_ANIM_DURATION);
        final List<Animator> animators = new ArrayList<>();
        animators.add(TransitionAnimators.createXYAnimator(startPoint, endPoint, transitioningView));
        animators.add(TransitionAnimators.createBoundsAnimator(startSize, endSize, transitioningView));

        if (transitioningView instanceof TextView tvStart) {
            if (targetView instanceof TextView tvEnd) {
                final float startTextSize = tvStart.getTextSize();
                Log.d(TAG, "transition: text start size: " + startTextSize);
                final float endTextSize = tvEnd.getTextSize();
                Log.d(TAG, "transition: text end size: " + endTextSize);
                final var sizeAnimator = AnimatorUtils.createAnimatorOfFloat(
                        (size) -> tvStart.setTextSize(TypedValue.COMPLEX_UNIT_PX, size),
                        startTextSize,
                        endTextSize);
                final int startColor = tvStart.getCurrentTextColor();
                final int endColor = tvEnd.getCurrentTextColor();

                if (startColor != endColor) {
                    final var colorAnimator = AnimatorUtils.createAnimatorOfColor(
                            tvStart::setTextColor,
                            startColor,
                            endColor
                    );

                    animators.add(colorAnimator);
                }

                animators.add(sizeAnimator);
            }
        }

        animatorSet.playTogether(animators);

        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
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

        view.setVisibility(View.INVISIBLE);
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

        if (transitioningView instanceof TextView tv) {
            final TextView textView = new TextView(tv.getContext());
            textView.setText(tv.getText());
            textView.setTextColor(tv.getCurrentTextColor());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.getTextSize());
            textView.setTypeface(tv.getTypeface());
            textView.setGravity(tv.getGravity());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                textView.setAllCaps(tv.isAllCaps());
            }

            final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    tv.getWidth(),
                    tv.getHeight()
            );

            textView.setLayoutParams(layoutParams);

            return textView;
        } else {
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
