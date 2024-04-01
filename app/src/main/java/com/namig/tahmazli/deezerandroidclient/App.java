package com.namig.tahmazli.deezerandroidclient;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.namig.tahmazli.deezerandroidclient.di.DaggerApplicationComponent;
import com.namig.tahmazli.deezerandroidclient.di.android.AndroidInjector;
import com.namig.tahmazli.deezerandroidclient.di.android.HasActivityInjector;

import javax.inject.Inject;

public final class App extends Application implements HasActivityInjector {

    @Inject
    AndroidInjector<Activity> activityInjector;

    @Override
    public AndroidInjector<Activity> getInjector() {
        return activityInjector;
    }

    @Override
    public void onCreate() {
        DaggerApplicationComponent.factory().create(this).inject(this);
        super.onCreate();

        Thread.setDefaultUncaughtExceptionHandler((t, e) ->
                Log.e("UNHANDLED_EXCEPTION", t.getName(), e));
    }
}
