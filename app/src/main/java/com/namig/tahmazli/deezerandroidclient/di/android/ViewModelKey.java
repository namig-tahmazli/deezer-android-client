package com.namig.tahmazli.deezerandroidclient.di.android;

import androidx.lifecycle.ViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import dagger.MapKey;

@MapKey
@Target({ElementType.METHOD})
public @interface ViewModelKey {
    Class<? extends ViewModel> value();
}
