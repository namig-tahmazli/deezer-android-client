package com.namig.tahmazli.deezerandroidclient.di.android;

import android.app.Activity;

public interface HasActivityInjector {
    AndroidInjector<Activity> getInjector();
}
