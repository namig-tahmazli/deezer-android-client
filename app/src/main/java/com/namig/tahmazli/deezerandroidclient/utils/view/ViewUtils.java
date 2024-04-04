package com.namig.tahmazli.deezerandroidclient.utils.view;

import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

public final class ViewUtils {
    private ViewUtils() {
        throw new IllegalStateException("It is forbidden to create an instance of ViewUtils");
    }

    public static void ensureViewIsLaidOut(final View view,
                                           final ViewLayoutNotifier notifier) {
        if (view.isLaidOut()) {
            notifier.notify(view);
        } else {
            if (view.isInLayout() || view.isLayoutRequested()) {
                view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        notifier.notify(view);
                    }
                });
            }
        }
    }

    public static void ensureViewIsPreDrawn(final View view,
                                            final ViewLayoutNotifier notifier) {
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                notifier.notify(view);
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
    }

    public static <T> int indexOfItem(final ListAdapter<T, ?> adapter,
                                      final T item,
                                      final BiFunction<T, T, Boolean> comparator) {
        final List<T> items = adapter.getCurrentList();

        return IntStream.range(0, items.size())
                .filter(i -> comparator.apply(items.get(i), item))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Could not find item %s in the list",
                                item)
                ));
    }

    public interface ViewLayoutNotifier {
        void notify(final View view);
    }

    public static void getNotifiedWhenViewIsAttached(
            final RecyclerView recyclerView,
            final int position,
            final ViewLayoutNotifier notifier) {
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                int pos = recyclerView.getChildAdapterPosition(view);
                if (pos == position) {
                    notifier.notify(view);
                    recyclerView.removeOnChildAttachStateChangeListener(this);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {

            }
        });
    }
}
