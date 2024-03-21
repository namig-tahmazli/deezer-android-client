package com.namig.tahmazli.deezerandroidclient.main.di;

import androidx.fragment.app.FragmentManager;

import com.namig.tahmazli.deezerandroidclient.di.android.ActivityScope;
import com.namig.tahmazli.deezerandroidclient.main.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
    @Provides
    @ActivityScope(MainActivity.class)
    static FragmentManager provideFragmentManager(final MainActivity activity) {
        return activity.getSupportFragmentManager();
    }
}
