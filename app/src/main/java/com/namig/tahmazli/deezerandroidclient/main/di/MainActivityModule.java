package com.namig.tahmazli.deezerandroidclient.main.di;

import androidx.fragment.app.FragmentManager;

import com.namig.tahmazli.deezerandroidclient.di.android.ActivityScope;
import com.namig.tahmazli.deezerandroidclient.main.MainActivity;
import com.namig.tahmazli.deezerandroidclient.utils.view.SharedElementTransition;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
    @Provides
    @ActivityScope(MainActivity.class)
    static FragmentManager provideFragmentManager(final MainActivity activity) {
        return activity.getSupportFragmentManager();
    }

    @Provides
    @ActivityScope(MainActivity.class)
    static SharedElementTransition provideTransition(final MainActivity activity) {
        return new SharedElementTransition(activity.getWindow());
    }
}
