package com.namig.tahmazli.deezerandroidclient.utils.presenter;

import android.os.Parcelable;

import androidx.lifecycle.SavedStateHandle;

import org.jetbrains.annotations.Nullable;

public abstract class Presenter<View, State extends Parcelable> {

    @Nullable
    private View mView;

    private final SavedStateHandle mSavedStateHandle;
    protected final State mState;

    protected Presenter(final SavedStateHandle handle) {
        mSavedStateHandle = handle;
        final State state = mSavedStateHandle.get("savedState");

        if (state == null) {
            mState = initialState();
        } else {
            mState = state;
        }
    }

    protected abstract State initialState();

    public final void attachView(final View view) {
        mView = view;
        initViewWithLatestState(view);
    }

    protected abstract void initViewWithLatestState(final View view);

    public final void detachView() {
        mView = null;
    }

    public final void saveState() {
        mSavedStateHandle.set("savedState", mState);
    }

    protected final void updateView(final ViewUpdater<View> viewUpdater) {
        @Nullable final View view = mView;
        if (view != null) {
            viewUpdater.update(view);
        }
    }

    protected interface ViewUpdater<View> {
        void update(final View view);
    }
}
