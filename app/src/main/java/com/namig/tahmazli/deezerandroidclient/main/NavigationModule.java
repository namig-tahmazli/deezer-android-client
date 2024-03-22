package com.namig.tahmazli.deezerandroidclient.main;

import com.namig.tahmazli.deezerandroidclient.di.AppScope;
import com.namig.tahmazli.deezerandroidclient.main.Navigator;
import com.namig.tahmazli.deezerandroidclient.main.NavigatorImpl;

import dagger.Binds;
import dagger.Module;

@Module public abstract class NavigationModule {

    @AppScope
    @Binds
    abstract Navigator bindNavigator(final NavigatorImpl navigator);
}
