package com.namig.tahmazli.deezerandroidclient.di.android;

import dagger.BindsInstance;

public interface Injector<T> {

    interface Factory<T> {

        Injector<T> create(@BindsInstance final T instance);
    }

    void inject(final T instance);
}
