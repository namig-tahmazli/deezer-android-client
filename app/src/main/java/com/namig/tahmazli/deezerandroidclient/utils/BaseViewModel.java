package com.namig.tahmazli.deezerandroidclient.utils;

import androidx.lifecycle.ViewModel;

import com.namig.tahmazli.deezerandroidclient.utils.presenter.Presenter;

public abstract class BaseViewModel<V, P extends Presenter<V, ?>> extends ViewModel {
    protected final P mPresenter;

    protected BaseViewModel(final P presenter) {
        mPresenter = presenter;
    }

    public final Presenter<V, ?> getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCleared() {
        mPresenter.saveState();
        super.onCleared();
    }
}
