package com.namig.tahmazli.deezerandroidclient.main.di;

import com.namig.tahmazli.deezerandroidclient.di.android.ActivityScope;
import com.namig.tahmazli.deezerandroidclient.di.android.Injector;
import com.namig.tahmazli.deezerandroidclient.main.MainActivity;

import dagger.Subcomponent;

@ActivityScope(MainActivity.class)
@Subcomponent(modules = {FragmentsBinding.class, MainActivityModule.class})
public interface MainSubcomponent extends Injector<MainActivity> {

    @Subcomponent.Factory
    interface Factory extends Injector.Factory<MainActivity> {
    }
}
