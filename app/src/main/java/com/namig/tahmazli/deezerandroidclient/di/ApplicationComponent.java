package com.namig.tahmazli.deezerandroidclient.di;

import com.namig.tahmazli.deezerandroidclient.App;
import com.namig.tahmazli.deezerandroidclient.di.android.Injector;

import dagger.Component;

@AppScope
@Component(modules = {ActivitiesBinding.class, ApplicationModule.class})
public interface ApplicationComponent extends Injector<App> {
    @Component.Factory
    interface Factory extends Injector.Factory<App> {
    }
}
