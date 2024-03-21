package com.namig.tahmazli.deezerandroidclient.di.android;

import androidx.fragment.app.Fragment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import dagger.MapKey;

@MapKey
@Target({ElementType.METHOD})
public @interface FragmentKey {
    Class<? extends Fragment> value();
}
