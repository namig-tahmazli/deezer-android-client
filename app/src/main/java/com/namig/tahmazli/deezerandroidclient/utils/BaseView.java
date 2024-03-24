package com.namig.tahmazli.deezerandroidclient.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

public abstract class BaseView implements MvpView {
    private final View mView;

    protected BaseView(final LayoutInflater inflater,
                       final ViewGroup parent,
                       @LayoutRes final int layoutId) {
        mView = inflater.inflate(layoutId, parent, false);
    }

    @Override
    public final View getRoot() {
        return mView;
    }

    protected <V extends View> V findViewById(@IdRes final int id) {
        return mView.findViewById(id);
    }

    protected Context getContext() {
        return mView.getContext();
    }
}
