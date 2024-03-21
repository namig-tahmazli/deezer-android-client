package com.namig.tahmazli.deezerandroidclient.di;

import com.namig.tahmazli.deezerandroidclient.di.android.ActivityKey;
import com.namig.tahmazli.deezerandroidclient.di.android.Injector;
import com.namig.tahmazli.deezerandroidclient.main.MainActivity;
import com.namig.tahmazli.deezerandroidclient.main.di.MainSubcomponent;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {MainSubcomponent.class})
public abstract class ActivitiesBinding {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract Injector.Factory<?> bindMainActivity(final MainSubcomponent.Factory factory);
}
