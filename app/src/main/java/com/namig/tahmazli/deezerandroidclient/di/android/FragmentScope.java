package com.namig.tahmazli.deezerandroidclient.di.android;

import androidx.fragment.app.Fragment;

import javax.inject.Scope;

@Scope
public @interface FragmentScope {
    Class<? extends Fragment> value();
}
