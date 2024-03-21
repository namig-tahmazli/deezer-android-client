package com.namig.tahmazli.deezerandroidclient.di.android;

import android.app.Activity;

import javax.inject.Scope;

@Scope
public @interface ActivityScope {
    Class<? extends Activity> value();
}
