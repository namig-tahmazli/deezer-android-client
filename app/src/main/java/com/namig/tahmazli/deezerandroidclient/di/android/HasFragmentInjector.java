package com.namig.tahmazli.deezerandroidclient.di.android;

import androidx.fragment.app.Fragment;

public interface HasFragmentInjector {
    AndroidInjector<Fragment> getInjector();
}
